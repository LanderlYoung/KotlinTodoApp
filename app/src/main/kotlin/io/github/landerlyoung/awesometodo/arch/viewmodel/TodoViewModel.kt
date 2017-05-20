package io.github.landerlyoung.awesometodo.arch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import io.github.landerlyoung.awesometodo.arch.data.TodoDataBase
import io.github.landerlyoung.awesometodo.kotlin.extension.getValue
import io.github.landerlyoung.awesometodo.kotlin.extension.setValue

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   21:42
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val newTodoItem = TodoItemViewModel(this.getApplication())

    var newItemName by ObservableField<CharSequence>()
    var newItemDone by ObservableBoolean()


    private val todoDao by lazy {
        // maybe use dagger to inject
        TodoDataBase.getOrCreateDb(getApplication()).todoDao()
    }

    fun getAllTodoItems() {

    }
}
