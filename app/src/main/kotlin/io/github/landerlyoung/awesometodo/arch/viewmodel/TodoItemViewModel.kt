package io.github.landerlyoung.awesometodo.arch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-20
 * Time:   22:47
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class TodoItemViewModel(private val todoViewModel: TodoViewModel)
    : ViewModel() {

    val name = ObservableField<String>()
    val done = ObservableBoolean()

    fun setItemDone(done: Boolean) {
        if (done != this.done.get()) {
            todoViewModel.modifyItem(name.get() ?: "", done)
        }
    }
}
