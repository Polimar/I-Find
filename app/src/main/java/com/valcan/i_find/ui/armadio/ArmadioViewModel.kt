package com.valcan.i_find.ui.armadio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valcan.i_find.data.model.Armadio
import com.valcan.i_find.data.repository.ArmadioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArmadioViewModel @Inject constructor(
    private val repository: ArmadioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArmadioUiState())
    val uiState: StateFlow<ArmadioUiState> = _uiState

    init {
        loadArmadi()
    }

    private fun loadArmadi() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getAllArmadi()
                .catch { e ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
                .collect { armadi ->
                    _uiState.update { 
                        it.copy(
                            armadi = armadi,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    fun addArmadio(nome: String, posizione: String) {
        viewModelScope.launch {
            try {
                val armadio = Armadio(nome = nome, posizione = posizione)
                repository.insertArmadio(armadio)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun updateArmadio(armadio: Armadio) {
        viewModelScope.launch {
            try {
                repository.updateArmadio(armadio)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }

    fun deleteArmadio(armadio: Armadio) {
        viewModelScope.launch {
            try {
                repository.deleteArmadio(armadio)
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            }
        }
    }
} 