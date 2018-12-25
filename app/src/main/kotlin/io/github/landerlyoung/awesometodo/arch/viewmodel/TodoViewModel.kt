package io.github.landerlyoung.awesometodo.arch.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.github.landerlyoung.awesometodo.arch.data.TodoDataBase
import io.github.landerlyoung.awesometodo.arch.data.TodoEntity
import io.github.landerlyoung.awesometodo.rx.Sched
import io.reactivex.Observable

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-20
 * Time:   21:42
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class TodoViewModel : AndroidViewModel, LifecycleObserver {
    private val todoDao by lazy {
        // maybe use dagger to inject
        TodoDataBase.getOrCreateDb(getApplication()).todoDao()
    }

    val newItemName = ObservableField<String>()
    val newItemDone = ObservableBoolean()
    val allItems: LiveData<PagedList<TodoEntity>>

    constructor(application: Application) : super(application) {
        allItems = LivePagedListBuilder(todoDao.allItemsDataSource(), 20).build()
    }

    fun addNewItem(): Boolean {
        return newItemName.get()?.let { name ->
            val entity = TodoEntity(name, newItemDone.get())

            addNewItem(entity)

            newItemName.set(null)
            newItemDone.set(false)
            true
        } ?: false
    }

    @SuppressLint("CheckResult")
    private fun addNewItem(entity: TodoEntity) {
        Observable.just(entity)
                .subscribeOn(Sched.ioScheduler)
                .subscribe { todoDao.addItem(it) }
    }

    fun modifyItem(name: String, done: Boolean) {
        val item = allItems.value?.firstOrNull { it?.name == name }
        item?.let {
            val newItem = item.copy(done = done, updateTimeMillis = System.currentTimeMillis())

            // update db
            val subscribe = Observable.just(newItem)
                    .subscribeOn(Sched.ioScheduler)
                    .subscribe {
                        todoDao.updateItem(it)
                    }
            subscribe
        }
    }

    // Async method using RxJava
    @SuppressLint("CheckResult")
    fun removeItem(index: Int): Boolean {
        allItems.value?.get(index)?.let { entity ->
            Observable.just(entity)
                    .subscribeOn(Sched.ioScheduler)
                    .subscribe {
                        todoDao.deleteItem(entity)
                    }
        }
        return false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun fetchData() {

        // getAllItems_paging()

//        or { getAllItems_Coroutines() }
//
//        or { getAllItems_AnkoAsync() }
//
//        or { getAllItems_RxJava() }
//
//        or { getAllItems_AsyncTask() }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun release() {
    }
}
