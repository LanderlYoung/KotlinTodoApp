@file:Suppress("NOTHING_TO_INLINE")

package io.github.landerlyoung.awesometodo.kotlin.extension

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import kotlin.reflect.KProperty

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   23:01
 * Life with Passion, Code with Creativity.
 * </pre>
 */
inline operator fun <T> ObservableField<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T?)
        = this.set(value)

inline operator fun <T> ObservableField<T>.getValue(thisRef: Any?, property: KProperty<*>): T?
        = this.get()

inline operator fun ObservableBoolean.setValue(thisRef: Any?, property: KProperty<*>, value: Boolean)
        = this.set(value)

inline operator fun ObservableBoolean.getValue(thisRef: Any?, property: KProperty<*>): Boolean
        = this.get()

inline operator fun ObservableInt.setValue(thisRef: Any?, property: KProperty<*>, value: Int)
        = this.set(value)

inline operator fun ObservableInt.getValue(thisRef: Any?, property: KProperty<*>): Int
        = this.get()

