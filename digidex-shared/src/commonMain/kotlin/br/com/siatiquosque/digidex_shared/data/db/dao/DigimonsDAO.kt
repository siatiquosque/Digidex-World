package br.com.siatiquosque.digidex_shared.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonEntity
import br.com.siatiquosque.digidex_shared.data.model.dw1.Digimon
import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonList
import br.com.siatiquosque.digidex_shared.data.model.dw1.Enemy
import br.com.siatiquosque.digidex_shared.data.model.dw1.EnemySpawn
import br.com.siatiquosque.digidex_shared.data.model.dw1.Evolution
import br.com.siatiquosque.digidex_shared.data.model.dw1.Item
import br.com.siatiquosque.digidex_shared.data.model.dw1.Technique

@Dao
interface DigimonsDAO {
    @Transaction
    @Query("SELECT * FROM Digimon WHERE isDigimon = 1 group by name order by id")
    suspend fun getAllDigimon(): List<DigimonList>

    @Transaction
    @Query("SELECT * FROM Digimon WHERE id == :id")
    suspend fun getById(id: Int): Digimon

    @Transaction
    @Query("SELECT * FROM Evolution INNER JOIN EvolutionTo ON Evolution.ID = EvolutionTo.To_ID where EvolutionTo.Evolution_ID = :id  ORDER BY EvolutionTo.priority")
    suspend fun getEvolutionToById(id: Int): List<Evolution>

    @Transaction
    @Query("SELECT * FROM Evolution INNER JOIN EvolutionFrom ON Evolution.ID = EvolutionFrom.From_ID where EvolutionFrom.Evolution_ID = :id  ORDER BY EvolutionFrom.priority")
    suspend fun getEvolutionFromById(id: Int): List<Evolution>

    @Transaction
    @Query("SELECT * FROM Digimon WHERE name LIKE '%' || :name || '%'")
    suspend fun searchByName(name: String): List<Digimon>

    @Insert
    suspend fun insert(digimons: DigimonEntity)

    @Transaction
    @Query("SELECT * FROM Item")
    suspend fun getItemsAsFood(): List<Item>

    @Query("SELECT Distinct(typus) FROM Digimon")
    suspend fun getAllTypes(): List<String>

    @Query("SELECT * FROM Technique WHERE id == :id")
    suspend fun getTechsById(id: Int): Technique

    @Query("SELECT * from Enemy INNER JOIN EnemyMove ON EnemyMove.Enemy_ID = Enemy.ID where EnemyMove.Technique_ID = :id")
    suspend fun getEnemyByTech(id: Int): List<EnemySpawn>

    @Query("SELECT *, percentage FROM Technique INNER JOIN EnemyMove ON EnemyMove.Technique_ID = Technique.ID where EnemyMove.Enemy_ID == :id")
    suspend fun getTechsByEnemy(id: Int): List<Technique>

    @Query("SELECT * FROM Enemy WHERE Map_ID == :mapId and Digimon_ID == :digimonId")
    suspend fun getEnemyById(mapId: Int, digimonId: Int) : Enemy
}