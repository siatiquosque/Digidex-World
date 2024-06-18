package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlin.collections.Map

@Entity
data class Technique(
    @PrimaryKey
    @ColumnInfo("ID")
    val id: Int?,
    val name: String?,
    val aiTargetDistance: Int?,
    val power: Int?,
    val mp: Int?,
    val invincibleTime: Int?,
    val range: String?,
    val type: String?,
    val effect: String?,
    val accuracy: Int?,
    val effectChance: Int?,
    val bitFlags: Int?,
    val unused: Int?,
    val tier: Int?,
    val first: Int?,
    val second: Int?,
    val third: Int?,
) {
    @Ignore
    var learnPercent: Int? = null

    @Ignore
    var digimons: List<Digimon> = emptyList()

    @Ignore
    var percentage: Int? = null
}
