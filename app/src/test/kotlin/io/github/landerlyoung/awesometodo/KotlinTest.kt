package io.github.landerlyoung.awesometodo

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-19
 * Time:   22:58
 * Life with Passion, Code with Creativity.
 * </pre>
 */
@RunWith(BlockJUnit4ClassRunner::class)
class KotlinTest {

    @Test
    fun helloWorld() {
        println("Hello World")
    }

    init {
        println("constructor")
    }
}
