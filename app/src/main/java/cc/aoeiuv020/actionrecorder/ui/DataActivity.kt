package cc.aoeiuv020.actionrecorder.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cc.aoeiuv020.actionrecorder.App
import cc.aoeiuv020.actionrecorder.R
import cc.aoeiuv020.actionrecorder.sql.Action
import cc.aoeiuv020.actionrecorder.util.ignoreException
import cc.aoeiuv020.actionrecorder.util.show
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.data_item.view.*
import kotlinx.android.synthetic.main.range_picker.*
import org.jetbrains.anko.*
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

        @SuppressLint("SimpleDateFormat")
        val copySdf = SimpleDateFormat("HH:mm:ss")
    }

    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        recyclerView.adapter = adapter

        ivRefresh.setOnClickListener {
            refresh()
        }

        swipeRefresh.setOnRefreshListener {
            refresh()
        }

        val now = System.currentTimeMillis()
        val from = now - TimeUnit.DAYS.toMillis(1)
        val to = now
        tvTime1.text = sdf.format(Date(from))
        tvTime2.text = sdf.format(Date(to))

        @Suppress("DEPRECATION")
        val listener: (View) -> Unit = { view ->
            if (view is TextView) {
                val date = ignoreException { sdf.parse(view.text.toString()) }
                        ?: Date()
                DatePickerDialog(this, { _, year, month, day ->
                    date.year = year - 1900
                    date.month = month
                    date.date = day
                    view.text = sdf.format(date)
                    TimePickerDialog(this, { _, hour, minutes ->
                        date.hours = hour
                        date.minutes = minutes
                        view.text = sdf.format(date)
                    }, date.hours, date.minutes, true).show()
                }, date.year + 1900, date.month, date.date).show()
            }
        }

        tvTime1.setOnClickListener(listener)
        tvTime2.setOnClickListener(listener)
    }

    override fun onStart() {
        super.onStart()
        refresh()
    }

    private fun refresh() {
        swipeRefresh.isRefreshing = true
        doAsync {
            val now = System.currentTimeMillis()
            val time1 = ignoreException { sdf.parse(tvTime1.text.toString()).time } ?: now
            val time2 = ignoreException { sdf.parse(tvTime2.text.toString()).time } ?: now
            val from = minOf(time1, time2)
            val to = maxOf(time1, time2)
            val actions = App.appDatabase.actionDao().getAction(from, to)
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(ctx).inflate(R.layout.data_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = actions.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = actions[position]
            holder.setData(item)
            holder.itemView.setOnClickListener { v ->
                val s = copySdf.format(Date(item.time))
                val cm: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val mClipData = ClipData.newPlainText(item.name, s)
                cm.setPrimaryClip(mClipData)
                v.context.toast(v.context.getString(R.string.copy_place_holder, s))
            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvBattery: TextView = itemView.tvBattery
        private val tvTime: TextView = itemView.tvTime
        private val tvType: TextView = itemView.tvType
        private val tvName: TextView = itemView.tvName
        private val tvComments: TextView = itemView.tvComments

        @SuppressLint("SetTextI18n")
        fun setData(action: Action) {
            tvTime.text = sdf.format(Date(action.time))
            tvType.text = action.type
            tvName.text = action.name
            tvComments.text = action.comments.toString()
            val commentsIsNull = action.comments == null
            tvType.show(commentsIsNull)
            tvName.show(commentsIsNull)
            tvComments.show(!commentsIsNull)
            tvBattery.text = "${action.battery}%"
            tvBattery.show(action.battery != -1)
        }
    }
}
