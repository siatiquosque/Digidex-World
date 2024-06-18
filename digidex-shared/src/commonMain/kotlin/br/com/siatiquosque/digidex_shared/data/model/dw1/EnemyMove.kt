package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["Enemy_ID", "Technique_ID"],)
data class EnemyMove(
    @ColumnInfo("Enemy_ID")
    val enemyId: Int,
    @ColumnInfo("Technique_ID")
    val technique: Int,
    val percentage: Int? = null,
)
