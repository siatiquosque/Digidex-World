package br.com.siatiquosque.digidexworld.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class DigimonNavigation(
    val route: String, val arguments: kotlin.collections.List<NamedNavArgument>
) {
    data object List : DigimonNavigation(
        route = "DigimonsList",
        arguments = emptyList()
    )

    data object Detail : DigimonNavigation(
        route = "DigimonsDetail",
        arguments = listOf(navArgument("name") {
            type = NavType.StringType
        })
    )

    data object Evolutions : DigimonNavigation(
        route = "Evolutions",
        arguments = listOf(navArgument("id") {
            type = NavType.IntType
        })
    )
}