package br.com.siatiquosque.digidexworld.presentation.ui.item

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import br.com.siatiquosque.digidexworld.presentation.navigation.ItemNavigation
import br.com.siatiquosque.digidexworld.presentation.ui.item.viewmodel.ItemViewModel
import org.koin.compose.koinInject

@Composable
fun ItemNav() {
    val navigator = rememberNavController()
    NavHost(
        startDestination = ItemNavigation.Item.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(
            route = ItemNavigation.Item.route,
        ) {
            val viewModel: ItemViewModel = koinInject()
            ItemScreen(
                event = viewModel::onTriggerEvent,
                state = viewModel.uiState
            )
        }
    }
}