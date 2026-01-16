package com.dola.videospeedchanger.domain

import com.dola.videospeedchanger.data.model.VideoItem
import com.dola.videospeedchanger.data.repository.VideoRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.math.BigDecimal

class GetVideoListUseCaseTest {

    private val mockRepo = mockk<VideoRepository>()
    private val useCase = GetVideoListUseCase(mockRepo)

    @Test
    fun `invoke returns correct video list from repository`() {
        // Arrange
        val testVideos = listOf(
            VideoItem(
                id = "1",
                file = File("/test1.mp4"),
                displayName = "test1.mp4",
                duration = BigDecimal("20.5"),
                size = 1024,
                mimeType = "video/mp4"
            )
        )
        every { mockRepo.getVideoList() } returns testVideos

        // Act
        val result = useCase()

        // Assert
        assertEquals(1, result.size)
        assertEquals("test1.mp4", result[0].displayName)
    }
}
