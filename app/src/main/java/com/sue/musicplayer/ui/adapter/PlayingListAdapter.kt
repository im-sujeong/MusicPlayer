package com.sue.musicplayer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sue.musicplayer.R
import com.sue.musicplayer.databinding.ItemPlayingListBinding
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.extensions.loadImage

class PlayingListAdapter(
    private val onItemClickListener: (music: PlayingMusicModel) -> Unit
): ListAdapter<PlayingMusicModel, PlayingListAdapter.ViewHolder>(diffUtil) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PlayingMusicModel>() {
            override fun areItemsTheSame(oldItem: PlayingMusicModel, newItem: PlayingMusicModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PlayingMusicModel,
                newItem: PlayingMusicModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(
        private val binding: ItemPlayingListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlayingMusicModel) = with(binding){
            coverImageView.loadImage(item.coverImageUrl, 8f)
            titleTextView.text = item.title
            artistNameTextView.text = item.artistName

            equalizerImageView.setAnimation("equalizer.json")

            if( item.isSelected ) {
                playButton.isVisible = false
                equalizerImageView.isVisible = true

                if( item.isPlaying ) {
                    equalizerImageView.playAnimation()
                }else {
                    equalizerImageView.cancelAnimation()
                    equalizerImageView.frame = 0
                }

                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.darkBlueGrey))
            }else {
                equalizerImageView.cancelAnimation()

                playButton.isVisible = true
                equalizerImageView.isVisible = false

                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.darkNavyBlue))
            }

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
}