package br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.viewmodel

import br.com.siatiquosque.digidex_shared.data.model.dw1.EvolutionHelper

sealed class DigimonEvolutionEvent {

    data class GetDigimon(val id: Int) : DigimonEvolutionEvent()
    data class EvolutionHelperUpdate(val evolutionHelper: EvolutionHelper) : DigimonEvolutionEvent()
    class ApplyHelper : DigimonEvolutionEvent()


}