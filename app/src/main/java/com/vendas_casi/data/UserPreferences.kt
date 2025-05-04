package com.vendas_casi.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UserPreferences(context: Context) {
    private val sharedPref = context.getSharedPreferences("vendas_prefs", Context.MODE_PRIVATE)

    var vendedorName: String by mutableStateOf(
        sharedPref.getString("vendedor_name", "") ?: ""
    )
        private set

    fun saveVendedorName(name: String) {
        sharedPref.edit().putString("vendedor_name", name).apply()
        vendedorName = name
    }
}