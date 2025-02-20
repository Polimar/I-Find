package com.example.ifind.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Checkroom
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.valcan.i_find.ui.theme.*

object AppColors {
    val PrimaryBlue = Color(0xFF90CAF9)      // Stesso del PastelPrimary
    val PastelBlue = Color(0xFFBBDEFB)       // Stesso del PastelSecondary
    val AccentBlue = Color(0xFF64B5F6)       // Stesso del PastelAccent
    val TextBlue = Color(0xFF1976D2)         // Stesso del PastelText
    val BackgroundBlue = Color(0xFFE3F2FD)   // Stesso del PastelBackground
}

@Composable
fun MainScreen(
    userName: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.BackgroundBlue)
    ) {
        // Header con titolo e nome utente
        WelcomeSection(userName)
        
        // Contenuto principale
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainTitle()
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Bottom Navigation
            BottomNavigation()
        }
    }
}

@Composable
fun WelcomeSection(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Benvenuto",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = AppColors.TextBlue
        )
        Text(
            text = userName,
            style = MaterialTheme.typography.headlineSmall,
            color = AppColors.AccentBlue
        )
    }
}

@Composable
fun MainTitle() {
    Text(
        text = "Cosa mi metto oggi?",
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        color = AppColors.PrimaryBlue,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(vertical = 32.dp)
    )
}

@Composable
fun BottomNavigation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavItem(Icons.Default.Home, "Home")
        BottomNavItem(Icons.Outlined.Checkroom, "Vestiti")
        BottomNavItem(Icons.Default.Weekend, "Armadi")
        BottomNavItem(Icons.Default.Search, "Cerca")
        BottomNavItem(Icons.Default.Settings, "Impostazioni")
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, contentDescription: String) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = Modifier.size(28.dp),
        tint = AppColors.AccentBlue
    )
} 