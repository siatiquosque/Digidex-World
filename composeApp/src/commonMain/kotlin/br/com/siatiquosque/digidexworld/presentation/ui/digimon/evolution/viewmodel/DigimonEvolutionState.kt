package br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.viewmodel

import androidx.compose.runtime.mutableStateListOf
import br.com.siatiquosque.digidex_shared.data.model.dw1.Digimon
import br.com.siatiquosque.digidex_shared.data.model.dw1.Evolution
import br.com.siatiquosque.digidex_shared.data.model.dw1.EvolutionHelper

data class DigimonEvolutionState(
    val entity: Digimon? = null,
    val to: List<Evolution> = mutableStateListOf(),
//    val from: List<Evolution> = emptyList(),
    val digimon: EvolutionHelper = EvolutionHelper()
)