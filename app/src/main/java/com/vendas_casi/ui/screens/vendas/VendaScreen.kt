package com.vendas_casi.ui.screens.vendas

import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vendas_casi.data.UserPreferences
import com.vendas_casi.data.repository.VendaRepository
import com.vendas_casi.ui.components.ConfirmModal
import com.vendas_casi.ui.components.Header
import com.vendas_casi.ui.screens.vendas.components.*
import com.vendas_casi.ui.theme.VendasCASITheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendaScreen(
    repository: VendaRepository,
    userPreferences: UserPreferences,
    onNavigateToHistorico: () -> Unit
) {
    VendasCASITheme {
        var produtoSelecionado by remember { mutableStateOf("") }
        var quantidade by remember { mutableStateOf("1") }
        var pagamentoSelecionado by remember { mutableStateOf("") }
        var vendedor by remember { mutableStateOf(userPreferences.vendedorName) }
        var mostrarDialogo by remember { mutableStateOf(false) }
        var mensagemDialogo by remember { mutableStateOf("") }
        var produtoExpandido by remember { mutableStateOf(false) }
        var pagamentoExpandido by remember { mutableStateOf(false) }
        val formasPagamento = listOf("Pix", "Dinheiro", "Cartão")
        val scrollState = rememberScrollState()

        LaunchedEffect(vendedor) {
            if (vendedor != userPreferences.vendedorName) {
                userPreferences.saveVendedorName(vendedor)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header()
                VendaForm(
                    produtoSelecionado = produtoSelecionado,
                    onProdutoSelecionadoChange = { produtoSelecionado = it },
                    produtoExpandido = produtoExpandido,
                    onProdutoExpandidoChange = { produtoExpandido = it },
                    quantidade = quantidade,
                    onQuantidadeChange = { quantidade = it },
                    pagamentoSelecionado = pagamentoSelecionado,
                    onPagamentoSelecionadoChange = { pagamentoSelecionado = it },
                    pagamentoExpandido = pagamentoExpandido,
                    onPagamentoExpandidoChange = { pagamentoExpandido = it },
                    formasPagamento = formasPagamento,
                    vendedor = vendedor,
                    onVendedorChange = { vendedor = it }
                )

                if (produtoSelecionado.isNotEmpty()) {
                    VendaList(produtoSelecionado, quantidade)
                }

                Spacer(modifier = Modifier.height(24.dp))

                ActionsBtn(
                    produtoSelecionado = produtoSelecionado,
                    quantidade = quantidade,
                    pagamentoSelecionado = pagamentoSelecionado,
                    vendedor = vendedor,
                    onRegistrar = {
                        if (validarCampos(produtoSelecionado, quantidade, pagamentoSelecionado, vendedor)) {
                            val venda = com.vendas_casi.data.models.Venda(
                                produto = produtoSelecionado,
                                valorUnitario = com.vendas_casi.data.models.Venda.getPrecoProduto(produtoSelecionado),
                                quantidade = quantidade.toInt(),
                                valorTotal = com.vendas_casi.data.models.Venda.getPrecoProduto(produtoSelecionado) * quantidade.toInt(),
                                formaPagamento = pagamentoSelecionado,
                                vendedor = vendedor
                            )

                            repository.salvarVenda(venda)
                            mensagemDialogo = "✅ Venda registrada!\n\n${venda.quantidade}x ${venda.produto}\nTotal: R$${"%.2f".format(venda.valorTotal)}"
                            mostrarDialogo = true

                            produtoSelecionado = ""
                            quantidade = "1"
                            pagamentoSelecionado = ""
                        } else {
                            mensagemDialogo = "⚠️ Preencha todos os campos!"
                            mostrarDialogo = true
                        }
                    },
                    onHistorico = onNavigateToHistorico
                )
            }

            if (mostrarDialogo) {
                ConfirmModal(
                    mensagem = mensagemDialogo,
                    onDismiss = { mostrarDialogo = false }
                )
            }
        }
    }
}

private fun validarCampos(
    produto: String,
    quantidade: String,
    pagamento: String,
    vendedor: String
): Boolean {
    return produto.isNotEmpty() &&
            quantidade.isNotEmpty() && quantidade.toIntOrNull() != null &&
            pagamento.isNotEmpty() &&
            vendedor.isNotEmpty()
}