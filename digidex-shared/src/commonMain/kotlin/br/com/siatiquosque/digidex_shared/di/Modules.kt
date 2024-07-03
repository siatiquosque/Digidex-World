package br.com.siatiquosque.digidex_shared.di

import br.com.siatiquosque.digidex_shared.data.db.DigimonWorld1Database
import br.com.siatiquosque.digidex_shared.data.db.getDatabaseBuilder
import br.com.siatiquosque.digidex_shared.data.model.dw1.EvolutionHelper
import br.com.siatiquosque.digidex_shared.domain.DigimonWorld1Interactor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val platformModule = module {
    single {
        getDatabaseBuilder()
            .fallbackToDestructiveMigration(true)
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single {
        val dw1DB = get<DigimonWorld1Database>()
        dw1DB.digimonsDao()
    }

    single {
        DigimonWorld1Interactor(get())
    }

    single {
        EvolutionHelper()
    }
}