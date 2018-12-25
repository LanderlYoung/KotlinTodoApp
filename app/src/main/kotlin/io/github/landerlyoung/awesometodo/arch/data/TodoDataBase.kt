package io.github.landerlyoung.awesometodo.arch.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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
        entities = [(TodoEntity::class)],
        version = 1
)
abstract class TodoDataBase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        private const val DB_NAME = "todo.db"

        fun getOrCreateDb(context: Context): TodoDataBase =
                Room.databaseBuilder(context, TodoDataBase::class.java, DB_NAME).build()
    }
}