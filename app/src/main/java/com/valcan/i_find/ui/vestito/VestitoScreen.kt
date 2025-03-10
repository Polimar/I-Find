package com.valcan.i_find.ui.vestito

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valcan.i_find.data.model.Vestito

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VestitoScreen(
    armadioId: Long,
    onNavigateBack: () -> Unit,
    viewModel: VestitoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(armadioId) {
        viewModel.loadVestitiByArmadio(armadioId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vestiti nell'armadio") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Torna indietro")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Aggiungi Vestito")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(uiState.vestiti) { vestito ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = vestito.nome,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${vestito.tipo} - ${vestito.colore}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = vestito.posizione,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddVestitoDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { nome, tipo, colore, posizione ->
                viewModel.addVestito(nome, tipo, colore, posizione, armadioId)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun VestitoItem(vestito: Vestito) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = vestito.nome,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tipo: ${vestito.tipo}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Colore: ${vestito.colore}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Posizione: ${vestito.posizione}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun AddVestitoDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var colore by remember { mutableStateOf("") }
    var posizione by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Aggiungi vestito") },
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
                    value = tipo,
                    onValueChange = { tipo = it },
                    label = { Text("Tipo") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = colore,
                    onValueChange = { colore = it },
                    label = { Text("Colore") },
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
                onClick = { onConfirm(nome, tipo, colore, posizione) },
                enabled = nome.isNotBlank() && tipo.isNotBlank() && 
                         colore.isNotBlank() && posizione.isNotBlank()
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