package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["Evolution_ID", "From_ID"],)
data class EvolutionFrom(
    @ColumnInfo("Evolution_ID")
    val evoId: Int,
    @ColumnInfo("From_ID")
    val fromId: Int,
    val priority: Int? = null,
)
