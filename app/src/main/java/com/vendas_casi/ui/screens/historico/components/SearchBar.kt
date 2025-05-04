package com.vendas_casi.ui.screens.historico.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization

@Composable
fun SearchBar(
    searchTerm: String,
    onSearchTermChange: (String) -> Unit,
    onSearch: () -> Unit = {},
    modifier: Modifier = Modifier,
    placeholder: String = "Pesquisar...",
    leadingIcon: @Composable (() -> Unit)? = {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Pesquisar"
        )
    },
    trailingIcon: @Composable (() -> Unit)? = if (searchTerm.isNotEmpty()) {
        {
            IconButton(onClick = { onSearchTermChange("") }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Limpar pesquisa"
                )
            }
        }
    } else null
) {
    OutlinedTextField(
        value = searchTerm,
        onValueChange = onSearchTermChange,
        modifier = modifier,
        placeholder = { Text(placeholder) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.medium
    )
}