package hu.me.iit.tankopedia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.me.iit.tankopedia.dao.TankDatabase
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

        populateTable(view)
    }

    private fun populateTable(view: View) {
        val db = TankDatabase.getInstance(super.requireContext()).tankDao()
        val tanks = tankListService.queryDatabase(db)
        Log.d("LS", tanks.toString())

        var rowStrip = 0
        if (tanks != null) {
            for(tank in tanks) {
                if(rowStrip >= 2) break
                Log.d("ITER", tank.toString())

                val tankTable = view.findViewById<TableLayout>(R.id.tank_table)
                layoutInflater.inflate(R.layout.name_country_row, tankTable,true)
                layoutInflater.inflate(R.layout.type_year_row, tankTable,true)
                layoutInflater.inflate(R.layout.buttons_row, tankTable,true)

                val rows = tankTable.children.toList()
                val nameCountryRow = rows[0] as TableRow
                val typeYearRow = rows[1] as TableRow
                val buttonsRow = rows[2] as TableRow

                val nameCountryTextViews = (nameCountryRow.children.toList()) as List<TextView>
                tankListService.populateNameCountryRow(nameCountryTextViews, tank)
                nameCountryTextViews[0].setOnClickListener {
                    Log.d("NCROW", tank.toString())
                }

                val typeYearTextViews = (typeYearRow.children.toList()) as List<TextView>
                tankListService.populateTypeYearRow(typeYearTextViews, tank)

                val buttonsList = (buttonsRow.children.toList()) as List<Button>
                buttonsList[0].setOnClickListener {
                    Log.d("MODIFY", tank.toString())
                }

                buttonsList[1].setOnClickListener {
                    Log.d("DELETE", tank.toString())
                }

                if(rowStrip%2 == 0) {
                    nameCountryRow.setBackgroundColor(resources.getColor(R.color.tank_table_second_row_background))
                    typeYearRow.setBackgroundColor(resources.getColor(R.color.tank_table_second_row_background))
                    buttonsRow.setBackgroundColor(resources.getColor(R.color.tank_table_second_row_background))
                }
                rowStrip++

                /*newNameCountryRow?.findViewById<TextView>(R.id.name_textView)?.text = tank.name
                newNameCountryRow?.findViewById<TextView>(R.id.country_textView)?.text = tank.nation
                newTypeYearRow?.findViewById<TextView>(R.id.type_textView)?.text = tank.type
                newTypeYearRow?.findViewById<TextView>(R.id.year_textView)?.text = tank.year.toString()

                if(rowStrip%2 == 0) {
                    newNameCountryRow?.setBackgroundColor(resources.getColor(R.color.tank_table_second_row_background))
                    newNameCountryRow?.setBackgroundColor(resources.getColor(R.color.tank_table_second_row_background))
                    newButtonsRow?.setBackgroundColor(resources.getColor(R.color.tank_table_second_row_background))
                }
                rowStrip++

                tankTableLayout.addView(newNameCountryRow)
                tankTableLayout.addView(newTypeYearRow)
                tankTableLayout.addView(newButtonsRow)*/
            }
        }
    }

    private fun queryDatabase(): List<Tank>? {
        var tanks: List<Tank>? = null
        GlobalScope.launch {
            val db = TankDatabase.getInstance(super.requireContext()).tankDao()
            tanks = db.getAll()
        }
        Thread.sleep(1000)
        return tanks
    }

}