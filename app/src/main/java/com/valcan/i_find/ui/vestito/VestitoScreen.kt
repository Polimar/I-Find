package com.valcan.i_find.ui.vestito

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valcan.i_find.data.model.Vestito
import com.valcan.i_find.data.model.Armadio
import com.valcan.i_find.ui.armadio.ArmadioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VestitoScreen(
    armadioId: Long,
    onNavigateBack: () -> Unit,
    viewModel: VestitoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    val armadioViewModel: ArmadioViewModel = hiltViewModel()
    val armadioState by armadioViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(armadioId) {
        // Se armadioId è -1, ci aspettiamo che il repository restituisca tutti i vestiti
        viewModel.loadVestitiByArmadio(armadioId)
    }

    // Se armadioId == -1 significa "tutti" altrimenti filtra per armadio specifico
    val filteredVestiti = if (armadioId == -1L) uiState.vestiti else uiState.vestiti.filter { it.armadioId == armadioId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "I miei Vestiti",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Torna indietro",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Aggiungi Vestito",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredVestiti) { vestito ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = vestito.nome,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${vestito.tipo} - ${vestito.colore}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Posizione: ${vestito.posizione}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddVestitoDialog(
            armadi = armadioState.armadi,
            onDismiss = { showAddDialog = false },
            onConfirm = { nome, tipo, colore, posizione, selectedArmadioId ->
                viewModel.addVestito(nome, tipo, colore, posizione, selectedArmadioId)
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