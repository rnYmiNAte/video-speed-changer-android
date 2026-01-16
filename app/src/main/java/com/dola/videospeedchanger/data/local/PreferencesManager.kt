package com.dola.videospeedchanger.data.local

import android.content.Context
import android.content.SharedPreferences
import java.math.BigDecimal

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("video_speed_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_TEMPO = "last_tempo"
        private const val KEY_LAST_START = "last_start"
        private const val KEY_LAST_END = "last_end"
    }

    fun saveLastAdjustment(adjustment: SpeedAdjustment) {
        prefs.edit()
            .putString(KEY_LAST_TEMPO, adjustment.tempo.toString())
            .putString(KEY_LAST_START, adjustment.startPosition.toString())
            .putString(KEY_LAST_END, adjustment.endPosition.toString())
            .apply()
    }

    fun getLastAdjustment(): SpeedAdjustment {
        val tempo = prefs.getString(KEY_LAST_TEMPO, "1.0")?.let { BigDecimal(it) } ?: BigDecimal.ONE
        val start = prefs.getString(KEY_LAST_START, "0.0")?.let { BigDecimal(it) } ?: BigDecimal.ZERO
        val end = prefs.getString(KEY_LAST_END, "10.0")?.let { BigDecimal(it) } ?: BigDecimal.TEN
        return SpeedAdjustment(tempo, start, end)
    }
}
