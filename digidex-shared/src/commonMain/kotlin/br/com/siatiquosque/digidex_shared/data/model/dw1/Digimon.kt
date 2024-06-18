package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.Embedded
import androidx.room.Ignore
import androidx.room.Junction
import androidx.room.Relation


data class Digimon(
    @Embedded val digimon: DigimonEntity? = null,
    @Relation(
        parentColumn = "dropItem",
        entityColumn = "ID"
    )
    val drop: ItemEntity? = null,
    @Relation(
        parentColumn = "finisher",
        entityColumn = "ID"
    )
    val finisherMove: Technique? = null,
    @Relation(
        parentColumn = "ID",
        entityColumn = "ID",
        associateBy = Junction(
            Move::class,
            parentColumn = "Digimon_ID",
            entityColumn = "Technique_ID"
        )
    )
    var moves: List<Technique>? = null,
//    @Relation(
//        parentColumn = "ID",
//        entityColumn = "ID",
//        associateBy = Junction(
//            EvolutionFrom::class,
//            parentColumn = "From_ID",
//            entityColumn = "Evolution_ID"
//        )
//    )

//    @Relation(
//        parentColumn = "ID",
//        entityColumn = "ID",
//        associateBy = Junction(
//            EvolutionTo::class,
//            parentColumn = "To_ID",
//            entityColumn = "Evolution_ID"
//        )
//    )
    @Relation(
        parentColumn = "ID",
        entityColumn = "ID"
    )
    val info: Info? = null,

) {
//    @Ignore
//    var to: List<Evolution>? = null
//
    @Ignore
    var from: List<Evolution>? = null

    @Ignore
    var spawn: List<EnemySpawn>? = null

}
