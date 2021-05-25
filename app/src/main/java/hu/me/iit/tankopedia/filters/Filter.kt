package hu.me.iit.tankopedia.filters

import hu.me.iit.tankopedia.model.Tank

interface Filter {

    fun filterList(list: MutableList<Tank>): MutableList<Tank>

}