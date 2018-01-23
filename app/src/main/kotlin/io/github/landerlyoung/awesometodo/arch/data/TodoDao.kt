package io.github.landerlyoung.awesometodo.arch.data

import android.arch.persistence.room.*
import android.util.Log
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-20
 * Time:   20:30
 * Life with Passion, Code with Creativity.
 * </pre>
 */
@Dao
abstract class TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addItem(item: TodoEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateItem(item: TodoEntity)

    @Delete
    abstract fun deleteItem(item: TodoEntity)

    /** query all items synchronized */
    @Query("SELECT * FROM ${TodoEntity.TABLE_NAME} ORDER BY ${TodoEntity.COLUME_UPDATE_TIME} DESC")
    abstract fun allItems(): List<TodoEntity>

    @Query("SELECT COUNT(*) FROM ${TodoEntity.TABLE_NAME}")
    abstract fun todoItems(): Int

    suspend fun allItemsCoroutine(): Deferred<List<TodoEntity>> = async(CommonPool) {
        Log.i("young", "allItemsCoroutine:" + Thread.currentThread().name)

        allItems()
    }
}