package com.example.findmyage.Adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.findmyage.models.New

class newsAdapter: PagingDataAdapter<New, RecyclerView.ViewHolder>(REPO_OPERATOR) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = getItem(position)
        if(news != null){
            (holder as newsViewHolder).bind(news)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return newsViewHolder.create(parent)
    }

    companion object {
        private val REPO_OPERATOR = object : DiffUtil.ItemCallback<New>(){
            override fun areItemsTheSame(oldItem: New, newItem: New): Boolean {
             return oldItem.news_id == newItem.news_id
            }

            override fun areContentsTheSame(oldItem: New, newItem: New): Boolean {
             return  oldItem == newItem
            }

        }
    }


}