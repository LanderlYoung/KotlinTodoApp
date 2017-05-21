package io.github.landerlyoung.awesometodo.arch.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import io.github.landerlyoung.awesometodo.AwesomeApplication
import io.github.landerlyoung.awesometodo.arch.data.TodoDataBase
import io.github.landerlyoung.awesometodo.arch.data.TodoEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

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

        Observable.just(entity)
                .subscribeOn(getApplication<AwesomeApplication>().ioScheduler)
                .subscribe { entity ->
                    todoDao.addItem(entity)
                }

        allItems.add(entity)

        newItemName.set(null)
        newItemDone.set(false)
        return true
    }

    fun modifyItem(name: String, done: Boolean) {
        val index = allItems.indexOfFirst { it.name == name }
        val item = if (index != -1) allItems[index] else null
        item?.let {
            val newItem = item.copy(done = done, updateTimeMillis = System.currentTimeMillis())

            // update memory cache
            allItems[index] = newItem

            // update db
            Observable.just(newItem)
                    .subscribeOn(getApplication<AwesomeApplication>().ioScheduler)
                    .subscribe {
                        todoDao.updateItem(it)
                    }
        }
    }

    fun removeItem(index: Int): Boolean {
        return false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchData() {
        Observable.create<List<TodoEntity>> {
            it.onNext(todoDao.allItems())
            it.onComplete()
        }
                .subscribeOn(getApplication<AwesomeApplication>().ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { items ->
                    allItems.clear()
                    allItems.addAll(items)
                }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun release() {
    }
}
