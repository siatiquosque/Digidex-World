package br.com.siatiquosque.digidexworld.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class ItemNavigation(
    val route: String, val arguments: kotlin.collections.List<NamedNavArgument>
) {
    data object Item : ItemNavigation(
        route = "Item",
        arguments = emptyList()
    )
}