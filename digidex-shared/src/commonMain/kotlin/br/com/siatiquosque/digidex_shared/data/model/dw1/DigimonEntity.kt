package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.compose.resources.DrawableResource

@Entity(
    tableName = "Digimon",
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["ID"],
            childColumns = ["dropItem"],
        ),
        ForeignKey(
            entity = Technique::class,
            parentColumns = ["ID"],
            childColumns = ["finisher"],
        ),
    ],
    indices = [Index(value = ["ID"])]
)
data class DigimonEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("ID")
    val id: Int? = null,
    val name: String? = null,
    val nodeCount: Int? = null,
    val radius: Int? = null,
    val height: Int? = null,
    val typus: String? = null,
    val level: String? = null,
    val speciality1: String? = null,
    val speciality2: String? = null,
    val speciality3: String? = null,
    val dropItem: Int? = null,
    val finisher: Int? = null,
    val dropChance: Int? = null,
    val isDigimon: Boolean? = true,
) {
    @Ignore
    var image: DrawableResource? = null
    @Ignore
    var sprite: DrawableResource? = null
}