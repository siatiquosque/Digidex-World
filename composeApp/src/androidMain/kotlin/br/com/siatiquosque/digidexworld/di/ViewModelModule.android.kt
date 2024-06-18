package br.com.siatiquosque.digidexworld.di

import br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.viewmodel.DigimonDetailViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.viewmodel.DigimonEvolutionViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel.DigimonListViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.item.viewmodel.ItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule: Module = module {


    //Fix when supporting KMM SavedState
    viewModel { DigimonListViewModel(digimonWorld1Interactor = get()) }
    viewModel { DigimonDetailViewModel(digimonWorld1Interactor = get()) }
    viewModel { DigimonEvolutionViewModel(digimonWorld1Interactor = get()) }
    viewModel { ItemViewModel(digimonWorld1Interactor = get()) }
}