package hu.me.iit.tankopedia.dao

import androidx.room.*
import hu.me.iit.tankopedia.model.Tank

@Dao
interface TankDao {

    @Query("SELECT * FROM tank")
    suspend fun getAll(): MutableList<Tank>

    @Query("SELECT * FROM tank WHERE id IN (:tankIds)")
    suspend fun getAllByIds(tankIds: IntArray): List<Tank>

    @Query("SELECT * FROM tank WHERE id == :id")
    suspend fun findById(id: Int): Tank

    @Query("SELECT * FROM tank WHERE name LIKE :name")
    suspend fun findByName(name: String): Tank

    @Query("SELECT DISTINCT nation FROM tank")
    suspend fun queryCountries(): MutableList<String>

    @Query("SELECT DISTINCT type FROM tank")
    suspend fun queryTypes(): MutableList<String>

    @Insert
    suspend fun insert(tank: Tank)

    @Insert
    suspend fun insertAll(vararg tanks: Tank)

    @Update
    suspend fun update(tank: Tank)

    @Delete
    suspend fun delete(tank: Tank)

}