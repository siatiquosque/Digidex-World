package br.com.siatiquosque.digidexworld.presentation.ui.digimon.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonList
import br.com.siatiquosque.digidexworld.Res
import br.com.siatiquosque.digidexworld.digimon_world_1_list_title
import br.com.siatiquosque.digidexworld.presentation.components.BaseScreen
import br.com.siatiquosque.digidexworld.presentation.components.ImageCard
import br.com.siatiquosque.digidexworld.presentation.components.SearchBox
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel.DigimonListEvent
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel.DigimonListState
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel.ListSort
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigimonListScreen(
    state: StateFlow<DigimonListState>,
    scrollDigimonState: LazyGridState,
    event: (DigimonListEvent) -> Unit,
    navigateToDetail: (String) -> Unit = {},
) {
    val uiState by state.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    BaseScreen(
        titleToolbar = stringResource(Res.string.digimon_world_1_list_title)
    ) {

        SortBottomSheet(showBottomSheet, sheetState, event, uiState.sort,
            onHide = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            })

        Column {
            Row {
                SearchBox(
                    value = uiState.query,
                    onValueChange = { event(DigimonListEvent.OnQueryChange(it)) },
                    onSearchExecute = { event(DigimonListEvent.OnSearch) },
                    onCleanSearch = {
                        event(DigimonListEvent.OnFilter(null))
                        event(DigimonListEvent.OnSort(ListSort.NONE))
                        scope.launch { scrollDigimonState.scrollToItem(0) }
                    }
                )
            }

            LazyVerticalGrid(
                state = scrollDigimonState,
                columns = GridCells.Fixed(2)
            ) {
                item(span = {
                    GridItemSpan(2)
                }) {
                    Row {
                        IconButton(
                            onClick = {
                                showBottomSheet = true
                            }
                        ) {
                            Icon(
                                Icons.Filled.SwapVert,
                                contentDescription = "Sort"
                            )
                        }
                        LazyRow {
                            items(uiState.types) {
                                FilterChip(
                                    modifier = Modifier.padding(horizontal = 2.dp),
                                    onClick = { event(DigimonListEvent.OnFilter(it)) },
                                    label = { Text(it) },
                                    selected = it == uiState.selectedType
                                )
                            }
                        }
                    }

                }
                items(uiState.digimons) {
                    CardList(it, navigateToDetail)
                }

            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SortBottomSheet(
    showBottomSheet: Boolean,
    sheetState: SheetState,
    event: (DigimonListEvent) -> Unit,
    sort: ListSort?,
    onHide: () -> Unit = {}
) {
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onHide,
            sheetState = sheetState
        ) {
            LazyColumn {
                items(ListSort.entries) {
                    Row(
                        modifier = Modifier.clickable {
                            event(DigimonListEvent.OnSort(it))
                            onHide()
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp).weight(1f),
                            text = it.label
                        )
                        if (sort == it) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = "Selected"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardList(
    digimon: DigimonList,
    onClick: (String) -> Unit = {},
) {
    Card(modifier = Modifier.padding(8.dp),
        onClick = {
            onClick.invoke(digimon.digimon?.name.toString())
        }) {
        Column(
            modifier = Modifier.padding(18.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            digimon.digimon?.image?.let {
                ImageCard(it, digimon.digimon?.name.toString())
            }

            Text(digimon.digimon?.name.toString())

        }

    }
}