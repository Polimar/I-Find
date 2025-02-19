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
        startDestination = IFindDestinations.ARMADI_ROUTE
    ) {
        composable(IFindDestinations.ARMADI_ROUTE) {
            ArmadioScreen(
                onNavigateToVestiti = { armadioId ->
                    navController.navigate(
                        IFindDestinations.vestitiByArmadioRoute(armadioId)
                    )
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
    }
}