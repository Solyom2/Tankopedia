package hu.me.iit.tankopedia.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.me.iit.tankopedia.model.Tank

@Database(entities = arrayOf(Tank::class), version = 1)
abstract class TankDatabase : RoomDatabase() {

    abstract fun tankDao() : TankDao

    companion object {
        private var INSTANCE: TankDatabase? = null

        fun getInstance(context: Context): TankDatabase {
            return if (INSTANCE == null) {
                Room.databaseBuilder(context, TankDatabase::class.java, "tankopedia").build()
            } else {
                INSTANCE!!
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}