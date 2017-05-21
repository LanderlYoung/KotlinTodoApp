package io.github.landerlyoung.awesometodo

import android.app.Application
import io.reactivex.internal.schedulers.IoScheduler

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-21
 * Time:   14:27
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class AwesomeApplication : Application() {
    val ioScheduler = IoScheduler()
}