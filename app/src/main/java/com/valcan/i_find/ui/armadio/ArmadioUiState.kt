package com.valcan.i_find.ui.armadio

import com.valcan.i_find.data.model.Armadio

data class ArmadioUiState(
    val armadi: List<Armadio> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 