package br.com.siatiquosque.digidexworld.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.LunchDining
import androidx.compose.material.icons.rounded.Pets
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector


sealed class MainNavigation(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
) {
    data object DigimonListMain : MainNavigation(
        route = "DigimonsList",
        title = "Digimons",
        selectedIcon = Icons.Rounded.Pets,
        unSelectedIcon = Icons.Rounded.Pets,
    )

    data object ItemMain : MainNavigation(
        route = "Item",
        title = "Items",
        selectedIcon = Icons.Rounded.LunchDining,
        unSelectedIcon = Icons.Rounded.LunchDining,
    )

    data object TechMain : MainNavigation(
        route = "Tech",
        title = "Techs",
        selectedIcon = Icons.Rounded.Settings,
        unSelectedIcon = Icons.Rounded.Settings,
    )
}