package com.dola.videospeedchanger.data.repository

import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import com.dola.videospeedchanger.data.model.VideoItem
import java.io.File
import java.math.BigDecimal

class VideoRepository(private val context: Context) {

    // Get all supported videos from device
    fun getVideoList(): List<VideoItem> {
        val videoList = mutableListOf<VideoItem>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.MIME_TYPE
        )

        context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.DATE_ADDED} DESC"
        )?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val pathCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            val durationCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val mimeTypeCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getString(idCol)
                val name = cursor.getString(nameCol)
                val path = cursor.getString(pathCol)
                val durationMs = cursor.getLong(durationCol)
                val size = cursor.getLong(sizeCol)
                val mimeType = cursor.getString(mimeTypeCol)

                // Convert ms to seconds with high precision
                val durationSec = BigDecimal(durationMs).divide(BigDecimal(1000), 6, BigDecimal.ROUND_HALF_UP)
                val file = File(path)

                if (file.exists() && isSupportedFormat(mimeType)) {
                    videoList.add(VideoItem(id, file, name, durationSec, size, mimeType))
                }
            }
        }
        return videoList
    }

    // Check if video format is supported
    private fun isSupportedFormat(mimeType: String): Boolean {
        return listOf("video/mp4", "video/mkv", "video/avi", "video/mov").contains(mimeType)
    }
}
