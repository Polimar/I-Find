package com.valcan.i_find.ui.navigation

object IFindDestinations {
    const val ARMADI_ROUTE = "armadi"
    const val VESTITI_ROUTE = "vestiti"
    const val VESTITI_BY_ARMADIO_ROUTE = "vestiti/{armadioId}"
    
    fun vestitiByArmadioRoute(armadioId: Long) = "vestiti/$armadioId"
} 