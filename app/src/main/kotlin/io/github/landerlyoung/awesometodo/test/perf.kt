package io.github.landerlyoung.awesometodo.test

import android.content.SharedPreferences

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-23
 * Time:   22:51
 * Life with Passion, Code with Creativity.
 * </pre>
 */

// shared preference DSL
inline fun SharedPreferences.write(block: SharedPreferences.Editor.() -> Unit) {
    val edit = edit()
    edit.block()
    edit.apply()
}

val map = mapOf(1 to 2, 3 to 4)

fun spDslWrite(sp: SharedPreferences) {
    sp.write {
        putString("string", "value")
        putBoolean("isKotlinAwesome", true)
    }
}
