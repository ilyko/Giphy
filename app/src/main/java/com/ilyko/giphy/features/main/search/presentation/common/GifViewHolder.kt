package com.ilyko.giphy.features.main.search.presentation.common


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ilyko.giphy.R
import com.ilyko.giphy.features.main.search.data.api.entity.GifObject
import com.ilyko.giphy.databinding.ItemGifBinding

class GifViewHolder(
    private val binding: ItemGifBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(gif: GifObject?) {
        gif?.images?.fixedHeight?.url?.let {
            Glide.with(binding.gif)
                .asGif()
                .placeholder(R.mipmap.ic_launcher)
                .transition(DrawableTransitionOptions.withCrossFade())
                .load(it)
                .into(binding.gif)
        }
    }

    companion object {

        fun create(
            parent: ViewGroup
        ): GifViewHolder {
            val itemBinding =
                ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return GifViewHolder(itemBinding)
        }
    }
}