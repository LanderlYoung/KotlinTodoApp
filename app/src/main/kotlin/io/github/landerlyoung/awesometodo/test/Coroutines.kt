package io.github.landerlyoung.awesometodo.test

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-06-03
 * Time:   19:46
 * Life with Passion, Code with Creativity.
 * </pre>
 */

suspend fun fibonacciSeq() {
    var a = 0
    var b = 1

}

//val seq = buildSequence {
//    var a = 0
//    var b = 1
//
//    yield(b)
//
//    while (true) {
//        val tmp = b
//        b += a
//        a = tmp
//        yield(b)
//    }
//}

fun main(args: Array<String>) = runBlocking {
    val job = launch {
        // create new coroutine and keep a reference to its Job
        delay(1000L)
        println("World!")
    }
    println("Hello,")
    job.join() // wait until child coroutine completes
}