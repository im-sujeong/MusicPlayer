package com.sue.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sue.musicplayer.databinding.ItemNewReleaseBinding
import com.sue.musicplayer.domain.model.MusicModel
import com.sue.musicplayer.extensions.loadImage

class ReleaseMusicAdapter(
    val onItemClickListener: (musicModel: MusicModel) -> Unit
): ListAdapter<MusicModel, ReleaseMusicAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(
        private val binding: ItemNewReleaseBinding
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
        return ViewHolder(ItemNewReleaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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