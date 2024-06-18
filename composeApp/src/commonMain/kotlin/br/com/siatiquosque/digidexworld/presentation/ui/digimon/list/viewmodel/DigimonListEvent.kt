package br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel

sealed class DigimonListEvent {

    data class OnQueryChange(val query: String) : DigimonListEvent()
    data object OnSearch : DigimonListEvent()
    data class OnSort(val sort: ListSort) : DigimonListEvent()
    data class OnFilter(val filter: String?) : DigimonListEvent()
}

enum class ListSort(val label: String) {
    NONE("None"),
    HP("HP"),
    OFF("Offense"),
    DEF("Defense");

    companion object {
        fun getByLabel(label: String): ListSort? {
            return ListSort.entries.firstOrNull { it.label == label }
        }
    }
}