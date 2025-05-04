package com.vendas_casi.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.vendas_casi.data.models.Venda
import java.io.File
import java.io.IOException

class VendaRepository(private val context: Context) {
    private val gson = Gson()
    private val fileName = "vendas.json"
    private val file by lazy { File(context.filesDir, fileName) }

    @Throws(IOException::class)
    fun salvarVenda(venda: Venda) {
        try {
            val vendas = carregarVendas().toMutableList()
            vendas.add(venda)
            file.writeText(gson.toJson(vendas))
        } catch (e: Exception) {
            throw IOException("Falha ao salvar venda: ${e.message}", e)
        }
    }

    @Throws(IOException::class, JsonSyntaxException::class)
    fun carregarVendas(): List<Venda> {
        return try {
            if (file.exists()) {
                val type = object : TypeToken<List<Venda>>() {}.type
                gson.fromJson(file.readText(), type) ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: JsonSyntaxException) {
            throw JsonSyntaxException("Erro ao analisar arquivo JSON: ${e.message}", e)
        } catch (e: Exception) {
            throw IOException("Falha ao carregar vendas: ${e.message}", e)
        }
    }

    @Throws(IOException::class)
    fun limparDados() {
        try {
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            throw IOException("Falha ao limpar dados: ${e.message}", e)
        }
    }
}