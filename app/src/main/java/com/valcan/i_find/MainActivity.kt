package com.valcan.i_find

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.valcan.i_find.ui.armadio.ArmadioScreen
import com.valcan.i_find.ui.navigation.IFindDestinations
import com.valcan.i_find.ui.theme.IFindTheme
import com.valcan.i_find.ui.vestito.VestitoScreen
import com.valcan.i_find.ui.home.HomePage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IFindTheme {
                IFindApp()
            }
        }
    }
}

@Composable
fun IFindApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = IFindDestinations.HOME_ROUTE
    ) {
        composable(IFindDestinations.HOME_ROUTE) {
            HomePage(
                onNavigateToAddVestiti = { navController.navigate(IFindDestinations.VESTITI_ROUTE) },
                onNavigateToAddArmadi = { navController.navigate(IFindDestinations.ARMADI_ROUTE) },
                onNavigateToSearch = { navController.navigate(IFindDestinations.SEARCH_ROUTE) },
                onNavigateToSettings = { navController.navigate(IFindDestinations.SETTINGS_ROUTE) }
            )
        }

        composable(IFindDestinations.ARMADI_ROUTE) {
            ArmadioScreen(
                onNavigateToVestiti = { armadioId ->
                    navController.navigate(IFindDestinations.vestitiByArmadioRoute(armadioId))
                }
            )
        }
        
        composable(
            route = IFindDestinations.VESTITI_BY_ARMADIO_ROUTE,
            arguments = listOf(
                navArgument("armadioId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val armadioId = backStackEntry.arguments?.getLong("armadioId") ?: return@composable
            VestitoScreen(
                armadioId = armadioId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Placeholder per le nuove schermate
        composable(IFindDestinations.SEARCH_ROUTE) {
            // TODO: Implementare la schermata di ricerca
        }

        composable(IFindDestinations.SETTINGS_ROUTE) {
            // TODO: Implementare la schermata delle impostazioni
        }

        // Aggiungi questa route per gestire l'aggiunta di vestiti dalla home
        composable(IFindDestinations.VESTITI_ROUTE) {
            VestitoScreen(
                armadioId = -1L,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}