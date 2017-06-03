package io.github.landerlyoung.awesometodo.arch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import io.github.landerlyoung.awesometodo.arch.data.TodoDataBase
import io.github.landerlyoung.awesometodo.arch.data.TodoEntity
import io.github.landerlyoung.awesometodo.kotlin.extension.or
import io.github.landerlyoung.awesometodo.rx.Sched
import io.reactivex.Observable
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   21:42
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class TodoViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    private val todoDao by lazy {
        // maybe use dagger to inject
        TodoDataBase.getOrCreateDb(getApplication()).todoDao()
    }

    val newItemName = ObservableField<String>()
    val newItemDone = ObservableBoolean()
    val allItems = ObservableArrayList<TodoEntity>()

    fun addNewItem(): Boolean {
        if (allItems.indexOfFirst { it.name == newItemName.get() } != -1) {
            return false
        }
        val entity = TodoEntity(newItemName.get(), newItemDone.get())

        addNewItem(entity)

        allItems.add(0, entity)

        newItemName.set(null)
        newItemDone.set(false)
        return true
    }

    private fun addNewItem(entity: TodoEntity) {
        Observable.just(entity)
                .subscribeOn(Sched.ioScheduler)
                .subscribe { entity ->
                    todoDao.addItem(entity)
                }
    }

    fun modifyItem(name: String, done: Boolean) {
        val index = allItems.indexOfFirst { it.name == name }
        val item = if (index != -1) allItems[index] else null
        item?.let {
            val newItem = item.copy(done = done, updateTimeMillis = System.currentTimeMillis())

            // update memory cache
            allItems.removeAt(index)
            allItems.add(0, newItem)

            // update db
            Observable.just(newItem)
                    .subscribeOn(Sched.ioScheduler)
                    .subscribe {
                        todoDao.updateItem(it)
                    }
        }
    }

    // Async method useing RxJava
    fun removeItem(index: Int): Boolean {
        val entity = allItems.removeAt(index)

        Observable.just(entity)
                .subscribeOn(Sched.ioScheduler)
                .subscribe {
                    todoDao.deleteItem(entity)
                }
        return false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchData() {

        getAllItems_Coroutines()

        or {
            getAllItems_AnkoAsync()
        }

        or {

        }
    }

    // Asnc method call use anko
    // async task style
    private fun getAllItems_AnkoAsync() {
        // elegant async task
        // weak ref, no memory leak
        doAsync {
            // do in the back ground
            val allItems = todoDao.allItems()

            // post to ui thread
            uiThread {
                this@TodoViewModel.allItems.clear()
                this@TodoViewModel.allItems.addAll(allItems)
            }
        }
    }

    private fun getAllItems_Coroutines() = launch(UI) {
        // magic happens!
        val items = todoDao.allItemsCoroutine().await()

        Log.i("young", "getAllItems_Coroutines:" + Thread.currentThread().name)
        allItems.clear()
        allItems.addAll(items)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun release() {
    }
}
