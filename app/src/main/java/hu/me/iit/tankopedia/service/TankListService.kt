package hu.me.iit.tankopedia.service

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.navigation.NavController
import hu.me.iit.tankopedia.dao.TankDao
import hu.me.iit.tankopedia.dao.TankDatabase
import hu.me.iit.tankopedia.model.Tank
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TankListService constructor() {

    fun populateNameCountryRow(textViews: List<TextView>, tank: Tank) {
        Log.d("SERV", tank.name!!)
        textViews[0].text = tank.name
        textViews[1].text = tank.nation
    }

    fun populateTypeYearRow(textViews: List<TextView>, tank: Tank) {
        textViews[0].text = tank.type
        textViews[1].text = tank.year.toString()
    }

    fun queryDatabase(db: TankDao): List<Tank>? {
        var tanks: List<Tank>? = null
        GlobalScope.launch {
            tanks = db.getAll()
        }
        Thread.sleep(1000)
        return tanks
    }

}