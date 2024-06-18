package br.com.siatiquosque.digidexworld.presentation.ui.item.viewmodel

import br.com.siatiquosque.digidex_shared.data.model.dw1.Item

data class ItemState(
    var query: String = "",
    var items: List<Item> = listOf()
)