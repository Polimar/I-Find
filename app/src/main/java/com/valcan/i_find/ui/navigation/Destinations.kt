package com.valcan.i_find.ui.navigation

sealed class Destinations(val route: String) {
    object ArmadioList : Destinations("armadioList")
    object VestitoList : Destinations("vestitoList/{armadioId}") {
        fun createRoute(armadioId: Long) = "vestitoList/$armadioId"
    }
    object AddArmadio : Destinations("addArmadio")
    object AddVestito : Destinations("addVestito/{armadioId}") {
        fun createRoute(armadioId: Long) = "addVestito/$armadioId"
    }
} 