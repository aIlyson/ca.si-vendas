package com.vendas_casi.ui.screens.vendas.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendaForm(
    produtoSelecionado: String,
    onProdutoSelecionadoChange: (String) -> Unit,
    produtoExpandido: Boolean,
    onProdutoExpandidoChange: (Boolean) -> Unit,
    quantidade: String,
    onQuantidadeChange: (String) -> Unit,
    pagamentoSelecionado: String,
    onPagamentoSelecionadoChange: (String) -> Unit,
    pagamentoExpandido: Boolean,
    onPagamentoExpandidoChange: (Boolean) -> Unit,
    formasPagamento: List<String>,
    vendedor: String,
    onVendedorChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = produtoExpandido,
                onExpandedChange = onProdutoExpandidoChange
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = produtoSelecionado,
                    onValueChange = {},
                    label = { Text("Produto", color = MaterialTheme.colorScheme.onSurface) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = produtoExpandido)
                    },
                    colors = textColors(),
                    shape = RoundedCornerShape(8.dp)
                )

                ExposedDropdownMenu(
                    expanded = produtoExpandido,
                    onDismissRequest = { onProdutoExpandidoChange(false) }
                ) {
                    com.vendas_casi.data.models.Venda.produtosDisponiveis.forEach { produto ->
                        DropdownMenuItem(
                            text = { Text(produto, color = MaterialTheme.colorScheme.onSurface) },
                            onClick = {
                                onProdutoSelecionadoChange(produto)
                                onProdutoExpandidoChange(false)
                            }
                        )
                    }
                }
            }

            ContadorQuantidade(quantidade, onQuantidadeChange)

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 1.dp
            )

            ExposedDropdownMenuBox(
                expanded = pagamentoExpandido,
                onExpandedChange = onPagamentoExpandidoChange
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = pagamentoSelecionado,
                    onValueChange = {},
                    label = { Text("Forma de Pagamento", color = MaterialTheme.colorScheme.onSurface) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = pagamentoExpandido)
                    },
                    colors = textColors(),
                    shape = RoundedCornerShape(8.dp)
                )

                ExposedDropdownMenu(
                    expanded = pagamentoExpandido,
                    onDismissRequest = { onPagamentoExpandidoChange(false) }
                ) {
                    formasPagamento.forEach { pagamento ->
                        DropdownMenuItem(
                            text = { Text(pagamento, color = MaterialTheme.colorScheme.onSurface) },
                            onClick = {
                                onPagamentoSelecionadoChange(pagamento)
                                onPagamentoExpandidoChange(false)
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = vendedor,
                onValueChange = onVendedorChange,
                label = { Text("Vendedor", color = MaterialTheme.colorScheme.onSurface) },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (vendedor.isNotEmpty()) {
                        IconButton(onClick = { onVendedorChange("") }) {
                            Icon(
                                Icons.Default.Close,
                                "Limpar vendedor",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                },
                colors = textColors(outlined = true),
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}