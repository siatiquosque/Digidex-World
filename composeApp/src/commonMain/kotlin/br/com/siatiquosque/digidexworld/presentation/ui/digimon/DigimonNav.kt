package br.com.siatiquosque.digidexworld.presentation.ui.digimon

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.siatiquosque.digidex_shared.data.model.dw1.Technique
import br.com.siatiquosque.digidex_shared.domain.DigimonWorld1Interactor
import br.com.siatiquosque.digidexworld.presentation.navigation.DigimonNavigation
import br.com.siatiquosque.digidexworld.presentation.navigation.TechNavigation
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.DigimonDetailScreen
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.viewmodel.DigimonDetailEvent
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.viewmodel.DigimonDetailViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.DigimonEvolutionScreen
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.viewmodel.DigimonEvolutionEvent
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.viewmodel.DigimonEvolutionViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.DigimonListScreen
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel.DigimonListViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.technique.detail.TechDetailScreen
import br.com.siatiquosque.digidexworld.presentation.ui.technique.detail.viewmodel.TechDetailViewModel
import org.koin.compose.koinInject

@Composable
fun DigimonListNav(
    navigator: NavHostController,
    scrollDigimonState: LazyGridState,
    goToTechnique: (Int) -> Unit,
) {

    NavHost(
        startDestination = DigimonNavigation.List.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(
            route = DigimonNavigation.List.route
        ) {
            val interactor: DigimonWorld1Interactor = koinInject()
            val viewModel: DigimonListViewModel = viewModel { DigimonListViewModel(interactor) }
//            val viewModel: DigimonListViewModel = koinInject()
            DigimonListScreen(
                scrollDigimonState = scrollDigimonState,
                navigateToDetail = {
                    navigator.navigate(route = DigimonNavigation.Detail.route.plus("/$it"))
                },
                event = viewModel::onTriggerEvent,
                state = viewModel.uiState
            )
        }

        composable(
            route = DigimonNavigation.Detail.route.plus("/{name}"),
            arguments = DigimonNavigation.Detail.arguments
        ) { backStackEntry ->
            val argument = backStackEntry.arguments
            val id = argument?.getString("name")

            id?.let {
                val interactor: DigimonWorld1Interactor = koinInject()
                val viewModel: DigimonDetailViewModel = viewModel { DigimonDetailViewModel(interactor) }
                LaunchedEffect(id) {
                    viewModel.onTriggerEvent(DigimonDetailEvent.GetDigimon(id))
                }

                DigimonDetailScreen(
                    state = viewModel.uiState,
                    event = viewModel::onTriggerEvent,
                    goToEvolutions = {
                        navigator.navigate(DigimonNavigation.Evolutions.route.plus("/$it"))
                    },
                    goToTechnique = {
                                    navigator.navigate(TechNavigation.Detail.route.plus("/$it"))
                    },
                    onBack = {
                        navigator.navigateUp()
                    }
                )
            }
        }

        composable(
            route = DigimonNavigation.Evolutions.route.plus("/{id}"),
            arguments = DigimonNavigation.Evolutions.arguments
        ) { backStackEntry ->
            val argument = backStackEntry.arguments
            val id = argument?.getInt("id")

            id?.let {
                val viewModel: DigimonEvolutionViewModel = koinInject()
                LaunchedEffect(id) {
                    viewModel.onTriggerEvent(DigimonEvolutionEvent.GetDigimon(id))
                }

                DigimonEvolutionScreen(
                    state = viewModel.uiState,
                    event = viewModel::onTriggerEvent,
                    goToEvolution = {
                        navigator.navigate(DigimonNavigation.Evolutions.route.plus("/$it")) {
//                            launchSingleTop = true
//                            //Restore state when reselecting a previously selected item
//                            restoreState = true
                        }
                    },
                    onBack = {
                        navigator.navigateUp()
                    }
                )
            }

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
                    state = viewModel.uiState,
                    onBack = {
                        navigator.navigateUp()
                    }
                )
            }
        }
    }
}