package hu.me.iit.tankopedia.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hu.me.iit.tankopedia.model.Tank

@Dao
interface TankDao {

    @Query("SELECT * FROM tank")
    fun getAll(): List<Tank>

    @Query("SELECT * FROM tank WHERE id IN (:tankIds)")
    fun loadAllByIds(tankIds: IntArray): List<Tank>

    @Query("SELECT * FROM tank WHERE name LIKE :name")
    fun findByName(name: String): Tank

    @Insert
    fun insertAll(vararg tanks: Tank)

    @Delete
    fun delete(tank: Tank)


}