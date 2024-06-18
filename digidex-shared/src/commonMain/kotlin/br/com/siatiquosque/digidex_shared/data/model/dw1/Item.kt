package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.Embedded
import androidx.room.Relation

data class Item(
    @Embedded
    val item: ItemEntity? = null,
    @Relation(parentColumn = "ID", entityColumn = "ID")
    val food: Food? = null
)