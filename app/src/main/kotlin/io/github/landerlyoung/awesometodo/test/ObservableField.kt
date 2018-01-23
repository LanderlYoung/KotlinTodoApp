package io.github.landerlyoung.awesometodo.test

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-20
 * Time:   14:20
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class ObservableField<T> {
    private var value: T? = null

    fun getValue(thisRef: Any?) = value

    fun setValue(thisReg: Any?) {
    }

    private fun notifyChanged() {

    }
}
