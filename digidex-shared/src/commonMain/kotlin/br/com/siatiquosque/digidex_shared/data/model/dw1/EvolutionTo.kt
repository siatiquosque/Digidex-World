package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["Evolution_ID", "To_ID"])
data class EvolutionTo(
    @ColumnInfo("Evolution_ID")
    val evoId: Int,
    @ColumnInfo("To_ID")
    val toId: Int,
    val priority: Int? = null,
)
