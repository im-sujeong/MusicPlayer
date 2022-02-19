package com.sue.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sue.musicplayer.databinding.ItemPlayingListBinding
import com.sue.musicplayer.domain.model.MusicModel
import com.sue.musicplayer.extensions.loadImage

class ReleaseMusicLinearAdapter(
    val onItemClickListener: (musicModel: MusicModel) -> Unit
): ListAdapter<MusicModel, ReleaseMusicLinearAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(
        private val binding: ItemPlayingListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MusicModel) = with(binding){
            coverImageView.loadImage(item.coverImageUrl, 8f)
            artistNameTextView.text = item.artistName
            titleTextView.text = item.title

            root.setOnClickListener {
                onItemClickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPlayingListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MusicModel>() {
            override fun areItemsTheSame(
                oldItem: MusicModel,
                newItem: MusicModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MusicModel,
                newItem: MusicModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}