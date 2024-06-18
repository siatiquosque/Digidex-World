package br.com.siatiquosque.digidexworld.presentation.ui.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.siatiquosque.digidex_shared.data.model.dw1.Item
import br.com.siatiquosque.digidex_shared.utils.ImageMapper
import br.com.siatiquosque.digidexworld.Res
import br.com.siatiquosque.digidexworld.digimon_world_1_list_title
import br.com.siatiquosque.digidexworld.presentation.components.BaseScreen
import br.com.siatiquosque.digidexworld.presentation.components.ImageCard
import br.com.siatiquosque.digidexworld.presentation.components.SearchBox
import br.com.siatiquosque.digidexworld.presentation.ui.item.viewmodel.ItemEvent
import br.com.siatiquosque.digidexworld.presentation.ui.item.viewmodel.ItemState
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.toShow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun ItemScreen(
    event: (ItemEvent) -> Unit,
    state: StateFlow<ItemState>,
) {
    val uiState by state.collectAsState()
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    BaseScreen(
        titleToolbar = stringResource(Res.string.digimon_world_1_list_title)
    ) {

        Column {
            SearchBox(
                value = uiState.query,
                onValueChange = { event(ItemEvent.OnQueryChange(it)) },
                onSearchExecute = { event(ItemEvent.OnSearch()) },
                onCleanSearch = { scope.launch { listState.scrollToItem(0) } }
            )

            LazyColumn(
                state = listState
            ) {
                items(uiState.items) {
                    CardItemList(it)
                }

            }
        }
    }

}

@Composable
fun CardItemList(item: Item) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp).fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(18.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.3f).fillMaxHeight()) {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
                ImageMapper.DigimonWorld1ItemSprites[item.item?.id.toString()]?.let {
                    ImageCard(it, item.item?.name.toString())
                }
//                }
                Text(
                    text = item.item?.name.toString()
                )
            }

            Column(
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Energy: ${item.food?.energy.toShow()}"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Weight: ${item.food?.weight.toShow()}"
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Tired: ${item.food?.tiredness.toShow()}"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Happy: ${item.food?.happiness.toShow()}"
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Disc: ${item.food?.discipline.toShow()}"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Lifetime: ${item.food?.lifetime.toShow()}"
                    )
                }
                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Sick: ${item.food?.sickness.toShow()}"
                    )
                }

                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Buff: ${item.food?.buff_flag.toShow()}"
                    )
                }

                Row {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Value: ${item.food?.buff_value.toShow()}"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Duration: ${item.food?.buff_duration.toShow()}"
                    )
                }

            }

        }

    }
}