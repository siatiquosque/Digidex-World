package br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.siatiquosque.digidex_shared.domain.DigimonWorld1Interactor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DigimonDetailViewModel(val digimonWorld1Interactor: DigimonWorld1Interactor) : ViewModel() {
    private val _uiState = MutableStateFlow(DigimonDetailState())

    val uiState: StateFlow<DigimonDetailState> = _uiState.asStateFlow()


    fun onTriggerEvent(event: DigimonDetailEvent) {
        when (event) {
            is DigimonDetailEvent.GetDigimon -> getDigimon(event.name)
        }
    }


    fun getDigimon(name: String) {
        digimonWorld1Interactor
            .searchByName(name)
            .onEach {
                _uiState.value = _uiState.value.copy(entity = mutableStateListOf(*it.toTypedArray()))
            }
            .launchIn(viewModelScope)
    }
}