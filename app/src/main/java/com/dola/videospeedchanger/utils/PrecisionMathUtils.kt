package com.dola.videospeedchanger.utils

import java.math.BigDecimal
import java.math.RoundingMode

object PrecisionMathUtils {
    val ROUND_MODE = RoundingMode.HALF_UP
    const val SCALE = 6 // Default decimal precision for calculations

    fun toBigDecimal(value: String): BigDecimal {
        return try {
            BigDecimal(value).setScale(SCALE, ROUND_MODE)
        } catch (e: Exception) {
            BigDecimal.ZERO.setScale(SCALE, ROUND_MODE)
        }
    }

    fun multiply(a: String, b: String): String {
        return toBigDecimal(a).multiply(toBigDecimal(b)).toPlainString()
    }

    fun divide(a: String, b: String): String {
        return if (toBigDecimal(b) == BigDecimal.ZERO) "0.0"
        else toBigDecimal(a).divide(toBigDecimal(b), SCALE, ROUND_MODE).toPlainString()
    }
}
