package br.com.siatiquosque.digidexworld.presentation.ui.technique.list.viewmodel

import br.com.siatiquosque.digidex_shared.data.model.dw1.Technique

data class TechListState(
    var items: List<Technique> = listOf()
)