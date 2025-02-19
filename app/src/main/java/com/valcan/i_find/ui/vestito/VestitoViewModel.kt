package com.valcan.i_find.ui.vestito

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valcan.i_find.data.model.Vestito
import com.valcan.i_find.data.repository.VestitoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class VestitoViewModel @Inject constructor(
    private val repository: VestitoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VestitoUiState())
    val uiState: StateFlow<VestitoUiState> = _uiState

    init {
        loadVestiti()
    }

    private fun loadVestiti() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getAllVestiti()
                .catch { e ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
                .collect { vestiti ->
                    _uiState.update { 
                        it.copy(
                            vestiti = vestiti,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    fun loadVestitiByArmadio(armadioId: Long) {
        viewModelScope.launch {
            val vestiti = if (armadioId == -1L) {
                repository.getAllVestiti().first()
            } else {
                repository.getVestitiByArmadio(armadioId).first()
            }
            Log.d("VestitoViewModel", "vestiti caricati: $vestiti")
            _uiState.update { it.copy(vestiti = vestiti) }
        }
    }

    fun addVestito(
        nome: String,
        tipo: String,
        colore: String,
        posizione: String,
        armadioId: Long,
        photoUri: String? = null
    ) {
        viewModelScope.launch {
            try {
                val vestito = Vestito(
                    nome = nome,
                    tipo = tipo,
                    colore = colore,
                    posizione = posizione,
                    armadioId = armadioId
                )
                repository.insertVestito(vestito)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun updateVestito(vestito: Vestito) {
        viewModelScope.launch {
            try {
                repository.updateVestito(vestito)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteVestito(vestito: Vestito) {
        viewModelScope.launch {
            try {
                repository.deleteVestito(vestito)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
} 