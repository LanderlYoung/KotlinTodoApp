package io.github.landerlyoung.awesometodo.arch.ui

import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import io.github.landerlyoung.awesometodo.R
import io.github.landerlyoung.awesometodo.arch.viewmodel.TodoItemViewModel

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-21
 * Time:   13:29
 * Life with Passion, Code with Creativity.
 * </pre>
 */
object TodoItemViewUI {
    fun inflate(inflater: LayoutInflater,
                parent: ViewGroup?,
                attachToParent: Boolean,
                viewMode: TodoItemViewModel): View {

        val rootView = inflater.inflate(
                R.layout.todo_item,
                parent,
                attachToParent)

        val done = rootView.findViewById(R.id.new_item_done) as? CheckBox
        val name = rootView.findViewById(R.id.name) as? TextView

        // dual-bind check-box
        done?.isChecked = viewMode.done.get()
        done?.setOnCheckedChangeListener({ _, isChecked ->
            viewMode.setItemDone(isChecked)
        })
        viewMode.done.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                done?.isChecked = (p0 as ObservableBoolean).get()
            }
        })

        // dual-bind edit-text
        name?.text = viewMode.name.get()
        viewMode.name.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            @Suppress("UncheckedCast")
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                val newString = (p0 as ObservableField<String>).get()
                val old = name?.text.toString()

                if (newString != old) {
                    name?.text = newString
                }
            }
        })



        return rootView
    }
}
