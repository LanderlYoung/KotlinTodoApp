package io.github.landerlyoung.awesometodo.kotlin.extension

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.UI

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-06-01
 * Time:   00:32
 * Life with Passion, Code with Creativity.
 * </pre>
 */
inline fun AndroidViewModel.ui(block: AnkoContext<Context>.() -> Unit): AnkoContext<Context> {
    return this.getApplication<Application>().UI {
        this.block()
    }
}
