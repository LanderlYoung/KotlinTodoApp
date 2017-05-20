package io.github.landerlyoung.awesometodo.arch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import io.github.landerlyoung.awesometodo.kotlin.extension.getValue
import io.github.landerlyoung.awesometodo.kotlin.extension.setValue

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   22:47
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class TodoItemViewModel(application: Application) : AndroidViewModel(application) {
    var name by ObservableField<CharSequence>()
    var done by ObservableBoolean()
}
