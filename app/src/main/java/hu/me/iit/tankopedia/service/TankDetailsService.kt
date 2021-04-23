package hu.me.iit.tankopedia.service

import hu.me.iit.tankopedia.dao.TankDao
import hu.me.iit.tankopedia.model.Tank
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TankDetailsService {

    fun queryDatabase(db: TankDao, id: Int): Tank? {
        var tank: Tank? = null
        GlobalScope.launch {
            tank = db.findById(id)
        }
        Thread.sleep(1000)
        return tank
    }

}