package com.jimbo.clicker.util

import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.icu.number.Precision
import java.util.Locale

/**
 * Extension function to format numbers with commas and the letter K (for example)
 */
fun Double.numberFormat(): String {
    return NumberFormatter.with()
        .notation(Notation.compactShort())
        .precision(Precision.maxSignificantDigits(4))
        .locale(Locale.getDefault())
        .format(this)
        .toString()

}