package br.com.siatiquosque.digidexworld.presentation.ui.technique.list.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TechListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TechListState())

    val uiState: StateFlow<TechListState> = _uiState.asStateFlow()

}