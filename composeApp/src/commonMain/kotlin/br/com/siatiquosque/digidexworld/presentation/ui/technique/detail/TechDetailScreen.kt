package br.com.siatiquosque.digidexworld.presentation.ui.technique.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.siatiquosque.digidex_shared.data.model.dw1.Digimon
import br.com.siatiquosque.digidex_shared.data.model.dw1.EnemySpawn
import br.com.siatiquosque.digidex_shared.data.model.dw1.Evolution
import br.com.siatiquosque.digidex_shared.utils.ImageMapper
import br.com.siatiquosque.digidexworld.presentation.components.BaseScreen
import br.com.siatiquosque.digidexworld.presentation.components.ImageCard
import br.com.siatiquosque.digidexworld.presentation.components.SpriteCard
import br.com.siatiquosque.digidexworld.presentation.theme.DigiTheme
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.EvolutionCard
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.toShow
import br.com.siatiquosque.digidexworld.presentation.ui.technique.detail.viewmodel.TechDetailState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TechDetailScreen(
    state: StateFlow<TechDetailState>,
    onBack: () -> Unit = {}
) {
    val uiState by state.collectAsState()

    BaseScreen(
        toolbar = {
            Text(uiState.technique?.name.toString())
        },
        onBack = onBack
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(0.6f).padding(top = 8.dp)
                ) {
                    ImageMapper.DigimonWorld1TechSprites[uiState.technique?.type.toString()]?.let {
                        ImageCard(it, uiState.technique?.type.toString())
                    }
                }
            }

            item {

                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Power: ${uiState.technique?.power.toShow()}"
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "MP: ${uiState.technique?.mp.toShow()}"
                        )
                    }
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Range: ${uiState.technique?.range.toShow()}"
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Type: ${uiState.technique?.type.toShow()}"
                        )
                    }
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Effect: ${uiState.technique?.effect.toShow()}"
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Accuracy: ${uiState.technique?.accuracy.toShow()}"
                        )
                    }
                    uiState.technique?.learnPercent?.let {
                        Row {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Learn Percent: ${uiState.technique?.learnPercent.toShow()}"
                            )
                        }
                    }
                }
            }

            items(uiState.technique?.digimons.orEmpty()) {
                DigimonCard(it)
            }
        }


    }
}

@Composable
fun DigimonCard(
    digimon: Digimon,
    onClick: (Int) -> Unit = {}
) {

    Card(
        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
        onClick = { onClick(digimon.digimon?.id ?: 0) }
    ) {
        Row(
            modifier = Modifier.padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight()) {

                ImageMapper.DigimonImg[digimon.digimon?.name]?.let {
                    ImageCard(it, digimon.digimon?.name.toString())
                }
                Text(
                    text = digimon.digimon?.name.toString()
                )
            }


            Column(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                digimon.spawn?.forEach {
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        maxLines = 3,
                        text = "${it.map?.name.toString()} \n${it.map?.description} - ${it.enemyMove?.percentage}"
                    )
                }
            }

        }
    }
}

