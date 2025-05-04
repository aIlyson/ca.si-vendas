package com.vendas_casi.ui.screens.vendas.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ActionsBtn(
    produtoSelecionado: String,
    quantidade: String,
    pagamentoSelecionado: String,
    vendedor: String,
    onRegistrar: () -> Unit,
    onHistorico: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onRegistrar,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            enabled = produtoSelecionado.isNotEmpty() &&
                    quantidade.isNotEmpty() && quantidade.toIntOrNull() != null &&
                    pagamentoSelecionado.isNotEmpty() &&
                    vendedor.isNotEmpty()
        ) {
            Text("REGISTRAR VENDA", fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
            onClick = onHistorico,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("VER HISTÓRICO", fontWeight = FontWeight.Bold)
        }
    }
}