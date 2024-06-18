package br.com.siatiquosque.digidexworld.presentation.ui.item.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.siatiquosque.digidex_shared.domain.DigimonWorld1Interactor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ItemViewModel(val digimonWorld1Interactor: DigimonWorld1Interactor) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemState())

    val uiState: StateFlow<ItemState> = _uiState.asStateFlow()

    init {
        getItemsAsFood()
    }


    fun onTriggerEvent(itemEvent: ItemEvent) {
        when (itemEvent) {
            is ItemEvent.OnQueryChange -> _uiState.value =
                _uiState.value.copy(query = itemEvent.query)

            is ItemEvent.OnSearch -> getItemsAsFood()
        }

    }

    private fun getItemsAsFood() {
        digimonWorld1Interactor
            .getItemsAsFood()
            .onEach {
                _uiState.value = _uiState.value.copy(items = it.filter {
                    _uiState.value.query.lowercase() in it.item?.name?.lowercase().orEmpty()
                })
            }
            .launchIn(viewModelScope)
    }

}