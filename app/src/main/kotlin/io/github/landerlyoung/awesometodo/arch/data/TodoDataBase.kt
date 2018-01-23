package io.github.landerlyoung.awesometodo.arch.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-20
 * Time:   20:29
 * Life with Passion, Code with Creativity.
 * </pre>
 */
@Database(
        entities = arrayOf(TodoEntity::class),
        version = 1
)
abstract class TodoDataBase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        const val DB_NAME = "todo.db"

        fun getOrCreateDb(context: Context): TodoDataBase =
                Room.databaseBuilder(context, TodoDataBase::class.java, DB_NAME).build()
    }
}