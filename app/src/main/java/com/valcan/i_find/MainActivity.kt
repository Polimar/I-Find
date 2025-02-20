package com.valcan.i_find

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.valcan.i_find.ui.armadio.ArmadioScreen
import com.valcan.i_find.ui.home.HomePage
import com.valcan.i_find.ui.navigation.Destinations
import com.valcan.i_find.ui.theme.IFindTheme
import com.valcan.i_find.ui.theme.PastelSurface
import com.valcan.i_find.ui.vestito.VestitoScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IFindTheme {
                val navController = rememberNavController()
                
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = PastelSurface,
                            modifier = Modifier.height(64.dp)
                        ) {
                            val currentBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = currentBackStackEntry?.destination?.route
                            
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Home, "Home") },
                                label = { Text("Home") },
                                selected = currentRoute == Destinations.Home.route,
                                onClick = {
                                    navController.navigate(Destinations.Home.route) {
                                        popUpTo(Destinations.Home.route) { inclusive = true }
                                    }
                                }
                            )
                            
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Checkroom, "Vestiti") },
                                label = { Text("Vestiti") },
                                selected = currentRoute == Destinations.Vestiti.route,
                                onClick = {
                                    navController.navigate(Destinations.Vestiti.route) {
                                        popUpTo(Destinations.Home.route)
                                    }
                                }
                            )
                            
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Checkroom, "Armadi") },
                                label = { Text("Armadi") },
                                selected = currentRoute == Destinations.Armadi.route,
                                onClick = {
                                    navController.navigate(Destinations.Armadi.route) {
                                        popUpTo(Destinations.Home.route)
                                    }
                                }
                            )
                            
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Search, "Cerca") },
                                label = { Text("Cerca") },
                                selected = currentRoute == Destinations.Search.route,
                                onClick = {
                                    navController.navigate(Destinations.Search.route) {
                                        popUpTo(Destinations.Home.route)
                                    }
                                }
                            )
                            
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Settings, "Impostazioni") },
                                label = null,
                                selected = currentRoute == Destinations.Settings.route,
                                onClick = {
                                    navController.navigate(Destinations.Settings.route) {
                                        popUpTo(Destinations.Home.route)
                                    }
                                }
                            )
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.Home.route,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(Destinations.Home.route) {
                            HomePage(
                                onNavigateToVestiti = { 
                                    navController.navigate(Destinations.Vestiti.route)
                                },
                                onNavigateToArmadi = {
                                    navController.navigate(Destinations.Armadi.route)
                                },
                                onNavigateToSearch = {
                                    navController.navigate(Destinations.Search.route)
                                },
                                onNavigateToSettings = {
                                    navController.navigate(Destinations.Settings.route)
                                }
                            )
                        }
                        
                        composable(Destinations.Vestiti.route) {
                            VestitoScreen(
                                armadioId = -1L,
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }
                        
                        composable(
                            route = Destinations.VestitiInArmadio.route,
                            arguments = listOf(
                                navArgument("armadioId") { type = NavType.LongType }
                            )
                        ) { backStackEntry ->
                            val armadioId = backStackEntry.arguments?.getLong("armadioId") ?: -1L
                            VestitoScreen(
                                armadioId = armadioId,
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }
                        
                        composable(Destinations.Armadi.route) {
                            ArmadioScreen(
                                onNavigateToVestiti = { armadioId ->
                                    navController.navigate(Destinations.VestitiInArmadio.createRoute(armadioId))
                                },
                                onNavigateBack = { navController.navigateUp() }
                            )
                        }
                        
                        composable(Destinations.Search.route) {
                            // TODO: Implementare SearchScreen
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text("Search Screen - Coming Soon")
                            }
                        }
                        
                        composable(Destinations.Settings.route) {
                            // TODO: Implementare SettingsScreen
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text("Settings Screen - Coming Soon")
                            }
                        }
                    }
                }
            }
        }
    }
}