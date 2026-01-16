package com.dola.videospeedchanger.presentation.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthenica.ffmpegkit.FFmpegSession
import com.arthenica.ffmpegkit.ReturnCode
import com.dola.videospeedchanger.data.local.PreferencesManager
import com.dola.videospeedchanger.data.model.SpeedAdjustment
import com.dola.videospeedchanger.data.model.VideoItem
import com.dola.videospeedchanger.domain.ProcessVideoUseCase
import com.dola.videospeedchanger.utils.FileUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoEditorViewModel(
    private val video: VideoItem,
    private val processVideoUseCase: ProcessVideoUseCase,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    // UI State
    private val _tempo = MutableStateFlow(preferencesManager.getLastAdjustment().tempo.toString())
    val tempo: StateFlow<String> = _tempo

    private val _startPos = MutableStateFlow(preferencesManager.getLastAdjustment().startPosition.toString())
    val startPos: StateFlow<String> = _startPos

    private val _endPos = MutableStateFlow(preferencesManager.getLastAdjustment().endPosition.toString())
    val endPos: StateFlow<String> = _endPos

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing

    private val _processingResult = MutableStateFlow<String?>(null)
    val processingResult: StateFlow<String?> = _processingResult

    // Update input values
    fun updateTempo(newValue: String) { _tempo.value = newValue }
    fun updateStartPos(newValue: String) { _startPos.value = newValue }
    fun updateEndPos(newValue: String) { _endPos.value = newValue }

    // Trigger video processing
    fun processVideo() {
        viewModelScope.launch {
            _isProcessing.value = true
            _processingResult.value = null

            val adjustment = SpeedAdjustment(
                tempo = _tempo.value.toBigDecimal(),
                startPosition = _startPos.value.toBigDecimal(),
                endPosition = _endPos.value.toBigDecimal()
            )

            if (adjustment.isValid(video.duration)) {
                preferencesManager.saveLastAdjustment(adjustment)
                val outputDir = FileUtils.getOutputDirectory(context = null) // Pass context in DI setup
                val session: FFmpegSession? = processVideoUseCase(video, adjustment, outputDir)

                _processingResult.value = when {
                    session == null -> "Invalid parameters"
                    ReturnCode.isSuccess(session.returnCode) -> "Saved to: ${outputDir.path}"
                    else -> "Error: ${session.failStackTrace}"
                }
            } else {
                _processingResult.value = "Invalid input (check range/start < end)"
            }

            _isProcessing.value = false
        }
    }
}
