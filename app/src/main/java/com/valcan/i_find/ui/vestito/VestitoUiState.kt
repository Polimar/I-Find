package com.valcan.i_find.ui.vestito

import com.valcan.i_find.data.model.Vestito

data class VestitoUiState(
    val vestiti: List<Vestito> = emptyList(),
    val vestitiByArmadio: Map<Long, List<Vestito>> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
) 