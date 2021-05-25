package hu.me.iit.tankopedia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.me.iit.tankopedia.dao.TankDatabase
import hu.me.iit.tankopedia.filters.CountryFilter
import hu.me.iit.tankopedia.filters.NameFilter
import hu.me.iit.tankopedia.filters.TypeFilter
import hu.me.iit.tankopedia.model.Tank
import hu.me.iit.tankopedia.service.TankListService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TankList : Fragment() {

    private val tankListService = TankListService()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tank_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateButtons(view)
        populateSpinners(view)
        populateTable(view)
    }

    private fun populateTable(view: View) {
        val db = TankDatabase.getInstance(super.requireContext()).tankDao()
        var tanks = tankListService.queryDatabase(db)
        tanks = checkFilters(tanks, view)

        var rowStrip = 0
        val tankTable = view.findViewById<TableLayout>(R.id.tank_table)
        tankTable.removeAllViewsInLayout()
        if (tanks != null) {
            for(tank in tanks) {
                layoutInflater.inflate(R.layout.name_country_row, tankTable,true)
                layoutInflater.inflate(R.layout.type_year_row, tankTable,true)
                layoutInflater.inflate(R.layout.buttons_row, tankTable,true)

                val childrenCount = tankTable.childCount
                val rows = tankTable.children.toList()
                val nameCountryRow = rows[childrenCount-3] as TableRow
                val typeYearRow = rows[childrenCount-2] as TableRow
                val buttonsRow = rows[childrenCount-1] as TableRow

                val nameCountryTextViews = (nameCountryRow.children.toList()) as List<TextView>
                tankListService.populateNameCountryRow(nameCountryTextViews, tank)
                nameCountryTextViews[0].setOnClickListener {
                    if(tank.id != null) {
                        val action = TankListDirections.actionTankListToTankDetails(tank.id)
                        findNavController().navigate(action)
                    }
                }

                val typeYearTextViews = (typeYearRow.children.toList()) as List<TextView>
                tankListService.populateTypeYearRow(typeYearTextViews, tank)

                val buttonsList = (buttonsRow.children.toList()) as List<Button>
                buttonsList[0].setOnClickListener {
                    if(tank.id != null) {
                        val action = TankListDirections.actionTankListToTankForm(true, tank.id)
                        findNavController().navigate(action)
                    }
                }
                buttonsList[1].setOnClickListener {
                    tankListService.deleteTank(db, tank)
                    view.findViewById<EditText>(R.id.list_search_name).text.clear()
                    populateSpinners(view)
                    populateTable(view)
                }

                if(rowStrip%2 == 0) {
                    nameCountryRow.setBackgroundColor(resources.getColor(R.color.tank_table_second_row_background))
                    typeYearRow.setBackgroundColor(resources.getColor(R.color.tank_table_second_row_background))
                    buttonsRow.setBackgroundColor(resources.getColor(R.color.tank_table_second_row_background))
                }
                rowStrip++
            }
        }
    }

    private fun populateSpinners(view: View) {
        val db = TankDatabase.getInstance(super.requireContext()).tankDao()
        val typesList = tankListService.queryTypes(db)
        val countriesList = tankListService.queryCountries(db)

        var spinner: Spinner = view.findViewById(R.id.list_types_spinner)
        var adapter: ArrayAdapter<String>? = typesList.let {
            ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item, it
            )
        }
        spinner.adapter = adapter
        spinner.setSelection(typesList.size-1)

        spinner = view.findViewById(R.id.list_countries_spinner)
        adapter = countriesList.let {
            ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_item, it
            )
        }
        spinner.adapter = adapter
        spinner.setSelection(countriesList.size-1)
    }

    private fun checkFilters(tanks: MutableList<Tank>?, view: View): MutableList<Tank>? {
        if(tanks != null) {
            val nameQuery = view.findViewById<EditText>(R.id.list_search_name).text.toString()
            val typeQuery = view.findViewById<Spinner>(R.id.list_types_spinner).selectedItem.toString()
            val countryQuery = view.findViewById<Spinner>(R.id.list_countries_spinner).selectedItem.toString()

            val nameFilter = NameFilter(nameQuery)
            val typeFilter = TypeFilter(typeQuery)
            val countryFilter = CountryFilter(countryQuery)
            typeFilter.next = countryFilter
            nameFilter.next = typeFilter
            return nameFilter.filterList(tanks)
        }
        return tanks
    }

    private fun populateButtons(view: View) {
        view.findViewById<Button>(R.id.list_search_button).setOnClickListener {
            populateTable(view)
        }
        view.findViewById<Button>(R.id.list_back_button).setOnClickListener {
            val action = TankListDirections.actionTankListToFirstFragment()
            findNavController().navigate(action)
        }
    }

}