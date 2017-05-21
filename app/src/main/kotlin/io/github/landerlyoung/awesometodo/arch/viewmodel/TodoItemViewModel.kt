package io.github.landerlyoung.awesometodo.arch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   22:47
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class TodoItemViewModel(application: Application) : AndroidViewModel(application) {
    val name = ObservableField<CharSequence>()
    val done = ObservableBoolean()
}
