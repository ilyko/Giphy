package com.ilyko.giphy.features.main.search.presentation.common

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ilyko.giphy.features.main.search.data.api.entity.GifObject

class SearchAdapter(
) : PagedListAdapter<GifObject, GifViewHolder>(SearchDifCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        return GifViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SearchDifCallback : DiffUtil.ItemCallback<GifObject>() {
    override fun areItemsTheSame(oldItem: GifObject, newItem: GifObject): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GifObject, newItem: GifObject): Boolean {
        return oldItem == newItem
    }
}