package io.github.landerlyoung.awesometodo.arch.data

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.*

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
    @Query("SELECT * FROM todo_entity ORDER BY updateTimeMillis DESC")
    abstract fun allItems(): List<TodoEntity>

    @Query("SELECT COUNT(*) FROM todo_entity")
    abstract fun todoItems(): Int

    @Query("SELECT COUNT(*) FROM todo_entity")
    abstract fun todoItemsLiveData(): LiveData<Int>

    @Query("SELECT * FROM todo_entity ORDER BY updateTimeMillis DESC")
    abstract fun allItemsDataSource(): DataSource.Factory<Int, TodoEntity>

}