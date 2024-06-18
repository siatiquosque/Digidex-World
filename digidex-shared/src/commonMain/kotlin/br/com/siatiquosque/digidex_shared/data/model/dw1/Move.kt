package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["Digimon_ID", "Technique_ID"],
//    foreignKeys = [
//        /* A MovieId MUST be a value of an existing id column in the movie table */
//        ForeignKey(
//            entity = Digimon::class,
//            parentColumns = ["ID"],
//            childColumns = ["Digimon_ID"],
//            /* Optional (helps maintain referential integrity) */
//            /* if parent is deleted then children rows of that parent are deleted */
//            onDelete = ForeignKey.NO_ACTION,
//            /* if parent column is changed then the column that references the parent is changed to the same value */
//            onUpdate = ForeignKey.NO_ACTION
//        ),
//        ForeignKey(
//            entity = Techniques::class,
//            parentColumns = ["ID"],
//            childColumns = ["Technique_ID"],
//            onDelete = ForeignKey.NO_ACTION,
//            onUpdate = ForeignKey.NO_ACTION
//        )
//    ]
)
data class Move(
    @ColumnInfo("Digimon_ID")
    val digiID: Int,
    @ColumnInfo("Technique_ID")
    val techID: Int
)
