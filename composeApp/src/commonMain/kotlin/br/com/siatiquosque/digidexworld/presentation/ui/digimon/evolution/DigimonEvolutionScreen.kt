package br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpCenter
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Help
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.siatiquosque.digidex_shared.data.model.dw1.Digimon
import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonEntity
import br.com.siatiquosque.digidex_shared.data.model.dw1.Evolution
import br.com.siatiquosque.digidex_shared.data.model.dw1.EvolutionHelper
import br.com.siatiquosque.digidex_shared.utils.ImageMapper
import br.com.siatiquosque.digidexworld.presentation.components.BaseScreen
import br.com.siatiquosque.digidexworld.presentation.components.ImageCard
import br.com.siatiquosque.digidexworld.presentation.components.SpriteCard
import br.com.siatiquosque.digidexworld.presentation.theme.DigiTheme
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.viewmodel.DigimonEvolutionEvent
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.viewmodel.DigimonEvolutionState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigimonEvolutionScreen(
    state: StateFlow<DigimonEvolutionState>,
    event: (DigimonEvolutionEvent) -> Unit,
    goToEvolution: (Int) -> Unit = {},
    onBack: () -> Unit = {}
) {

    val uiState by state.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden, skipHiddenState = false
        )
    )

    var isHelper by remember { mutableStateOf(false) }


    LaunchedEffect(sheetState.bottomSheetState.currentValue) {
        when (sheetState.bottomSheetState.currentValue) {

            SheetValue.Hidden -> {}

            SheetValue.Expanded -> {
            }

            SheetValue.PartiallyExpanded -> {
                if (!showBottomSheet) {
                    scope.launch { sheetState.bottomSheetState.hide() }
                }
            }
        }
    }

    BaseScreen(toolbar = {
        uiState.entity?.digimon?.sprite?.let {
            SpriteCard(it, uiState.entity?.digimon?.name.toString())
        }
    }, actions = {
        IconButton(onClick = {
            scope.launch { sheetState.bottomSheetState.expand() }.invokeOnCompletion {
                showBottomSheet = true
            }
        }) {
            Icon(
                Icons.AutoMirrored.Filled.HelpCenter, contentDescription = "Helper"
            )
        }
    }, onBack = onBack
    ) {

        DigimonBottomSheet(showBottomSheet = showBottomSheet,
            sheetState = sheetState,
            evolutionHelper = uiState.digimon,
            event = event,
            onHide = {
                isHelper = true
                scope.launch { sheetState.bottomSheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.bottomSheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            }) {


            LazyColumn {
                item {
                    Text("Evolução")
                }

                items(uiState.to) {
                    EvolutionCard(
                        it, isHelper, onClick = goToEvolution
                    )
                }


                item {
                    Text("Pré-evolução")
                }

                items(uiState.entity?.from.orEmpty()) {
                    EvolutionCard(
                        it, isHelper, onClick = goToEvolution
                    )
                }


            }
        }
    }
}


