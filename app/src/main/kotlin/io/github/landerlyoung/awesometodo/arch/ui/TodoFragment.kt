package io.github.landerlyoung.awesometodo.arch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
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
class TodoFragment : androidx.fragment.app.Fragment() {
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
        recyclerView.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(context, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(p0: androidx.recyclerview.widget.RecyclerView, p1: androidx.recyclerview.widget.RecyclerView.ViewHolder, p2: androidx.recyclerview.widget.RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(v: androidx.recyclerview.widget.RecyclerView.ViewHolder, dir: Int) {
                todoViewMode.removeItem(v.adapterPosition)
            }
        }).attachToRecyclerView(recyclerView)

        vm.allItems.observe(this, Observer { pagedList ->
            adapter.submitList(pagedList)

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

    private class ViewHolder(view: View, val vm: TodoItemViewModel) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    private inner class Adapter : PagedListAdapter<TodoEntity, ViewHolder>(object :DiffUtil.ItemCallback<TodoEntity>() {
        override fun areContentsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: TodoEntity, newItem: TodoEntity): Boolean {
            return oldItem.name == newItem.name
        }
    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val vm = TodoItemViewModel(todoViewMode)
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
