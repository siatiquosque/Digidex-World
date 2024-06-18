package br.com.siatiquosque.digidex_shared.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.siatiquosque.digidex_shared.data.db.dao.DigimonsDAO
import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonEntity
import br.com.siatiquosque.digidex_shared.data.model.dw1.Enemy
import br.com.siatiquosque.digidex_shared.data.model.dw1.EnemyMove
import br.com.siatiquosque.digidex_shared.data.model.dw1.Evolution
import br.com.siatiquosque.digidex_shared.data.model.dw1.EvolutionFrom
import br.com.siatiquosque.digidex_shared.data.model.dw1.EvolutionTo
import br.com.siatiquosque.digidex_shared.data.model.dw1.Food
import br.com.siatiquosque.digidex_shared.data.model.dw1.Info
import br.com.siatiquosque.digidex_shared.data.model.dw1.ItemEntity
import br.com.siatiquosque.digidex_shared.data.model.dw1.Map
import br.com.siatiquosque.digidex_shared.data.model.dw1.Move
import br.com.siatiquosque.digidex_shared.data.model.dw1.Technique

@Database(
    entities = [
        DigimonEntity::class,
        ItemEntity::class,
        Technique::class,
        Move::class,
        Evolution::class,
        EvolutionFrom::class,
        EvolutionTo::class,
        Food::class,
        Info::class,
        Map::class,
        Enemy::class,
        EnemyMove::class,
    ],
    version = 10,
    exportSchema = false
)
abstract public class DigimonWorld1Database : RoomDatabase() {
    abstract public fun digimonsDao(): DigimonsDAO
}


expect fun getDatabaseBuilder(): RoomDatabase.Builder<DigimonWorld1Database>

