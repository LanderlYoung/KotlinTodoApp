package io.github.landerlyoung.awesometodo

import androidx.test.runner.AndroidJUnit4
import io.github.landerlyoung.awesometodo.test.KotlinTest
import org.junit.Test
import org.junit.runner.RunWith

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-19
 * Time:   22:58
 * Life with Passion, Code with Creativity.
 * </pre>
 */
@RunWith(AndroidJUnit4::class)
class DroidKotlinTest {

    @Test
    fun helloWorld() {
        println("Hello World").hashCode()
        println("${KotlinTest::class}:${KotlinTest::class.java}:${this.javaClass}" )
    }

    init {
        println("constructor")
    }
}
