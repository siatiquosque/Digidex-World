package br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.viewmodel

import androidx.compose.runtime.mutableStateListOf
import br.com.siatiquosque.digidex_shared.data.model.dw1.Digimon

data class DigimonDetailState(
    val entity: List<Digimon>? = mutableStateListOf()
)