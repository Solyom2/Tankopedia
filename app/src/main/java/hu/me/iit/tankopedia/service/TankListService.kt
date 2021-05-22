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

class TankListService {

    fun populateNameCountryRow(textViews: List<TextView>, tank: Tank) {
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

    fun deleteTank(db: TankDao, tank: Tank) {
        GlobalScope.launch {
            db.delete(tank)
        }
        Thread.sleep(1000)
    }

    fun queryTypes(db: TankDao): List<String> {
        var types: List<String> = emptyList()
        GlobalScope.launch {
            types = db.queryTypes()
        }
        Thread.sleep(1000)
        types.plus("Any")
        return types
    }

    fun queryCountries(db: TankDao): List<String> {
        var countries: List<String> = emptyList()
        GlobalScope.launch {
            countries = db.queryCountries()
        }
        Thread.sleep(1000)
        countries.plus("Any")
        return countries
    }

}