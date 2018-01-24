package io.github.landerlyoung.awesometodo.arch.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.recyclerview.extensions.DiffCallback
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
import io.github.landerlyoung.awesometodo.databinding.ActivityTodoBinding

/**
 * <pre>
 * Author: landerlyoung@gmail.com
 * Date:   2017-05-20
 * Time:   22:47
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class TodoFragment : Fragment() {
    private lateinit var adapter: Adapter
    private lateinit var todoViewMode: TodoViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val vm = ViewModelProviders.of(activity!!).get(TodoViewModel::class.java)
        val binding: ActivityTodoBinding = DataBindingUtil.inflate(inflater, R.layout.activity_todo, container, false)

        binding.viewModel = vm

        todoViewMode = vm
        lifecycle.addObserver(vm)
        adapter = Adapter()

        val recyclerView = binding.recyclerView
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(p0: RecyclerView?, p1: RecyclerView.ViewHolder?, p2: RecyclerView.ViewHolder?): Boolean {
                return true
            }

            override fun onSwiped(v: RecyclerView.ViewHolder, dir: Int) {
                todoViewMode.removeItem(v.adapterPosition)
            }
        }).attachToRecyclerView(recyclerView)

        vm.allItems.observe(this, Observer { pagedList ->
            adapter.setList(pagedList)

            pagedList?.addWeakCallback(null, object : PagedList.Callback() {
                override fun onChanged(position: Int, count: Int) {
                }

                override fun onInserted(position: Int, count: Int) {
                }

                override fun onRemoved(position: Int, count: Int) {
                }
            })
        })

        binding.add.setOnLongClickListener {
            // add fake data
            for (i in 0 until 100) {
                vm.newItemName.set("fake todo $i")
                vm.addNewItem()
            }

            true
        }

        return binding.root
    }

    private class ViewHolder(view: View, val vm: TodoItemViewModel) : RecyclerView.ViewHolder(view)

    private inner class Adapter : PagedListAdapter<TodoEntity, ViewHolder>(object : DiffCallback<TodoEntity?>() {
        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem == newItem
        }
    }) {
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
            val item = getItem(position)
            viewHolder.vm.name.set(item?.name)
            viewHolder.vm.done.set(item?.done ?: false)
        }
    }
}
