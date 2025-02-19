package com.valcan.i_find.ui.vestito

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valcan.i_find.data.model.Armadio
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVestitoDialog(
    armadi: List<Armadio>,
    onDismiss: () -> Unit,
    onConfirm: (nome: String, tipo: String, colore: String, posizione: String, selectedArmadioId: Long) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var colore by remember { mutableStateOf("") }
    var posizione by remember { mutableStateOf("") }

    // Stato per il dropdown degli armadi
    var expanded by remember { mutableStateOf(false) }
    // Se esistono armadi, selezioniamo come default il primo
    var selectedArmadioId by remember { mutableStateOf<Long?>(armadi.firstOrNull()?.id) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Aggiungi Vestito") },
        text = {
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                TextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = tipo,
                    onValueChange = { tipo = it },
                    label = { Text("Tipo") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = colore,
                    onValueChange = { colore = it },
                    label = { Text("Colore") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = posizione,
                    onValueChange = { posizione = it },
                    label = { Text("Posizione") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (armadi.isNotEmpty()) {
                    // Menu a tendina per la selezione dell'armadio
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = armadi.find { it.id == selectedArmadioId }?.nome ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Seleziona Armadio") },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            armadi.forEach { armadio ->
                                DropdownMenuItem(
                                    text = { Text(armadio.nome) },
                                    onClick = {
                                        selectedArmadioId = armadio.id
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                } else {
                    // Se non esistono armadi, mostra un messaggio e disabilita la conferma
                    Text(
                        text = "Nessun armadio presente. Creane uno prima di aggiungere un vestito.",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            if (armadi.isNotEmpty() &&
                nome.isNotBlank() && tipo.isNotBlank() &&
                colore.isNotBlank() && posizione.isNotBlank() &&
                selectedArmadioId != null
            ) {
                TextButton(
                    onClick = { onConfirm(nome, tipo, colore, posizione, selectedArmadioId!!) }
                ) {
                    Text("Conferma")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
} 