@Composable
fun EvolutionCard(
    evolution: Evolution, isHelper: Boolean, onClick: (Int) -> Unit = {}
) {

    Card(modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth(),
        onClick = { onClick(evolution.id ?: 0) }) {
        Row(
            modifier = Modifier.padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight()) {

                ImageMapper.DigimonImg[evolution.name]?.let {
                    ImageCard(it, evolution.name.toString())
                }
                Text(
                    text = evolution.name.toString()
                )
            }


            Column(
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Column(
                    modifier = Modifier.padding(1.dp).border(
                        1.dp,
                        getColor(evolution.statsEnabled, isHelper),
                        shape = RoundedCornerShape(3.dp)
                    ).padding(1.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f), text = "HP: ${evolution.hp.toShow()}"
                        )
                        Text(modifier = Modifier.weight(1f), text = "MP: ${evolution.mp.toShow()}")
                    }
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Offense: ${evolution.offense.toShow()}"
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Defense: ${evolution.defense.toShow()}"
                        )
                    }
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Speed: ${evolution.speed.toShow()}"
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Brain: ${evolution.brains.toShow()}"
                        )
                    }
                }
                Row {
                    Row(
                        modifier = Modifier.weight(1f).padding(1.dp).border(
                            1.dp,
                            getColor(evolution.careEnabled == true, isHelper),
                            shape = RoundedCornerShape(3.dp)
                        ).padding(1.dp),
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Cares: ${evolution.care.toShow()}"
                        )
                    }
                    Row(
                        modifier = Modifier.weight(1f).padding(1.dp).border(
                            1.dp,
                            getColor(evolution.weightEnabled == true, isHelper),
                            shape = RoundedCornerShape(3.dp)
                        ).padding(1.dp),
                    ) {
                        Text(
                            text = "Weight: ${evolution.weight.toShow()}"
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(1.dp).border(
                        1.dp,
                        getColor(evolution.bonusEnabled == true, isHelper),
                        shape = RoundedCornerShape(3.dp)
                    ).padding(1.dp)
                ) {
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Discipline: ${evolution.disc.toShow()}"
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Happy: ${evolution.happy.toShow()}"
                        )
                    }
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Battles: ${evolution.battles.toShow()}"
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Techs: ${evolution.techs.toShow()}"
                        )
                    }
                }
                evolution.score?.let {
                    Row {
                        Text(
                            text = "Score: $it"
                        )

                        if (evolution.selected) {
                            Icon(
                                Icons.Filled.CheckCircle,
                                contentDescription = "Selected",
                                tint = DigiTheme.colors.SecondaryBlueGabumonDark

                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun getColor(b: Boolean?, isHelper: Boolean): Color {
    return if (!isHelper) Color.Transparent else b?.let { if (b) DigiTheme.colors.SecondaryBlueGabumonDark else DigiTheme.colors.PrimaryOrangeAgumonDark }
        ?: run { Color.Transparent }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DigimonBottomSheet(
    showBottomSheet: Boolean,
    sheetState: BottomSheetScaffoldState,
    evolutionHelper: EvolutionHelper,
    event: (DigimonEvolutionEvent) -> Unit,
    onHide: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {

//    if (showBottomSheet) {
    BottomSheetScaffold(scaffoldState = sheetState,
//            onDismissRequest = onHide,
//            sheetState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
            ) {
                RowEvolutionHelper(
                    "HP", evolutionHelper.hp.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    hp = it.toForm(evolutionHelper.hp)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )

                RowEvolutionHelper(
                    "MP", evolutionHelper.mp.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    mp = it.toForm(evolutionHelper.mp)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )

                RowEvolutionHelper(
                    "Offense", evolutionHelper.offense.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    offense = it.toForm(evolutionHelper.offense)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )
                RowEvolutionHelper(
                    "Defense", evolutionHelper.defense.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    defense = it.toForm(evolutionHelper.defense)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )
                RowEvolutionHelper(
                    "Speed", evolutionHelper.speed.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    speed = it.toForm(evolutionHelper.speed)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )
                RowEvolutionHelper(
                    "Brains", evolutionHelper.brains.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    brains = it.toForm(evolutionHelper.brains)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )


                RowEvolutionHelper(
                    "Cares", evolutionHelper.care.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    care = it.toForm(evolutionHelper.care)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )
                RowEvolutionHelper(
                    "Weight", evolutionHelper.weight.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    weight = it.toForm(evolutionHelper.weight)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )

                Text("Bonus")

                RowEvolutionHelper(
                    "Happy", evolutionHelper.happy.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    happy = it.toForm(evolutionHelper.happy)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )
                RowEvolutionHelper(
                    "Disc", evolutionHelper.disc.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    disc = it.toForm(evolutionHelper.disc)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )
                RowEvolutionHelper(
                    "Battles", evolutionHelper.battles.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    battles = it.toForm(evolutionHelper.battles)
                                )
                            )
                        )
                    }, keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number,
                    )
                )
                RowEvolutionHelper(
                    "Tech", evolutionHelper.techs.toValue(), onValueChange = {
                        event(
                            DigimonEvolutionEvent.EvolutionHelperUpdate(
                                evolutionHelper.copy(
                                    techs = it.toForm(evolutionHelper.techs)
                                )
                            )
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number,
                    ),
                    onDone = {
                        event(
                            DigimonEvolutionEvent.ApplyHelper()
                        )
                        onHide()
                    }

                )

                Row {
                    Button(onClick = {
                        event(
                            DigimonEvolutionEvent.ClearHelper()
                        )
                    }) {
                        Text("Clear")
                    }

                    Button(onClick = {
                        event(
                            DigimonEvolutionEvent.ApplyHelper()
                        )
                        onHide()
                    }) {
                        Text("Apply")
                    }
                }
            }
        }) {
        content(it)
//        }
    }
}

@Composable
fun RowEvolutionHelper(
    label: String,
    value: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier.padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp).weight(0.3f)
        ) {
            Text(
                text = label
            )
        }
        Row(
            modifier = Modifier.padding(start = 8.dp).weight(1f)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(label)
                },
                value = value,
                onValueChange = onValueChange,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDone()
                        keyboardController?.hide()
                    },
                ),

                )
        }
    }
}

fun String?.toShow(): String {
    return if (this.isNullOrEmpty()) " - " else this
}

fun Int?.toShow(): String {
    return this?.toString() ?: " - "
}

fun Int?.toValue(): String {
    return this?.toString() ?: ""
}

fun String.toForm(default: Int? = 0): Int? {
    return if (this.isEmpty()) {
        null
    } else {
        if (this.toIntOrNull() == null) {
            default
        } else {
            this.toIntOrNull()
        }
    }

}