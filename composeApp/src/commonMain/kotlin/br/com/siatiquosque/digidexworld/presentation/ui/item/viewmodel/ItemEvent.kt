package br.com.siatiquosque.digidexworld.presentation.ui.item.viewmodel

sealed class ItemEvent {

    data class OnQueryChange(val query: String) : ItemEvent()
    class OnSearch : ItemEvent()
}