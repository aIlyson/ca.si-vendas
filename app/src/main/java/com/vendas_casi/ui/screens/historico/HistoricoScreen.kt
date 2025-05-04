package com.vendas_casi.ui.screens.historico

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vendas_casi.data.models.Venda
import com.vendas_casi.ui.screens.historico.components.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoricoScreen(
    vendas: List<Venda>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchTerm by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val filteredSales = remember(vendas, searchTerm, selectedDate) {
        if (searchTerm.isEmpty() && selectedDate == null) {
            vendas
        } else {
            vendas.filter { venda ->
                val matchesSearch = searchTerm.isEmpty() ||
                        venda.produto.lowercase().contains(searchTerm.lowercase()) ||
                        venda.vendedor.lowercase().contains(searchTerm.lowercase()) ||
                        venda.data.contains(searchTerm)

                val matchesDate = selectedDate?.let { date ->
                    val (day, month, year) = venda.data.split("/").map { it.toIntOrNull() ?: 0 }
                    date.dayOfMonth == day && date.monthValue == month && date.year == year
                } ?: true

                matchesSearch && matchesDate
            }
        }
    }

    val salesByMonth = remember(filteredSales) {
        filteredSales.groupBy { venda ->
            venda.data.split("/").let { parts ->
                if (parts.size >= 3) "${parts[1]}/${parts[2]}" else "Data inválida"
            }
        }.toSortedMap(compareByDescending { it })
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            selectedDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        selectedDate = null
                        showDatePicker = false
                    }
                ) {
                    Text("Limpar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Histórico de Vendas", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                actions = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Filtrar por data"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SearchBar(
                    searchTerm = searchTerm,
                    onSearchTermChange = { searchTerm = it },
                    modifier = Modifier.weight(1f)
                )

                FilterChip(
                    selected = selectedDate != null,
                    onClick = { showDatePicker = true },
                    label = {
                        Text(
                            selectedDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "Data"
                        )
                    },
                    leadingIcon = if (selectedDate != null) {
                        @Composable {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Limpar filtro",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    }
                )
            }

            when {
                vendas.isEmpty() -> {
                    EmptyState(
                        modifier = Modifier.fillMaxSize(),
                        hasSearchTerm = false
                    )
                }
                filteredSales.isEmpty() -> {
                    EmptyState(
                        modifier = Modifier.fillMaxSize(),
                        hasSearchTerm = true
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        salesByMonth.forEach { (monthYear, sales) ->
                            item(key = monthYear) {
                                MonthGroup(
                                    monthYear = monthYear,
                                    sales = sales
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}