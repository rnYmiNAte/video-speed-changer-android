package com.dola.videospeedchanger.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.arthenica.ffmpegkit.ReturnCode
import com.dola.videospeedchanger.data.model.SpeedAdjustment
import com.dola.videospeedchanger.data.model.VideoItem
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class VideoProcessorInstrumentedTest {

    private lateinit var context: Context
    private lateinit var testInputFile: File
    private lateinit var testOutputFile: File

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        // Copy test video from assets to local storage (simplified)
        testInputFile = File(context.cacheDir, "test_input.mp4")
        testOutputFile = File(context.cacheDir, "test_output.mp4")
    }

    @Test
    fun `process returns success for valid input`() {
        // Arrange
        val testVideo = VideoItem(
            id = "1",
            file = testInputFile,
            displayName = "test_input.mp4",
            duration = BigDecimal("5.0"),
            size = testInputFile.length(),
            mimeType = "video/mp4"
        )
        val adjustment = SpeedAdjustment(
            tempo = BigDecimal("1.5"),
            startPosition = BigDecimal("0.0"),
            endPosition = BigDecimal("5.0")
        )

        // Act
        val session = VideoProcessor.process(testVideo, adjustment, testOutputFile)
        session.waitForCompletion()

        // Assert
        assertEquals(ReturnCode.SUCCESS, session.returnCode)
        assertTrue(testOutputFile.exists())
    }

    @After
    fun cleanup() {
        testInputFile.delete()
        testOutputFile.delete()
    }
}
