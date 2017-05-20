package io.github.landerlyoung.awesometodo.arch.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   20:29
 * Life with Passion, Code with Creativity.
 * </pre>
 */
@Entity(tableName = TodoEntity.TABLE_NAME)
data class TodoEntity(
        @PrimaryKey val name: String,
        val done: Boolean,
        val createTimeMillis: Long = System.currentTimeMillis(),
        val updateTimeMillis: Long = System.currentTimeMillis()) {

    companion object {
        const val TABLE_NAME = "todo_entity"

        const val COLUME_NAME = "name"
        const val COLUME_done = "done"
        const val COLUME_CREATE_TIME = "createTimeMillis"
        const val COLUME_UPDATE_TIME = "updateTimeMillis"
    }
}
