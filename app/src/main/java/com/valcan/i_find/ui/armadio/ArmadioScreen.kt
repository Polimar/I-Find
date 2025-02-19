package com.valcan.i_find.ui.armadio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valcan.i_find.R
import com.valcan.i_find.data.model.Armadio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArmadioScreen(
    onNavigateToVestiti: (Long) -> Unit,
    viewModel: ArmadioViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Aggiungi Armadio")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(uiState.armadi) { armadio ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onNavigateToVestiti(armadio.id) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = armadio.nome,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = armadio.posizione,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddArmadioDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { nome, posizione ->
                viewModel.addArmadio(nome, posizione)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun ArmadioItem(
    armadio: Armadio,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = armadio.nome,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = armadio.posizione,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AddArmadioDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var posizione by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Aggiungi armadio") },
        text = {
            Column {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = posizione,
                    onValueChange = { posizione = it },
                    label = { Text("Posizione") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(nome, posizione) },
                enabled = nome.isNotBlank() && posizione.isNotBlank()
            ) {
                Text("Conferma")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
} 