package io.github.landerlyoung.awesometodo.arch.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-20
 * Time:   20:29
 * Life with Passion, Code with Creativity.
 * </pre>
 */
@Entity(tableName = "todo_entity")
data class TodoEntity(
        @PrimaryKey val name: String,
        val done: Boolean,
        val createTimeMillis: Long = System.currentTimeMillis(),
        val updateTimeMillis: Long = System.currentTimeMillis()
)
