package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Item"
)
data class ItemEntity(
    @PrimaryKey
    @ColumnInfo("ID")
    val id: Int?,
    val name: String?,
    val price: Int?,
    val meritValue: Int?,
    val sortingValue: Int?,
    val color: Int?,
    val dropable: String?,
    val unknown: Int?,
)
