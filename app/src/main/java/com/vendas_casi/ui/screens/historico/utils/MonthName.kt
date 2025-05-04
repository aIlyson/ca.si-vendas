package com.vendas_casi.ui.screens.historico.utils

import java.time.Month
import java.util.Locale
import android.os.Build

fun monthName(monthNumber: Int): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        try {
            Month.of(monthNumber).getDisplayName(java.time.format.TextStyle.FULL, Locale("pt", "BR"))
        } catch (e: Exception) {
            ""
        }
    } else {
        val monthNames = listOf(
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        )
        monthNames.getOrNull(monthNumber - 1) ?: ""
    }
}
