package br.com.siatiquosque.digidexworld.presentation.ui.technique.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.siatiquosque.digidex_shared.domain.DigimonWorld1Interactor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class TechDetailViewModel(val digimonWorld1Interactor: DigimonWorld1Interactor) : ViewModel() {
    private val _uiState = MutableStateFlow(TechDetailState())

    val uiState: StateFlow<TechDetailState> = _uiState.asStateFlow()

    fun getTechById(id: Int) {
        digimonWorld1Interactor.getTechByID(id)
            .onEach { tech ->
                _uiState.update {
                    it.copy(technique = tech)
                }
            }
            .launchIn(viewModelScope)
    }
}