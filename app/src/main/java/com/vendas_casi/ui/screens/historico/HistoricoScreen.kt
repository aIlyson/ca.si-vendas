package com.vendas_casi.ui.screens.historico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vendas_casi.data.models.Venda
import com.vendas_casi.ui.screens.historico.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoScreen(
    vendas: List<Venda>,
    onBack: () -> Unit
) {
    var searchTerm by remember { mutableStateOf("") }

    val filteredSales = vendas.filter { venda ->
        searchTerm.isEmpty() ||
                venda.produto.contains(searchTerm, ignoreCase = true) ||
                venda.vendedor.contains(searchTerm, ignoreCase = true) ||
                venda.data.contains(searchTerm)
    }

    val salesByMonth = filteredSales.groupBy { venda ->
        val dateParts = venda.data.split("/")
        "${dateParts[1]}/${dateParts[2]}"
    }.toSortedMap(compareByDescending { it })

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Histórico de Vendas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                searchTerm = searchTerm,
                onSearchTermChange = { searchTerm = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            if (filteredSales.isEmpty()) {
                EmptyState(
                    hasSearchTerm = searchTerm.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    salesByMonth.forEach { (monthYear, sales) ->
                        item {
                            MonthGroup(monthYear = monthYear, sales = sales)
                        }
                    }
                }
            }
        }
    }
}