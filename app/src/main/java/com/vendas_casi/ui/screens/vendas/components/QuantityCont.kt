package com.vendas_casi.ui.screens.vendas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContadorQuantidade(
    quantidade: String,
    onQuantidadeChange: (String) -> Unit
) {
    Column {
        Text(
            text = "Quantidade",
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 2.dp)
                .padding(horizontal = 20.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        val current = quantidade.toIntOrNull() ?: 1
                        if (current > 1) onQuantidadeChange((current - 1).toString())
                    }
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Remove,
                    contentDescription = "Reduzir",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }

            TextField(
                value = quantidade,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.toIntOrNull() != null) {
                        onQuantidadeChange(newValue)
                    }
                },
                modifier = Modifier.width(60.dp),
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = textColors(),
                singleLine = true
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        val current = quantidade.toIntOrNull() ?: 1
                        onQuantidadeChange((current + 1).toString())
                    }
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Aumentar",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}