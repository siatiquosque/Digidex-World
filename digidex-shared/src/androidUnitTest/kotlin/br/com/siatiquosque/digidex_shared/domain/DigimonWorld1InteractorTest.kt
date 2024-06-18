package br.com.siatiquosque.digidex_shared.domain

import br.com.siatiquosque.digidex_shared.data.db.dao.DigimonsDAO
import br.com.siatiquosque.digidex_shared.data.model.dw1.Evolution
import br.com.siatiquosque.digidex_shared.data.model.dw1.EvolutionHelper
import br.com.siatiquosque.digidex_shared.mocks.Mocks
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.every
import io.mockative.mock
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class DigimonWorld1InteractorTest : KoinTest {

    @Mock
    val dao = mock(classOf<DigimonsDAO>())

    val interactor: DigimonWorld1Interactor by inject()

    @BeforeTest
    fun koin() {
        startKoin {
            modules(
                module {
                    single { dao }
                    single { DigimonWorld1Interactor(get()) }
                })
        }
    }

    @Test
    fun checkDigievolution() {
        runBlocking {

            val digimonHelper = EvolutionHelper(
                name = "Agumon",
                level = "CHAMPION",
                hp = 1600,
                mp = 900,
                offense = 160,
                defense = 100,
                speed = 100,
                brains = 100,
                care = 0,
                weight = 25,
                disc = 0,
                happy = 0,
                battles = 0,
                techs = 0,
            )

            val evolution: List<Evolution> = Json.decodeFromString<List<Evolution>>(Mocks.evolutions).sortedBy { it.priority }

            val result = interactor.checkDigievolution(
                digimonHelper, evolution
            )

            assertTrue { result.first().score == 115 }
            assertTrue { result.first { it.selected }.name == "Greymon" }
            assertTrue { !result.first { it.name == "Meramon" }.enabled }
            assertTrue { !result.first { it.name == "Birdramon" }.enabled }


        }

    }
}