package com.dola.videospeedchanger.data.model

import java.io.File
import java.math.BigDecimal

data class VideoItem(
    val id: String, // Unique identifier (e.g., media store ID)
    val file: File,
    val displayName: String,
    val duration: BigDecimal, // Total duration in seconds (high-precision)
    val size: Long, // File size in bytes
    val mimeType: String
)
