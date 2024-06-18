package br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonList
import br.com.siatiquosque.digidex_shared.domain.DigimonWorld1Interactor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

private const val DIGIMON_LIST_SAVED_STATE_KEY = "DigimonListKey"

private const val DIGIMON_LIST_SORT_SAVED_STATE_KEY = "DigimonListSortKey"
private const val DIGIMON_LIST_QUERY_SAVED_STATE_KEY = "DigimonListQueryKey"


class DigimonListViewModel(
    private val digimonWorld1Interactor: DigimonWorld1Interactor
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        DigimonListState()
    )

    var uiState: StateFlow<DigimonListState> = _uiState.asStateFlow()


    init {
        getDigimons()
    }

    fun getDigimons() {
        combine(
            digimonWorld1Interactor.getAllDigimons()
        ) {
            _uiState.update { newState ->
                newState.copy(digimons = it.last().filter {
                    uiState.value.query.lowercase() in it.digimon?.name?.lowercase().orEmpty()
                })
            }
            digimonWorld1Interactor.getAllTypes()
        }.onEach {
            _uiState.value = _uiState.value.copy(types = it.last())
        }.launchIn(viewModelScope)
    }

    fun onTriggerEvent(digimonListEvent: DigimonListEvent) {
        when (digimonListEvent) {
            is DigimonListEvent.OnQueryChange -> {
                _uiState.value = _uiState.value.copy(query = digimonListEvent.query)
            }

            is DigimonListEvent.OnSearch -> getDigimons()
            is DigimonListEvent.OnSort -> sortDigimons(digimonListEvent.sort)
            is DigimonListEvent.OnFilter -> filterDigimonsByType(digimonListEvent.filter)
        }

    }

    private fun filterDigimonsByType(filter: String?) {
        digimonWorld1Interactor.getAllDigimons().onEach { list ->
            val isFilter = if (filter != _uiState.value.selectedType) filter else null
            applyFilter(list, _uiState.value.copy(selectedType = isFilter))
        }.launchIn(viewModelScope)
    }

    private fun sortDigimons(sort: ListSort) {
        applyFilter(_uiState.value.digimons, _uiState.value.copy(sort = sort))
    }

    private fun applyFilter(digimonList: List<DigimonList>, digimonListState: DigimonListState) {
        val filteredList = digimonList
            .filter {
                digimonListState.query.lowercase() in it.digimon?.name?.lowercase().orEmpty() &&
                        (digimonListState.selectedType.isNullOrEmpty() || digimonListState.selectedType == it.digimon?.typus)
            }
            .sortedWith(when (digimonListState.sort) {
                ListSort.HP -> compareByDescending<DigimonList> { it.info?.hp }.thenBy { it.digimon?.id }
                ListSort.OFF -> compareByDescending<DigimonList> { it.info?.offense }.thenBy { it.digimon?.id }
                ListSort.DEF -> compareByDescending<DigimonList> { it.info?.defense }.thenBy { it.digimon?.id }
                ListSort.NONE -> compareBy { it.digimon?.id }
            })
        _uiState.update { newState ->
            newState.copy(
                digimons = filteredList,
                query = digimonListState.query,
                sort = digimonListState.sort,
                selectedType = digimonListState.selectedType
            )
        }

    }
}