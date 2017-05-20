package io.github.landerlyoung.awesometodo

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   11:50
 * Life with Passion, Code with Creativity.
 * </pre>
 */

open class KotlinTest {
    private val log = StringBuilder()

    val logMsg
        get() = log.toString()

    fun run() {
        val clazz = KotlinTest::class.java
        clazz.declaredMethods
                .filter { it.getAnnotation(Test::class.java) != null }
                .forEach {
                    println(it.name)
                    it.invoke(this)
                }
    }

    internal fun println(msg: String) {
        log.append(msg).append('\n')
    }

    @Test
    fun helloWorld() {
        println("Hello World").hashCode()
        println("${KotlinTest::class}:${KotlinTest::class.java}:${this.javaClass}")

    }
}
