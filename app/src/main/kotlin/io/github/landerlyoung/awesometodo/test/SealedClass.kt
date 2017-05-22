package io.github.landerlyoung.awesometodo.test

import java.util.concurrent.locks.Lock

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-23
 * Time:   00:20
 * Life with Passion, Code with Creativity.
 * </pre>
 */

sealed class Item

data class ItemTitle(val title: String, val action: String) : Item()

data class ItemShow(val show: Show) : Item()

fun onBindViewHolder(viewHolder: Any, data: Item) {
    when (data) {
        is ItemTitle -> println(data.title)
        is ItemShow -> println(data.show.showId)
        else -> throw AssertionError()
    }
}

fun ouch(data: Any) {
    when (data) {
        is String -> println(data)
        is Lock -> data.lock()
        is StringBuilder -> data.append("yeah")
    }
}
