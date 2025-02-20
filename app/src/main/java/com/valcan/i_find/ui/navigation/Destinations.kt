package com.valcan.i_find.ui.navigation

sealed class Destinations(val route: String) {
    object Home : Destinations("home")
    object Vestiti : Destinations("vestiti")
    object Armadi : Destinations("armadi")
    object Search : Destinations("search")
    object Settings : Destinations("settings")
    
    // Per la navigazione ai vestiti di un armadio specifico
    object VestitiInArmadio : Destinations("vestiti/{armadioId}") {
        fun createRoute(armadioId: Long) = "vestiti/$armadioId"
    }
} 