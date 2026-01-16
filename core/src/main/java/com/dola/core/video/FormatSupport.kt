package com.dola.core.video

object FormatSupport {
    // Supported video file extensions (lowercase for consistency)
    val SUPPORTED_VIDEO_EXTENSIONS = setOf("mp4", "mkv", "avi", "mov", "flv", "webm")

    // Supported video MIME types
    val SUPPORTED_VIDEO_MIME_TYPES = mapOf(
        "mp4" to "video/mp4",
        "mkv" to "video/x-matroska",
        "avi" to "video/x-msvideo",
        "mov" to "video/quicktime",
        "flv" to "video/x-flv",
        "webm" to "video/webm"
    )

    // Supported output formats (limited to most compatible options)
    val SUPPORTED_OUTPUT_FORMATS = setOf("mp4", "webm")

    // Default output format
    const val DEFAULT_OUTPUT_FORMAT = "mp4"

    /**
     * Check if a file extension is supported
     */
    fun isExtensionSupported(extension: String): Boolean {
        return SUPPORTED_VIDEO_EXTENSIONS.contains(extension.lowercase())
    }

    /**
     * Check if a MIME type is supported
     */
    fun isMimeTypeSupported(mimeType: String): Boolean {
        return SUPPORTED_VIDEO_MIME_TYPES.values.contains(mimeType.lowercase())
    }

    /**
     * Get MIME type from file extension (or null if unsupported)
     */
    fun getMimeTypeFromExtension(extension: String): String? {
        return SUPPORTED_VIDEO_MIME_TYPES[extension.lowercase()]
    }

    /**
     * Check if an output format is allowed
     */
    fun isOutputFormatSupported(format: String): Boolean {
        return SUPPORTED_OUTPUT_FORMATS.contains(format.lowercase())
    }
}
