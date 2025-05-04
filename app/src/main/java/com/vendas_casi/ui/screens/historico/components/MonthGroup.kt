package com.vendas_casi.ui.screens.historico.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vendas_casi.data.models.Venda
import com.vendas_casi.ui.screens.historico.utils.monthName

@Composable
fun MonthGroup(
    monthYear: String,
    sales: List<Venda>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Mês: ${monthName(monthYear.split("/")[0].toInt())}/${monthYear.split("/")[1]}",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )

        sales.forEach { sale ->
            Item(venda = sale)
        }

        Divider(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )
    }
}