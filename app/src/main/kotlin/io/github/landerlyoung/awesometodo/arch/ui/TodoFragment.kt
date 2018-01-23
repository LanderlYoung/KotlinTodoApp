package io.github.landerlyoung.awesometodo.arch.ui

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.landerlyoung.awesometodo.R
import io.github.landerlyoung.awesometodo.arch.data.TodoEntity
import io.github.landerlyoung.awesometodo.arch.viewmodel.TodoItemViewModel
import io.github.landerlyoung.awesometodo.arch.viewmodel.TodoViewModel

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   22:47
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class TodoFragment : Fragment() {
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)
    private lateinit var adapter: Adapter
    private lateinit var todoViewMode: TodoViewModel

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val vm = ViewModelProviders.of(activity!!).get(TodoViewModel::class.java)
        val view = TodoUI.inflate(inflater, container, false, vm)

        todoViewMode = vm
        lifecycleRegistry.addObserver(vm)
        adapter = Adapter()
        adapter.todoItems.addAll(vm.allItems)

        val recyclerView = view.findViewById(R.id.recycler_view) as? RecyclerView
        recyclerView?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView?.adapter = adapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(p0: RecyclerView?, p1: RecyclerView.ViewHolder?, p2: RecyclerView.ViewHolder?): Boolean {
                return true
            }

            override fun onSwiped(v: RecyclerView.ViewHolder, dir: Int) {
                todoViewMode.removeItem(v.adapterPosition)
            }
        }).attachToRecyclerView(recyclerView)

        vm.allItems.addOnListChangedCallback(
                object : ObservableList.OnListChangedCallback<ObservableArrayList<TodoEntity>>() {
                    override fun onItemRangeInserted(list: ObservableArrayList<TodoEntity>, index: Int, count: Int) {
                        adapter.todoItems.clear()
                        adapter.todoItems.addAll(list)

                        for (position in index until (index + count)) {
                            adapter.notifyItemInserted(position)
                        }
                    }

                    override fun onChanged(list: ObservableArrayList<TodoEntity>) {
                    }

                    override fun onItemRangeMoved(list: ObservableArrayList<TodoEntity>, from: Int, to: Int, count: Int) {

                    }

                    override fun onItemRangeRemoved(list: ObservableArrayList<TodoEntity>, index: Int, count: Int) {
                        adapter.todoItems.clear()
                        adapter.todoItems.addAll(list)
                        adapter.notifyItemRangeRemoved(index, count)
                    }

                    override fun onItemRangeChanged(list: ObservableArrayList<TodoEntity>, index: Int, count: Int) {
                        for (pos in index until (index + count)) {
                            adapter.todoItems[pos] = list[pos]
                        }
                        adapter.notifyItemRangeChanged(index, count)
                    }
                })

        return view
    }

    private class ViewHolder(view: View, val vm: TodoItemViewModel) : RecyclerView.ViewHolder(view)

    private inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        val todoItems: MutableList<TodoEntity> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val vm = TodoItemViewModel(activity!!.application, todoViewMode)
            val view = TodoItemViewUI.inflate(
                    LayoutInflater.from(context),
                    parent,
                    false,
                    vm)

            return ViewHolder(view, vm)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            val item = todoItems[position]
            viewHolder.vm.name.set(item.name)
            viewHolder.vm.done.set(item.done)
        }

        override fun getItemCount(): Int {
            return todoItems.size
        }
    }
}
