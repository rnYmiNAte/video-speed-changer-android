package com.dola.videospeedchanger.presentation.editor.widget

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.dola.videospeedchanger.utils.PrecisionMathUtils

class DecimalInputEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var minValue: String? = null
    private var maxValue: String? = null

    init {
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            // Allow only numbers and single decimal point
            if (source.isEmpty()) return@InputFilter source
            val currentText = text?.toString() ?: ""
            if (source == "." && currentText.contains(".")) return@InputFilter ""
            source
        })
    }

    // Set valid range (BigDecimal-compatible strings)
    fun setRange(min: String?, max: String?) {
        minValue = min
        maxValue = max
    }

    // Get value as BigDecimal (or default if invalid)
    fun getValue(): String {
        val text = text?.toString() ?: "0.0"
        return if (isValid(text)) text else "0.0"
    }

    private fun isValid(value: String): Boolean {
        return try {
            val bd = PrecisionMathUtils.toBigDecimal(value)
            val min = minValue?.let { PrecisionMathUtils.toBigDecimal(it) }
            val max = maxValue?.let { PrecisionMathUtils.toBigDecimal(it) }
            (min == null || bd >= min) && (max == null || bd <= max)
        } catch (e: Exception) {
            false
        }
    }
}
