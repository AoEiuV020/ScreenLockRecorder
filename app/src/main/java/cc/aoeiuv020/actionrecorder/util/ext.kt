package cc.aoeiuv020.actionrecorder.util

/**
 *
 * Created by AoEiuV020 on 2017.11.24-14:21:39.
 */
fun <R> ignoreException(block: () -> R?): R? = try {
    block()
} catch (_: Exception) {
    null
}