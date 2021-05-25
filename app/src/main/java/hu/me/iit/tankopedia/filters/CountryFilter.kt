package hu.me.iit.tankopedia.filters

import android.util.Log
import hu.me.iit.tankopedia.model.Tank

class CountryFilter constructor(val query: String) : Filter {

    var next: Filter? = null

    override fun filterList(list: MutableList<Tank>): MutableList<Tank> {
        if(query.isNotEmpty() && query != "Any") {
            val iterator = list.iterator()
            while(iterator.hasNext()) {
                val tank = iterator.next()
                if (!tank.nation.isNullOrEmpty() && tank.nation != query) {
                    iterator.remove()
                }
            }
        }
        if(next != null) {
            return next!!.filterList(list)
        }
        return list
    }

}