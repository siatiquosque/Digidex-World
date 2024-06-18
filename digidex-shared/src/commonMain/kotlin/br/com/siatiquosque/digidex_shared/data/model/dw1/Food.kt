package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["ID"],
            childColumns = ["ID"],
        ),
    ]
)
data class Food(
    @PrimaryKey
    @ColumnInfo("ID")
    val id: Int? = null,
    val item: String? = null,
    val energy: Int? = null,
    val weight: Int? = null,
    val tiredness: String? = null,
    val happiness: String? = null,
    val discipline: String? = null,
    val lifetime: String? = null,
    val sickness: String? = null,
    val heal_hp: String? = null,
    val heal_mp: String? = null,
    val hp: String? = null,
    val mp: String? = null,
    val offense: String? = null,
    val defense: String? = null,
    val speed: String? = null,
    val brains: String? = null,
    val buff_flag: String? = null,
    val buff_value: String? = null,
    val buff_duration: String? = null,
)
