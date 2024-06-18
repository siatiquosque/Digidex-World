package br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.siatiquosque.digidex_shared.data.model.dw1.Technique
import br.com.siatiquosque.digidex_shared.utils.ImageMapper
import br.com.siatiquosque.digidexworld.presentation.components.BaseScreen
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.viewmodel.DigimonDetailState
import br.com.siatiquosque.digidexworld.presentation.components.ImageCard
import br.com.siatiquosque.digidexworld.presentation.components.SpriteCard
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.viewmodel.DigimonDetailEvent
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.toShow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun DigimonDetailScreen(
    state: StateFlow<DigimonDetailState>,
    event: (DigimonDetailEvent) -> Unit,
    goToEvolutions: (Int) -> Unit = {},
    goToTechnique: (Int) -> Unit = {},
    onBack: () -> Unit = {}
) {
    val uiState by state.collectAsState()
    val entity = uiState.entity?.firstOrNull()

    BaseScreen(
        toolbar = {
            entity?.digimon?.sprite?.let {
                SpriteCard(it, entity.digimon?.name.toString())
            }
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
                    entity?.digimon?.image?.let {
                        ImageCard(it, entity.digimon?.name.toString())
                    }
                }
            }

            item {

                Row(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                        text = "Tipo: ${entity?.digimon?.typus}"
                    )

                    Text(
                        modifier = Modifier.weight(1f).padding(start = 8.dp),
                        text = "Level: ${entity?.digimon?.level}"
                    )
                }
            }

            item {

                Button(
                    onClick = {
                        entity?.digimon?.id?.let { goToEvolutions(it) }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Evolutions"
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Evolutions"
                    )
                }
            }

            items(entity?.moves.orEmpty()) {
                TechsCards(
                    it,
                    onClick = goToTechnique
                )
            }
        }

    }
}


@Composable
fun TechsCards(
    technique: Technique,
    onClick: (Int) -> Unit = {}
) {
    Card(
        onClick = { onClick(technique.id ?: 0) },
        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight()) {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
                ImageMapper.DigimonWorld1TechSprites[technique.type.toString()]?.let {
                    ImageCard(it, technique.type.toString())
                }
//                }
                Text(
                    text = technique.name.toString()
                )
            }

            Column(
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Power: ${technique.power.toShow()}"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "MP: ${technique.mp.toShow()}"
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Range: ${technique.range.toShow()}"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Type: ${technique.type.toShow()}"
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Effect: ${technique.effect.toShow()}"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Accuracy: ${technique.accuracy.toShow()}"
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Learn Percent: ${technique.learnPercent.toShow()}"
                    )
                }
            }

        }

    }
}