package br.com.siatiquosque.digidexworld.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class TechNavigation(
    val route: String, val arguments: List<NamedNavArgument>
) {
    data object Tech : TechNavigation(
        route = "Tech",
        arguments = emptyList()
    )

    data object Detail : TechNavigation(
        route = "Detail",
        arguments = listOf(navArgument("id") {
            type = NavType.IntType
        })
    )
}