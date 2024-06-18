package br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.viewmodel

sealed class DigimonDetailEvent {

    data class GetDigimon(val name: String) : DigimonDetailEvent()
}