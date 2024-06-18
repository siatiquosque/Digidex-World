package br.com.siatiquosque.digidexworld.di

import br.com.siatiquosque.digidexworld.presentation.ui.digimon.detail.viewmodel.DigimonDetailViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.evolution.viewmodel.DigimonEvolutionViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel.DigimonListViewModel
import br.com.siatiquosque.digidexworld.presentation.ui.item.viewmodel.ItemViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val viewModelModule: Module = module {


    //Fix when supporting KMM SavedState
    factory { DigimonListViewModel(digimonWorld1Interactor = get()) }
    factory { DigimonDetailViewModel(digimonWorld1Interactor = get()) }
    factory { DigimonEvolutionViewModel(digimonWorld1Interactor = get()) }
    factory { ItemViewModel(digimonWorld1Interactor = get()) }
}