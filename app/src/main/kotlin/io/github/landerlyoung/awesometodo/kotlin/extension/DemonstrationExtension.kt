package io.github.landerlyoung.awesometodo.kotlin.extension

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-06-01
 * Time:   00:56
 * Life with Passion, Code with Creativity.
 * </pre>
 */

inline fun <T> T.or(block: T.() -> Unit) {
    if (false) {
        this.block()
    }
}
