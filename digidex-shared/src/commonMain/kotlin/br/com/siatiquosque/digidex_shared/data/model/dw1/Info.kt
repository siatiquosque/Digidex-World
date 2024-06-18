package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = DigimonEntity::class,
            parentColumns = ["ID"],
            childColumns = ["ID"],
        )
    ],
)
data class Info(
    @PrimaryKey
    @ColumnInfo("ID")
    val id: Int? = null,
    val name: String? = null,
    val hp: Int? = null,
    val mp: Int? = null,
    val offense: Int? = null,
    val defense: Int? = null,
    val speed: Int? = null,
    val brains: Int? = null,
)
