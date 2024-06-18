package br.com.siatiquosque.digidex_shared.data.model.dw1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Map(
    @PrimaryKey
    @ColumnInfo("ID")
    val id: String,
    val name: String? = null,
    val description: String? = null,
) {
    @Ignore
    var enemyPercentage: Int? = null
}
