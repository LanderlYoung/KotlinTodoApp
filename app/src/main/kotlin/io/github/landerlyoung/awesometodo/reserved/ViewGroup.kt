package io.github.landerlyoung.awesometodo.reserved

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-06-07
 * Time:   11:12
 * Life with Passion, Code with Creativity.
 * </pre>
 */
fun main(args: Array<String>) {
    val viewGroup = FrameLayout(null)
    val view = View(null)

    viewGroup += view
    assert(viewGroup[0] == view)
    assert(view in viewGroup)

    for (view in viewGroup) {
    }
}

inline operator fun ViewGroup.plusAssign(view: View) {
    addView(view)
}

inline operator fun ViewGroup.get(index: Int): View {
    return getChildAt(index)
}

inline operator fun ViewGroup.contains(view: View): Boolean {
    return indexOfChild(view) != -1
}

inline operator fun ViewGroup.iterator(): Iterator<View> {
    return object : Iterator<View> {
        val parent = this@iterator
        var index = 0

        override fun hasNext(): Boolean {
            return index < parent.childCount
        }

        override fun next(): View {
            return parent[index]
        }
    }
}
