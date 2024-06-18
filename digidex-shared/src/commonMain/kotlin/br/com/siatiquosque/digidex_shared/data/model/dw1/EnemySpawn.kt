package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Relation

@Entity
data class EnemySpawn(
    @Embedded
    val enemy: Enemy,

    @Relation(
        parentColumn = "Map_ID",
        entityColumn = "ID"
    )
    val map: Map? = null,

    @Relation(
        parentColumn = "ID",
        entityColumn = "Enemy_ID"
    )
    val enemyMove: EnemyMove? = null,
) {
    @Ignore
    var move: List<Technique>? = null
}