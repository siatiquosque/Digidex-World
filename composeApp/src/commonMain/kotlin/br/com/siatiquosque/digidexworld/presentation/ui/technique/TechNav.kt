package br.com.siatiquosque.digidexworld.presentation.ui.technique

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.siatiquosque.digidex_shared.domain.DigimonWorld1Interactor
import br.com.siatiquosque.digidexworld.presentation.navigation.TechNavigation
import br.com.siatiquosque.digidexworld.presentation.ui.technique.detail.TechDetailScreen
import br.com.siatiquosque.digidexworld.presentation.ui.technique.detail.viewmodel.TechDetailViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.technique.list.TechListScreen
import br.com.siatiquosque.digidexworld.presentation.ui.technique.list.viewmodel.TechListViewModel
import org.koin.compose.koinInject

@Composable
fun TechNav(navigator: NavHostController) {
    val navteste = rememberNavController()
    NavHost(
        startDestination = TechNavigation.Tech.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(
            route = TechNavigation.Tech.route
        ) { backstack ->
//            val viewModel: TechListViewModel = viewModel { TechListViewModel() }
//            TechListScreen(
//                state = viewModel.uiState
//            )
            navteste.navigate(TechNavigation.Detail.route.plus("/0"))
        }

        composable(
            route = TechNavigation.Detail.route.plus("/{id}"),
            arguments = TechNavigation.Detail.arguments,
        ) { backStackEntry ->
            val argument = backStackEntry.arguments
            val id = argument?.getInt("id")

            id?.let {
                val interactor: DigimonWorld1Interactor = koinInject()
                val viewModel: TechDetailViewModel = viewModel { TechDetailViewModel(interactor) }

                LaunchedEffect(id) {
                    viewModel.getTechById(id)
                }

                TechDetailScreen(
                    state = viewModel.uiState
                )
            }
        }
    }
}