package br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.siatiquosque.digidex_shared.data.model.dw1.Evolution
import br.com.siatiquosque.digidex_shared.domain.DigimonWorld1Interactor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class DigimonEvolutionViewModel(val digimonWorld1Interactor: DigimonWorld1Interactor) :
    ViewModel() {


    private val _uiState = MutableStateFlow(DigimonEvolutionState())

    val uiState: StateFlow<DigimonEvolutionState> = _uiState.asStateFlow()


    fun onTriggerEvent(event: DigimonEvolutionEvent) {
        when (event) {
            is DigimonEvolutionEvent.GetDigimon -> getDigimon(event.id)
            is DigimonEvolutionEvent.EvolutionHelperUpdate -> _uiState.update { it.copy(digimon = event.evolutionHelper) }
            is DigimonEvolutionEvent.ApplyHelper -> applyHelper()
        }
    }

    private fun applyHelper() {
        digimonWorld1Interactor.getToEvolution(
            _uiState.value.entity?.digimon?.id ?: 0,
            _uiState.value.digimon.copy()
        )
            .onEach { digimon ->
                _uiState.update { it.copy(to = mutableStateListOf(*digimon.toTypedArray())) }
            }.launchIn(viewModelScope)
    }

    private fun getDigimon(id: Int) {
        combine(
            digimonWorld1Interactor
                .getById(id)
        ) {
            _uiState.update { newState ->
                newState.copy(
                    entity = it.last(),
                    digimon = newState.digimon.copy(
                        name = it.last().digimon?.name.toString(),
                        level = it.last().digimon?.level.toString()
                    ),
                )
            }
            digimonWorld1Interactor.getToEvolution(id, _uiState.value.digimon.copy())
        }.onEach { evolution ->
            _uiState.update {
                it.copy(
                    to = mutableStateListOf(*evolution.last().toTypedArray()),
                )
            }
        }.launchIn(viewModelScope)
    }
}