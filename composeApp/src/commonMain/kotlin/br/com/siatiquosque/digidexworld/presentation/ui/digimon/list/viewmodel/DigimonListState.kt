package br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel

import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonList

data class DigimonListState(
    val digimons: List<DigimonList> = listOf(),
    val types: List<String> = listOf(),
    val selectedType : String? = null,
    val sort: ListSort = ListSort.NONE,
    val query: String = "",
)