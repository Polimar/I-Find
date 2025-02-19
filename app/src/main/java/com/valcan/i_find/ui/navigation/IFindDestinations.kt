package com.valcan.i_find.ui.navigation

object IFindDestinations {
    const val HOME_ROUTE = "home"
    const val ARMADI_ROUTE = "armadi"
    const val VESTITI_ROUTE = "vestiti"
    const val VESTITI_BY_ARMADIO_ROUTE = "vestiti/{armadioId}"
    const val SEARCH_ROUTE = "search"
    const val SETTINGS_ROUTE = "settings"
    
    fun vestitiByArmadioRoute(armadioId: Long) = "vestiti/$armadioId"
} 