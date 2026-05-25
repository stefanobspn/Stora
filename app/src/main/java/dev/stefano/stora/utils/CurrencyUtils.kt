package dev.stefano.stora.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {
    fun formatCurrency(amount: String): String {
        val cleanString = amount.replace(Regex("[^\\d]"), "")
        if (cleanString.isEmpty()) return ""
        
        val parsed = cleanString.toDoubleOrNull() ?: 0.0
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        formatter.maximumFractionDigits = 0
        return formatter.format(parsed).replace("Rp", "Rp ").trim()
    }

    fun parseCurrency(amount: String): String {
        return amount.replace(Regex("[^\\d]"), "")
    }
}
