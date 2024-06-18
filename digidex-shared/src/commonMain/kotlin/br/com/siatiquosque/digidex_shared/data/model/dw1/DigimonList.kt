package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class DigimonList(
    @Embedded val digimon: DigimonEntity? = null,
    @Relation(
        parentColumn = "ID",
        entityColumn = "ID"
    )
    val info: Info? = null,
)
