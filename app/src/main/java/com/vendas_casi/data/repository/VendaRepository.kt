package com.vendas_casi.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vendas_casi.data.models.Venda
import java.io.File

class VendaRepository(private val context: Context) {
    private val gson = Gson()
    private val fileName = "vendas.json"
    private val file by lazy { File(context.filesDir, fileName) }

    fun salvarVenda(venda: Venda) {
        val vendas = carregarVendas().toMutableList()
        vendas.add(venda)
        file.writeText(gson.toJson(vendas))
    }

    fun carregarVendas(): List<Venda> {
        return if (file.exists()) {
            val type = object : TypeToken<List<Venda>>() {}.type
            gson.fromJson(file.readText(), type) ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun limparDados() {
        if (file.exists()) {
            file.delete()
        }
    }
}