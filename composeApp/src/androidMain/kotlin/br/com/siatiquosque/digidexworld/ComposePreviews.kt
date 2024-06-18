package br.com.siatiquosque.digidexworld

import android.content.res.Configuration
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonList
import br.com.siatiquosque.digidexworld.presentation.ui.main.BottomNavigationUI
import br.com.siatiquosque.digidexworld.presentation.theme.DigimonAppTheme
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.CardList


@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
fun BottomAppPreview() {
    DigimonAppTheme {

        val navBottomBarController = rememberNavController()
        val navDigimonController = rememberNavController()
        val grid = rememberLazyGridState()
        BottomNavigationUI(navBottomBarController, grid,navDigimonController, "")
    }
}

@Composable
@Preview
fun CardListPreview() {
    CardList(DigimonList())
}



