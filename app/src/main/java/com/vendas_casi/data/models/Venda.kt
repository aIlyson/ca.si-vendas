package com.vendas_casi.data.models

import java.text.SimpleDateFormat
import java.util.*

data class Venda(
    val id: Long = System.currentTimeMillis(),
    val data: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
    val produto: String,
    val valorUnitario: Double,
    val quantidade: Int,
    val valorTotal: Double,
    val formaPagamento: String,
    val vendedor: String
) {
    companion object {
        val produtosDisponiveis = listOf("Café normal", "Café com leite")

        fun getPrecoProduto(produto: String): Double {
            return when (produto) {
                "Café normal" -> 1.00
                "Café com leite" -> 1.50
                else -> throw IllegalArgumentException("Produto não encontrado: $produto")
            }
        }
    }
}