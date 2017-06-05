package io.github.landerlyoung.awesometodo.kotlin.extension

import java.lang.ref.WeakReference

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-06-05
 * Time:   23:15
 * Life with Passion, Code with Creativity.
 * </pre>
 */

inline fun <T> withWeak(thiz: T, block: WeakContext<T>.() -> Unit) {
    WeakContext(thiz).block()
}

class WeakContext<out T>(context: T) {
    private val weakThiz = WeakReference(context)
    val thiz: T? get() = weakThiz.get()
}
