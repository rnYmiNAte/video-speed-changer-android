package com.dola.videospeedchanger.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dola.videospeedchanger.data.model.VideoItem
import com.dola.videospeedchanger.databinding.ItemVideoBinding

class VideoListAdapter(
    private val onVideoSelected: (VideoItem) -> Unit
) : RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    private var videos: List<VideoItem> = emptyList()

    fun submitList(newList: List<VideoItem>) {
        videos = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int = videos.size

    inner class VideoViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(video: VideoItem) {
            binding.tvName.text = video.displayName
            binding.tvDuration.text = "${video.duration}s"
            binding.tvSize.text = "${video.size / 1024 / 1024} MB"
            itemView.setOnClickListener { onVideoSelected(video) }
        }
    }
}
