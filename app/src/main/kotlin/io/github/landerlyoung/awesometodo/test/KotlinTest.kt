package io.github.landerlyoung.awesometodo.test

import android.support.v4.os.TraceCompat
import java.io.FileInputStream
import java.io.InputStream
import java.util.concurrent.CompletableFuture
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

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

    fun inlineLambda() {
        thread {
            return@thread
        }

    }

    fun lock() {
        val lock: Lock = ReentrantLock()
        lock.lock()

        // 0
        try {
            println("have fun with the lock $lock")
        } finally {
            lock.unlock()
        }

        // 1
        lock.withLock {
            println("yeah")
        }

        // 2
        synchronized(lock) {

        }
    }

    fun stream() {
        val stream: InputStream = FileInputStream("/path/to/a/file")

        stream.use {
            while (stream.available() > 0) {
                println(stream.read())
            }
        }
    }

    fun drawAView(): Boolean = true

    fun traceExample(): Boolean {

        TraceCompat.beginSection("profile_draw")
        drawAView()
        TraceCompat.endSection()

        TraceCompat.beginSection("profile_draw")
        try {
            return drawAView()
        } finally {
            TraceCompat.endSection()
        }
    }

    fun niceTrace(): Boolean {
        trace("profile_draw") {
            return drawAView()
        }
    }

    inline fun <T> trace(name: String, block: () -> T): T {
        TraceCompat.beginSection(name)
        try {
            return block()
        } finally {
            TraceCompat.endSection()
        }
    }

    fun onBizResultImpl(rsp: GetShowInfo?) {
        if (rsp != null
                && rsp.showInfo != null
                && rsp.showInfo.show != null
                && rsp.showInfo.show.showId != null) {
            println("get data! ${rsp.showInfo.show.showId}")
        }

        rsp?.showInfo?.show?.showId?.let { id ->

        }
    }

    @Test
    fun refinedGeneric() {
        println(className<Runnable>())
        println(className<CompletableFuture.AsynchronousCompletionTask>())
    }

    inline fun <reified T> className(): String {
        return T::class.java.simpleName
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
            arg4: Int = 0) {
    }
}
