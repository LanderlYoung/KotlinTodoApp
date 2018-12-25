package io.github.landerlyoung.awesometodo.paging

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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

    private fun keyedPaging(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        val adapter = object : PagedListAdapter<String, androidx.recyclerview.widget.RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
                val text = LayoutInflater.from(parent.context).inflate(android.R.layout.test_list_item, parent, false)

                return object : androidx.recyclerview.widget.RecyclerView.ViewHolder(text) {}
            }

            override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
                (holder.itemView as TextView).text = getItem(position)
            }
        }

        adapter.submitList(keyedPagedList())

        recyclerView.adapter = adapter
    }

    @SuppressLint("RestrictedApi")
    private fun keyedPagedList() = PagedList.Builder<String, String>(
            keyedDataSource(), 10)
            .setFetchExecutor(ArchTaskExecutor.getIOThreadExecutor())
            .setNotifyExecutor(ArchTaskExecutor.getMainThreadExecutor())
            .setInitialKey(null)
            .build()

    val h = Handler()

    private fun keyedDataSource() = object : PageKeyedDataSource<String, String>() {
        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, String>) {
            Log.i(TAG, "loadAfter ${params.key}")

            val pageIndex = Integer.parseInt(params.key.split("-")[1])

            h.postDelayed({

            callback.onResult(
                    (0 until 10).map {
                        "${params.key} item $it"
                    },
                    "page-${pageIndex + 1}")
            }, 2000)
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
