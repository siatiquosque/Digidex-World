package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Enemy(
    @PrimaryKey
    @ColumnInfo("ID")
    val id  : Int? = null,
    @ColumnInfo("Map_ID")
    val mapId: String? = null,
    @ColumnInfo("Digimon_ID")
    val digimonId: Int? = null,
    val currenthp: Int? = null,
    val currentmp: Int? = null,
    val hp: Int? = null,
    val mp: Int? = null,
    val offense: Int? = null,
    val defense: Int? = null,
    val speed: Int? = null,
    val brains: Int? = null,
    val bits: Int? = null,
    val charge_mode: Int? = null,
)