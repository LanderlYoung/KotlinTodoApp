package io.github.landerlyoung.awesometodo.paging

import android.arch.core.executor.ArchTaskExecutor
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import android.arch.paging.PagedListAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import io.github.landerlyoung.awesometodo.R

class PagingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagind)

        keyedPaging(findViewById(R.id.recycler_view))
    }

    private fun keyedPaging(recyclerView: RecyclerView) {
        val adapter = object : PagedListAdapter<String, RecyclerView.ViewHolder>(object : DiffCallback<String?>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val text = LayoutInflater.from(parent.context).inflate(android.R.layout.test_list_item, parent, false)

                return object : RecyclerView.ViewHolder(text) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                (holder.itemView as TextView).text = getItem(position)
            }
        }

        adapter.setList(keyedPagedList())

        recyclerView.adapter = adapter
    }

    private fun keyedPagedList() = PagedList.Builder<String, String>(
            keyedDataSource(), 10)
            .setBackgroundThreadExecutor(ArchTaskExecutor.getIOThreadExecutor())
            .setMainThreadExecutor(ArchTaskExecutor.getMainThreadExecutor())
            .setInitialKey(null)
            .build()

    private fun keyedDataSource() = object : PageKeyedDataSource<String, String>() {
        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, String>) {
            Log.i(TAG, "loadAfter ${params.key}")

            val pageIndex = Integer.parseInt(params.key.split("-")[1])

            callback.onResult(
                    (0 until 10).map {
                        "${params.key} item $it"
                    },
                    "page-${pageIndex + 1}")
        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, String>) {
            Log.i(TAG, "loadBefore ${params.key}")

            // we can't load before
            callback.onResult(listOf(), null)
        }

        override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, String>) {
            Log.i(TAG, "loadInitial")

            callback.onResult(
                    (0 until 10).map {
                        "page-0 item $it"
                    },
                    null,
                    "page-1")
        }
    }

    companion object {
        const val TAG = "PagingActivity"
    }
}
