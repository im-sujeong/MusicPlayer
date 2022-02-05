package com.sue.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sue.musicplayer.databinding.ItemRecommendBinding
import com.sue.musicplayer.domain.model.RecommendMusicModel
import com.sue.musicplayer.extensions.loadImage

class RecommendMusicAdapter: ListAdapter<RecommendMusicModel, RecommendMusicAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(
        private val binding: ItemRecommendBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecommendMusicModel) = with(binding){
            coverImageView.loadImage(
                item.imageUrl,
                16f
            )

            subTitleTextView.text = item.subTitle
            titleTextView.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RecommendMusicModel>() {
            override fun areItemsTheSame(
                oldItem: RecommendMusicModel,
                newItem: RecommendMusicModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RecommendMusicModel,
                newItem: RecommendMusicModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}