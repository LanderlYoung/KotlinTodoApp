package io.github.landerlyoung.awesometodo.test

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

    private var string: String? = null

    val logMsg
        get() = log.toString()

    fun run() {
        val clazz = io.github.landerlyoung.awesometodo.test.KotlinTest::class.java
        clazz.declaredMethods
                .filter { it.getAnnotation(io.github.landerlyoung.awesometodo.Test::class.java) != null }
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
        println("${io.github.landerlyoung.awesometodo.test.KotlinTest::class}:${io.github.landerlyoung.awesometodo.test.KotlinTest::class.java}:${this.javaClass}")
    }

    @Test
    fun collection(): List<String> {
        listOf(1, 2).any {
            true
        }
        return listOf("a", "b", "c", "d")
    }

    @Test
    fun inlineTest() {
        var items = IntArray(10, { it * it })
        items.forEach {
            if (it == 25) {
                println("break from lambda")
                return
            }
        }
        println("return method")
    }

    @Test
    fun nullTest() {
        string = "hello"
        string!!.length

    }

    @Test
    fun defaultArgs() {
        manyArgs()
        manyArgs(arg3 = 9)
    }

    fun manyArgs(
            arg1: Int = 0,
            arg2: Int = 0,
            arg3: Int = 0,
            arg4: Int = 0,
            arg5: Int = 0,
            arg6: Int = 0,
            arg7: Int = 0,
            arg8: Int = 0,
            arg9: Int = 0,
            arg10: Int = 0,
            arg11: Int = 0,
            arg12: Int = 0,
            arg13: Int = 0) {

    }
}
