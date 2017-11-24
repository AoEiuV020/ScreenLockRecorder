package cc.aoeiuv020.actionrecorder.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import cc.aoeiuv020.actionrecorder.App
import cc.aoeiuv020.actionrecorder.R
import cc.aoeiuv020.actionrecorder.sql.Action
import cc.aoeiuv020.actionrecorder.util.show
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.data_item.view.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DataActivity : AppCompatActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity<DataActivity>()
        }

        @SuppressLint("SimpleDateFormat")
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    }

    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        recyclerView.adapter = adapter

        swipeRefresh.setOnRefreshListener {
            refresh()
        }
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        swipeRefresh.isRefreshing = true
        doAsync {
            // 加载三小时内的数据，
            val now = System.currentTimeMillis()
            val from = now - TimeUnit.HOURS.toMillis(3)
            val actions = App.database.actionDao().getActionFrom(from)
            uiThread {
                adapter.setData(actions)
                recyclerView.post {
                    recyclerView.smoothScrollToPosition(adapter.itemCount)
                }
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> refresh()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_data, menu)
        return super.onCreateOptionsMenu(menu)
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        private var actions: List<Action> = emptyList()
        fun setData(actions: List<Action>) {
            this.actions = actions
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(ctx).inflate(R.layout.data_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = actions.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.setData(actions[position])
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTime: TextView = itemView.tvTime
        private val tvType: TextView = itemView.tvType
        private val tvName: TextView = itemView.tvName
        private val tvComments: TextView = itemView.tvComments
        fun setData(action: Action) {
            tvTime.text = sdf.format(Date(action.time))
            tvType.text = action.type
            tvName.text = action.name
            tvComments.text = action.comments.toString()
            val commentsIsNull = action.comments == null
            tvType.show(commentsIsNull)
            tvName.show(commentsIsNull)
            tvComments.show(!commentsIsNull)
        }
    }
}
