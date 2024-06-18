package br.com.siatiquosque.digidexworld.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import br.com.siatiquosque.digidexworld.presentation.navigation.MainNavigation
import br.com.siatiquosque.digidexworld.presentation.navigation.TechNavigation
import br.com.siatiquosque.digidexworld.presentation.theme.DigiTheme
import br.com.siatiquosque.digidexworld.presentation.theme.DigimonAppTheme
import br.com.siatiquosque.digidexworld.presentation.ui.item.ItemNav
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.DigimonListNav
import br.com.siatiquosque.digidexworld.presentation.ui.technique.TechNav
import kotlinx.coroutines.launch

@Composable
fun MainNav() {
    val navBottomBarController = rememberNavController()
    val navDigimonController = rememberNavController()
    val navTechController = rememberNavController()
    val scrollDigimonState = rememberLazyGridState()
    val scope = rememberCoroutineScope()


    var currentRoute by remember {
        mutableStateOf(MainNavigation.DigimonListMain.route)
    }

    DigimonAppTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationUI(
                    navBottomBarController,
                    scrollDigimonState,
                    navDigimonController,
                    currentRoute,
                    onChangeItem = {
                        if (currentRoute != it.route) {
                            currentRoute = it.route
                            changeBottomItemNavigation(navBottomBarController, it)
                        } else {
                            if (navDigimonController.currentDestination?.route != MainNavigation.DigimonListMain.route) {
                                navDigimonController.popBackStack(it.route, false)
                            } else {
                                scope.launch { scrollDigimonState.scrollToItem(0) }
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    startDestination = MainNavigation.DigimonListMain.route,
                    navController = navBottomBarController,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(route = MainNavigation.DigimonListMain.route) {
                        DigimonListNav(
                            navDigimonController,
                            scrollDigimonState,
                            goToTechnique = {
//                                currentRoute = MainNavigation.TechMain.route
//                                changeBottomItemNavigation(
//                                    navBottomBarController,
//                                    MainNavigation.TechMain
//                                )

                            }
                        )
                    }
                    composable(route = MainNavigation.ItemMain.route) {
                        ItemNav()
                    }

                    composable(route = MainNavigation.TechMain.route) {
                        TechNav(navTechController)
                    }
                }
            }
        }
    }
}

fun changeBottomItemNavigation(
    navBottomBarController: NavController,
    mainNavigation: MainNavigation
) {
    navBottomBarController.navigate(mainNavigation.route) {
        navBottomBarController.graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
fun BottomNavigationUI(
    navController: NavController,
    scrollDigimonState: LazyGridState,
    navDigimonController: NavController,
    currentRoute: String?,
    onChangeItem: (MainNavigation) -> Unit = {}
) {

    NavigationBar(
        containerColor = DigiTheme.colors.Background,
    ) {
        val items = listOf(
            MainNavigation.DigimonListMain,
            MainNavigation.ItemMain,
            MainNavigation.TechMain
        )

        items.forEach {
            NavigationBarItem(
//                selectedContentColor = DigiTheme.colors.YellowAgumon,
//                unselectedContentColor = DigiTheme.colors.DarkGray,
                selected = it.route == currentRoute,
                icon = {
                    Icon(
                        imageVector = (if (it.route == currentRoute) it.selectedIcon else it.unSelectedIcon),
                        it.title,
                    )
                },
                onClick = {
                    onChangeItem(it)
                }
            )
        }
    }
}
