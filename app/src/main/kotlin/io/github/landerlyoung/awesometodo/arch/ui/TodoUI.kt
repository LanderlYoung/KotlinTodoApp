package io.github.landerlyoung.awesometodo.arch.ui

import android.databinding.Observable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import io.github.landerlyoung.awesometodo.R
import io.github.landerlyoung.awesometodo.arch.viewmodel.TodoViewModel

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-21
 * Time:   13:29
 * Life with Passion, Code with Creativity.
 * </pre>
 */
object TodoUI {
    fun inflate(inflater: LayoutInflater,
                parent: ViewGroup?,
                attachToParent: Boolean,
                viewMode: TodoViewModel): View {

        val rootView = inflater.inflate(
                R.layout.activity_todo,
                parent,
                attachToParent)

        val done = rootView.findViewById(R.id.new_item_done) as? CheckBox
        val newItem = rootView.findViewById(R.id.new_item_name) as? EditText
        val add = rootView.findViewById(R.id.add) as? ImageButton

        // dual-bind check-box
        done?.isChecked = viewMode.newItemDone.get()
        add?.isEnabled = !viewMode.newItemName.get().isNullOrEmpty()

        done?.setOnCheckedChangeListener({ _, isChecked ->
            viewMode.newItemDone.set(isChecked)
        })
        viewMode.newItemDone.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                done?.isChecked = (p0 as ObservableBoolean).get()
            }
        })

        // dual-bind edit-text
        newItem?.setText(viewMode.newItemName.get())
        newItem?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val newString = newItem.text?.toString()
                val old = viewMode.newItemName.get()

                if (old != newString) {
                    viewMode.newItemName.set(newString)
                    add?.isEnabled = !newString.isNullOrEmpty()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        val addNewItem: () -> Unit = {
            if (!viewMode.newItemName.get().isNullOrBlank()) {
                if (!viewMode.addNewItem()) {
                    Snackbar.make(newItem!!, "Item Already Exist", Snackbar.LENGTH_SHORT)
                            .show()
                }
            } else {
                Snackbar.make(newItem!!, "Can't Add Empty Item", Snackbar.LENGTH_SHORT)
                        .show()
            }
        }

        newItem?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (!newItem.text.isNullOrEmpty()) {
                    addNewItem()
                    return@setOnKeyListener true
                }
            }
            false
        }

        newItem?.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEND -> {
                    addNewItem()
                    true
                }
                else -> false
            }
        }

        viewMode.newItemName.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            @Suppress("UNCHECKED_CAST")
            override fun onPropertyChanged(p0: Observable?, p1: Int) {
                val newString = (p0 as ObservableField<String>).get()
                val old = newItem?.text.toString()

                if (newString != old) {
                    newItem?.setText(newString)
                }
            }
        })

        // bind click
        add?.setOnClickListener({ _ -> addNewItem() })

        return rootView
    }

}