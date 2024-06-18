package br.com.siatiquosque.digidexworld

import android.app.Application
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.siatiquosque.digidexworld.di.appModule
import br.com.siatiquosque.digidexworld.presentation.ui.digimon.list.viewmodel.DigimonListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class DigidexApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // inject Android context
            androidContext(this@DigidexApplication)

            modules(appModule)
        }
    }
}