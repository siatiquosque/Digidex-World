package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = DigimonEntity::class,
            parentColumns = ["ID"],
            childColumns = ["ID"],
        ),
    ]
)
@Serializable
data class Evolution(
    @PrimaryKey
    @ColumnInfo("ID")
    val id: Int? = null,
    val name: String? = null,
    val bonus: String? = null,
    val hp: Int? = null,
    val mp: Int? = null,
    val offense: Int? = null,
    val defense: Int? = null,
    val speed: Int? = null,
    val brains: Int? = null,
    val care: Int? = null,
    val weight: Int? = null,
    val disc: Int? = null,
    val happy: Int? = null,
    val battles: Int? = null,
    val techs: Int? = null,
    val flags: Int? = null,
) {

    @Ignore
    var enabled: Boolean = false

    @Ignore
    var score: Int? = null

    @Ignore
    var selected: Boolean = false

    @Ignore
    var priority: Int = 0

    @Ignore
    var statsEnabled : Boolean? = null

    @Ignore
    var careEnabled : Boolean? = null

    @Ignore
    var weightEnabled : Boolean? = null

    @Ignore
    var bonusEnabled : Boolean? = null
}
