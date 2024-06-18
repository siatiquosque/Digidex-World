package br.com.siatiquosque.digidexworld

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import br.com.siatiquosque.digidexworld.di.appModule
import initKoin
import org.koin.compose.KoinApplication
import org.koin.dsl.module

private val koin = initKoin().koin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Digidex World",
    ) {
        App()
    }
}