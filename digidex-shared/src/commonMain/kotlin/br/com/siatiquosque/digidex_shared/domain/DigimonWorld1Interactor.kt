package br.com.siatiquosque.digidex_shared.domain

import br.com.siatiquosque.digidex_shared.data.db.dao.DigimonsDAO
import br.com.siatiquosque.digidex_shared.data.model.dw1.Digimon
import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonList
import br.com.siatiquosque.digidex_shared.data.model.dw1.Evolution
import br.com.siatiquosque.digidex_shared.data.model.dw1.EvolutionHelper
import br.com.siatiquosque.digidex_shared.data.model.dw1.Item
import br.com.siatiquosque.digidex_shared.data.model.dw1.Technique
import br.com.siatiquosque.digidex_shared.utils.ImageMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DigimonWorld1Interactor(private val digimonsDAO: DigimonsDAO) {

    fun getAllDigimons(): Flow<List<DigimonList>> {
        return flow {
            emit(digimonsDAO.getAllDigimon().map {
                it.digimon?.image = ImageMapper.DigimonImg[it.digimon?.name.toString()]
                it
            })
        }.flowOn(Dispatchers.IO)
    }

    fun getById(id: Int, helper: EvolutionHelper? = null): Flow<Digimon> {
        return flow {
            val digimon = digimonsDAO.getById(id)
            digimon.from = digimonsDAO.getEvolutionFromById(id)
//            digimon.to = digimonsDAO.getEvolutionToById(id)
//            val list = digimonsDAO.getEvolutionToById(id)
//            digimon.to = if (helper != null) checkDigievolution(helper, list) else list
            emit(handleMap(digimon))
        }.flowOn(Dispatchers.IO)
    }

    fun getToEvolution(id: Int, helper: EvolutionHelper): Flow<List<Evolution>> {
        return flow {
            emit(checkDigievolution(helper, digimonsDAO.getEvolutionToById(id)))
        }.flowOn(Dispatchers.IO)
    }

    fun getTechByID(id: Int): Flow<Technique> {
        return flow {
            val tech = digimonsDAO.getTechsById(id)
            val enemy = digimonsDAO.getEnemyByTech(id)
                .filter { it.map?.id?.contains("ROOM") == false && !it.map.id.contains("TWN") }
                .groupBy { it.enemy.digimonId }


            tech.digimons = enemy.map {
                val digimon = digimonsDAO.getById(it.key ?: 0)
                digimon.spawn = it.value.map { enemy ->
                    enemy.move = digimonsDAO.getTechsByEnemy(enemy.enemy.id ?: 0).filter { it.id == id }
                    enemy
                }
                digimon
            }
            emit(tech)
        }.flowOn(Dispatchers.IO)
    }

    fun searchByName(name: String): Flow<List<Digimon>> {
        return flow {
            emit(digimonsDAO.searchByName(name).map {
                handleMap(it)
            })
        }.flowOn(Dispatchers.IO)
    }

    fun getItemsAsFood(): Flow<List<Item>> {
        return flow {
            emit(digimonsDAO.getItemsAsFood().filter { it.food != null })
        }.flowOn(Dispatchers.IO)
    }


    fun getAllTypes(): Flow<List<String>> {
        return flow {
            emit(digimonsDAO.getAllTypes())
        }.flowOn(Dispatchers.IO)
    }

    private fun handleMap(digimon: Digimon): Digimon {

        digimon.digimon?.image = ImageMapper.DigimonImg[digimon.digimon?.name.toString()]
        digimon.digimon?.sprite = ImageMapper.DigimonWorld1Sprites[digimon.digimon?.name.toString()]

        digimon.moves = digimon.moves?.map {
            it.learnPercent = when (it.type) {
                digimon.digimon?.speciality1 -> {
                    it.first
                }

                digimon.digimon?.speciality2 -> {
                    it.second
                }

                digimon.digimon?.speciality3 -> {
                    it.third
                }

                else -> {
                    0
                }
            }
            it
        }

        return digimon
    }

    fun checkDigievolution(digimon: EvolutionHelper, evolution: List<Evolution>): List<Evolution> {
        return when (digimon.level) {
            "ROOKIE", "CHAMPION" -> {
                var carriedOverStats = 0
                var carriedOverCount = 0
                var maxScore = 0


                val result = evolution.map {

                    var reqEnabled = 0
                    var statsSum = 0
                    var statsCount = 0
                    var reqStats = 0

                    //begin Stats
                    digimon.hp?.let { hp ->
                        if (it.hp != null) {
                            reqStats++
                            if (hp / 10 >= it.hp) {
                                statsSum += hp / 10
                                statsCount++
                            }
                        }
                    }

                    digimon.mp?.let { mp ->
                        if (it.mp != null) {
                            reqStats++
                            if (mp / 10 >= it.mp) {
                                statsSum += mp / 10
                                statsCount++
                            }
                        }
                    }

                    digimon.offense?.let { offense ->
                        if (it.offense != null) {
                            reqStats++
                            if (offense >= it.offense) {
                                statsSum += offense
                                statsCount++
                            }
                        }
                    }

                    digimon.defense?.let { defense ->
                        if (it.defense != null) {
                            reqStats++
                            if (defense >= it.defense) {
                                statsSum += defense
                                statsCount++
                            }
                        }
                    }

                    digimon.speed?.let { speed ->
                        if (it.speed != null) {
                            reqStats++
                            if (speed >= it.speed) {
                                statsSum += speed
                                statsCount++
                            }
                        }
                    }

                    digimon.brains?.let { speed ->
                        if (it.brains != null) {
                            reqStats++
                            if (speed >= it.brains) {
                                statsSum += speed
                                statsCount++
                            }
                        }
                    }

                    if (reqStats > 0) {
                        if (reqStats == statsCount) {
                            it.statsEnabled = true
                            reqEnabled++
                        } else {
                            it.statsEnabled = false
                        }
                    }
                    //end Stats

                    //begin Care
                    //                digimon.care?.let { care ->
                    if (it.care != null &&
                        ((it.flags?.shr(4)?.and(1) == 1 && (digimon.care ?: 0) <= it.care) ||
                                (it.flags?.shr(4)?.and(1) == 0 && (digimon.care ?: 0) >= it.care))
                    ) {
                        it.careEnabled = true
                        reqEnabled++
                    }
                    //                }
                    //end Care

                    //begin Weight
                    //                digimon.weight?.let { weight ->
                    if (it.weight != null && ((digimon.weight
                            ?: 0) >= it.weight - 5 && (digimon.weight
                            ?: 0) <= it.weight + 5)
                    ) {
                        it.weightEnabled = true
                        reqEnabled++
                    }
                    //                }
                    //end Weight

                    //begin Bonus
                    //                digimon.happy?.let { happy ->
                    if (it.happy != null &&
                        ((it.flags?.shr(1)?.and(1) == 1 && (digimon.happy ?: 0) <= it.happy) ||
                                (it.flags?.shr(1)?.and(1) == 0 && (digimon.happy ?: 0) >= it.happy))
                    ) {
                        it.bonusEnabled = true
                        reqEnabled++
                    }
                    //                }
                    //                digimon.disc?.let { disc ->
                    if (it.disc != null &&
                        ((it.flags?.shr(2)?.and(1) == 1 && (digimon.disc ?: 0) <= it.disc) ||
                                (it.flags?.shr(2)?.and(1) == 0 && (digimon.disc ?: 0) >= it.disc))
                    ) {
                        it.bonusEnabled = true
                        reqEnabled++
                    }
                    //                }

                    //                digimon.battles?.let { battles ->
                    if (it.battles != null &&
                        ((it.flags?.shr(0)?.and(1) == 1 && (digimon.battles ?: 0) <= it.battles) ||
                                (it.flags?.shr(0)?.and(1) == 0 && (digimon.battles
                                    ?: 0) >= it.battles))
                    ) {
                        it.bonusEnabled = true
                        reqEnabled++
                    }
                    //                }

                    //                digimon.techs?.let { techs ->
                    if (it.techs != null && (digimon.techs ?: 0) >= it.techs) {
                        it.bonusEnabled = true
                        reqEnabled++
                    }
                    //                }

                    if (digimon.name == it.bonus) {
                        it.bonusEnabled = true
                        reqEnabled++
                    }
                    //end Bonus

                    //Check Enabled
                    if (reqEnabled >= 3) {
                        it.enabled = true
                    }

                    //Check Score
                    if (it.enabled && statsCount > 0) {
                        it.score = (statsSum + carriedOverStats) / (statsCount + carriedOverCount)

                        //Check Score
                        if ((it.score ?: 0) <= maxScore) {
                            carriedOverStats = (it.score ?: 0)
                            carriedOverCount += reqStats
                        }

                        //Update MaxScore
                        if ((it.score ?: 0) > maxScore) {
                            maxScore = (it.score ?: 0)
                            carriedOverCount = 0
                            carriedOverStats = 0
                        }
                    }
                    it
                }

                result.maxBy { (it.score ?: 0) }.selected = true
                result
            }

            "IN-TRAINING" -> {

                val result = evolution.map {
                    var maxStatus = 0

                    var reqEnabled = 0
                    var statsCount = 0
                    var reqStats = 0

                    //begin Stats
                    digimon.hp?.let { hp ->
                        if (it.hp != null) {
                            reqStats++
                            if (hp > 0) {
                                statsCount++
                                if (hp / 10 > maxStatus) {
                                    maxStatus = hp / 10
                                }
                            }
                        }
                    }

                    digimon.mp?.let { mp ->
                        if (it.mp != null) {
                            reqStats++
                            if (mp > 0) {
                                statsCount++
                                if (mp / 10 > maxStatus) {
                                    maxStatus = mp / 10
                                }
                            }
                        }
                    }

                    digimon.offense?.let { offense ->
                        if (it.offense != null) {
                            reqStats++
                            if (offense >= it.offense) {
                                statsCount++
                                if (offense > maxStatus) {
                                    maxStatus = offense
                                }
                            }
                        }
                    }

                    digimon.defense?.let { defense ->
                        if (it.defense != null) {
                            reqStats++
                            if (defense >= it.defense) {
                                statsCount++
                                if (defense > maxStatus) {
                                    maxStatus = defense
                                }
                            }
                        }
                    }

                    digimon.speed?.let { speed ->
                        if (it.speed != null) {
                            reqStats++
                            if (speed >= it.speed) {
                                statsCount++
                                if (speed > maxStatus) {
                                    maxStatus = speed
                                }
                            }
                        }
                    }

                    digimon.brains?.let { speed ->
                        if (it.brains != null) {
                            reqStats++
                            if (speed >= it.brains) {
                                statsCount++
                                if (speed > maxStatus) {
                                    maxStatus = speed
                                }
                            }
                        }
                    }

                    if (reqStats > 0) {
                        if (reqStats == statsCount) {
                            it.statsEnabled = true
                            reqEnabled++
                        } else {
                            it.statsEnabled = false
                        }
                    }
                    //end Stats

                    //begin Care
                    //                digimon.care?.let { care ->
                    if (it.care != null &&
                        ((it.flags?.shr(4)?.and(1) == 1 && (digimon.care ?: 0) <= it.care) ||
                                (it.flags?.shr(4)?.and(1) == 0 && (digimon.care ?: 0) >= it.care))
                    ) {
                        it.careEnabled = true
                        reqEnabled++
                    }
                    //                }
                    //end Care

                    //begin Weight
                    //                digimon.weight?.let { weight ->
                    if (it.weight != null && ((digimon.weight
                            ?: 0) >= it.weight - 5 && (digimon.weight
                            ?: 0) <= it.weight + 5)
                    ) {
                        it.weightEnabled = true
                        reqEnabled++
                    }
                    //                }
                    //end Weight

                    //begin Bonus
                    //                digimon.happy?.let { happy ->
                    if (it.happy != null &&
                        ((it.flags?.shr(1)?.and(1) == 1 && (digimon.happy ?: 0) <= it.happy) ||
                                (it.flags?.shr(1)?.and(1) == 0 && (digimon.happy ?: 0) >= it.happy))
                    ) {
                        reqEnabled++
                    }
                    //                }
                    //                digimon.disc?.let { disc ->
                    if (it.disc != null &&
                        ((it.flags?.shr(2)?.and(1) == 1 && (digimon.disc ?: 0) <= it.disc) ||
                                (it.flags?.shr(2)?.and(1) == 0 && (digimon.disc ?: 0) >= it.disc))
                    ) {
                        reqEnabled++
                    }
                    //                }

                    //                digimon.battles?.let { battles ->
                    if (it.battles != null &&
                        ((it.flags?.shr(0)?.and(1) == 1 && (digimon.battles ?: 0) <= it.battles) ||
                                (it.flags?.shr(0)?.and(1) == 0 && (digimon.battles
                                    ?: 0) >= it.battles))
                    ) {
                        it.bonusEnabled = true
                        reqEnabled++
                    }
                    //                }

                    //                digimon.techs?.let { techs ->
                    if (it.techs != null && (digimon.techs ?: 0) >= it.techs) {
                        it.bonusEnabled = true
                        reqEnabled++
                    }
                    //                }

                    if (digimon.name == it.bonus) {
                        it.bonusEnabled = true
                        reqEnabled++
                    }
                    //end Bonus

                    //Check Enabled
                    if (reqEnabled >= 3) {
                        it.enabled = true
                    }

                    if (maxStatus > 0) {
                        it.score = maxStatus
                    }

                    it
                }

                result.maxBy { (it.score ?: 0) }.selected = true
                result
            }

            else -> {
                evolution
            }
        }
    }

}