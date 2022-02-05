package com.sue.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sue.musicplayer.R
import com.sue.musicplayer.databinding.ItemTopSongBinding
import com.sue.musicplayer.domain.model.MusicModel
import com.sue.musicplayer.extensions.loadImage
import kotlin.math.abs

class TopSongsAdapter(
    private val onItemClickListener: (musicModel: MusicModel) -> Unit
): ListAdapter<MusicModel, TopSongsAdapter.ViewHolder>(diffUtil){
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MusicModel>() {
            override fun areItemsTheSame(oldItem: MusicModel, newItem: MusicModel): Boolean {
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

    inner class ViewHolder(
        private val binding: ItemTopSongBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MusicModel) = with(binding) {
            coverImageView.loadImage(item.coverImageUrl, 8f)
            titleTextView.text = item.title
            artistNameTextView.text = item.artistName
            numberTextView.text = item.number.toString()

            item.rank?.let {
                rankTextView.isVisible = item.rank != 0
                rankTextView.text = abs(item.rank).toString()
                rankTextView.setCompoundDrawablesWithIntrinsicBounds(
                    if( item.rank < 0 ) {
                        ContextCompat.getDrawable(rankTextView.context, R.drawable.ic_down)
                    }else {
                        ContextCompat.getDrawable(rankTextView.context, R.drawable.ic_up)
                    }, null, null, null
                )
            }

            root.setOnClickListener {
                onItemClickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTopSongBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}