package io.github.landerlyoung.awesometodo.arch.ui

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.landerlyoung.awesometodo.arch.data.TodoEntity
import io.github.landerlyoung.awesometodo.arch.viewmodel.TodoItemViewModel
import io.github.landerlyoung.awesometodo.arch.viewmodel.TodoViewModel
import io.github.landerlyoung.awesometodo.databinding.ActivityTodoBinding
import io.github.landerlyoung.awesometodo.databinding.TodoItemBinding

/**
 * <pre>
 * Author: taylorcyang@tencent.com
 * Date:   2017-05-20
 * Time:   22:47
 * Life with Passion, Code with Creativity.
 * </pre>
 */
class TodoFragment : Fragment(), LifecycleRegistryOwner {
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = ActivityTodoBinding.inflate(
                inflater, container, false)
        val vm = ViewModelProviders.of(this).get(TodoViewModel::class.java)

        binding.viewModel = vm

        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = Adapter()

        vm.allItems.addOnListChangedCallback(
                object : ObservableList.OnListChangedCallback<ObservableArrayList<TodoEntity>>() {
                    override fun onItemRangeInserted(p0: ObservableArrayList<TodoEntity>, p1: Int, p2: Int) {
                    }

                    override fun onChanged(p0: ObservableArrayList<TodoEntity>) {
                    }

                    override fun onItemRangeMoved(p0: ObservableArrayList<TodoEntity>, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onItemRangeRemoved(p0: ObservableArrayList<TodoEntity>, p1: Int, p2: Int) {
                    }

                    override fun onItemRangeChanged(p0: ObservableArrayList<TodoEntity>, p1: Int, p2: Int) {
                    }
                })

        return binding.root
    }

    class ViewHolder(view: View, val vm: TodoItemViewModel) : RecyclerView.ViewHolder(view)

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {
        val todoItems: List<TodoEntity> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val binding = TodoItemBinding.inflate(LayoutInflater.from(context), parent, false)
            val vm = ViewModelProviders.of(this@TodoFragment).get(TodoItemViewModel::class.java)

            binding.viewModel = vm

            return ViewHolder(binding.root, vm)
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            val item = todoItems[position]
            viewHolder.vm.name = item.name
            viewHolder.vm.done = item.done

            DataBindingUtil.getBinding<ViewDataBinding>(viewHolder.itemView)
                    ?.executePendingBindings()
        }

        override fun getItemCount(): Int {
            return todoItems.size
        }
    }
}
