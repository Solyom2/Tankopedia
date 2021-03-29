package hu.me.iit.tankopedia.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.me.iit.tankopedia.model.Tank

@Database(entities = arrayOf(Tank::class), version = 1)
abstract class TankDatabase : RoomDatabase() {

    abstract fun tankDao() : TankDao

}