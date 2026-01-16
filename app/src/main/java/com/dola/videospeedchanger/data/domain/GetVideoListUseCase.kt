package com.dola.videospeedchanger.domain

import com.dola.videospeedchanger.data.model.VideoItem
import com.dola.videospeedchanger.data.repository.VideoRepository

class GetVideoListUseCase(private val repository: VideoRepository) {
    operator fun invoke(): List<VideoItem> {
        return repository.getVideoList()
    }
}
