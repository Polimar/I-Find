package com.valcan.i_find.ui.vestito

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.valcan.i_find.data.model.Vestito
import com.valcan.i_find.data.model.Armadio
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.ui.ExperimentalComposeUiApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditVestitoDialog(
    vestito: Vestito,
    armadi: List<Armadio>,
    onDismiss: () -> Unit,
    onConfirm: (updatedVestito: Vestito) -> Unit
) {
    // Campi precompilati con i dati del vestito esistente
    var nome by remember { mutableStateOf(vestito.nome) }
    var tipo by remember { mutableStateOf(vestito.tipo) }
    var colore by remember { mutableStateOf(vestito.colore) }
    var posizione by remember { mutableStateOf(vestito.posizione) }
    
    var selectedArmadioId by remember { mutableStateOf<Long?>(vestito.armadioId) }
    var expanded by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    // Inizializza lo state della foto, convertendo la stringa in Uri, se presente
    var photoUriState by remember { mutableStateOf<Uri?>(if (vestito.photoUri != null) Uri.parse(vestito.photoUri) else null) }
    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (!success) {
            photoUriState = null
        }
    }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modifica Vestito") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
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
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = armadi.find { it.id == selectedArmadioId }?.nome ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Seleziona Armadio") },
                            trailingIcon = { TrailingIcon(expanded = expanded) },
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
                    Text(
                        text = "Nessun armadio disponibile",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val uri = com.valcan.i_find.utils.FileUtils.createImageUri(context)
                            photoUriState = uri
                            photoLauncher.launch(uri)
                        }
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Scatta Foto")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Scatta Foto")
                    }
                    if (photoUriState != null) {
                        Image(
                            painter = rememberAsyncImagePainter(photoUriState),
                            contentDescription = "Thumbnail della foto",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (nome.isNotBlank() && tipo.isNotBlank() && colore.isNotBlank() && posizione.isNotBlank() && selectedArmadioId != null) {
                TextButton(
                    onClick = {
                        val updatedVestito = vestito.copy(
                            nome = nome,
                            tipo = tipo,
                            colore = colore,
                            posizione = posizione,
                            armadioId = selectedArmadioId!!,
                            photoUri = photoUriState?.toString()
                        )
                        onConfirm(updatedVestito)
                    }
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