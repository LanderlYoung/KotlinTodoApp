package io.github.landerlyoung.awesometodo

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-19
 * Time:   22:58
 * Life with Passion, Code with Creativity.
 * </pre>
 *
 * cannot run
 */
@RunWith(JUnit4::class)
class HostKotlinTest {

    @Test
    fun helloWorld() {
        println("Hello World").hashCode()
        println("${HostKotlinTest::class}:${HostKotlinTest::class.java}:${this.javaClass}" )
    }

    init {
        println("constructor")
    }
}
