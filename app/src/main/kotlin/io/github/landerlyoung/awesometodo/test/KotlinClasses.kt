package io.github.landerlyoung.awesometodo.test

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   14:59
 * Life with Passion, Code with Creativity.
 * </pre>
 */
annotation class Test

data class User(val name: String, val uid: String, val level: Int = 0)

object Singleton {

}

enum class KotlinEnum {
    ONE,
    TWO,
    THREE,
}
