package hu.me.iit.tankopedia.service

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import hu.me.iit.tankopedia.R
import hu.me.iit.tankopedia.dao.TankDao
import hu.me.iit.tankopedia.model.Tank
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TankFormService {

    fun validateTextInput(fields: List<String>): Boolean {
        for(field in fields) {
            if(TextUtils.isEmpty(field)) return false
        }
        return true
    }

    fun validateNumericInput(fields: List<EditText>): Boolean {
        for(field in fields) {
            if(TextUtils.isEmpty(field.text.toString()) && field.text.toString().toInt() < 0) return false
        }
        return true
    }

    fun queryDatabase(db: TankDao, id: Int): Tank? {
        var tank: Tank? = null
        GlobalScope.launch {
            tank = db.findById(id)
        }
        Thread.sleep(1000)
        return tank
    }

    fun populateFields(view: View, tank: Tank?) {
        if (tank != null) {
            view.findViewById<EditText>(R.id.form_name).setText(tank.name)
            view.findViewById<EditText>(R.id.form_country).setText(tank.nation)
            view.findViewById<EditText>(R.id.form_year).setText(tank.year.toString())
            view.findViewById<EditText>(R.id.form_crew).setText(tank.crew.toString())
            view.findViewById<EditText>(R.id.form_gun).setText(tank.gun)
            view.findViewById<EditText>(R.id.form_engine).setText(tank.engine)
            view.findViewById<EditText>(R.id.form_armour_front).setText(tank.frontArmour.toString())
            view.findViewById<EditText>(R.id.form_armour_side).setText(tank.sideArmour.toString())
            view.findViewById<EditText>(R.id.form_armour_rear).setText(tank.rearArmour.toString())
            view.findViewById<EditText>(R.id.form_image_url).setText(tank.image)
            view.findViewById<EditText>(R.id.form_description).setText(tank.description)
            setSpinnerItem(view.findViewById(R.id.types_spinner), tank.type)
        }
    }

    private fun setSpinnerItem(spinner: Spinner, type: String?) {
        if(type != null) {
            when(type) {
                "Light Tank" -> spinner.setSelection(0)
                "Medium Tank" -> spinner.setSelection(1)
                "Heavy Tank" -> spinner.setSelection(2)
                "Tank Destroyer" -> spinner.setSelection(3)
                "SP Artillery" -> spinner.setSelection(4)
                "MBT" -> spinner.setSelection(5)
                "IFV" -> spinner.setSelection(6)
            }
        }
    }

    fun getTextFields(view: View): List<String> {
        val textFields = mutableListOf<String>()
        textFields.add(view.findViewById<EditText>(R.id.form_name).text.toString())
        textFields.add(view.findViewById<EditText>(R.id.form_country).text.toString())
        textFields.add(view.findViewById<EditText>(R.id.form_gun).text.toString())
        textFields.add(view.findViewById<EditText>(R.id.form_engine).text.toString())
        textFields.add(view.findViewById<EditText>(R.id.form_image_url).text.toString())
        textFields.add(view.findViewById<EditText>(R.id.form_description).text.toString())
        textFields.add(view.findViewById<Spinner>(R.id.types_spinner).selectedItem.toString())
        return textFields
    }

    fun getNumericFields(view: View): List<EditText> {
        val numericFields = mutableListOf<EditText>()
        numericFields.add(view.findViewById(R.id.form_year))
        numericFields.add(view.findViewById(R.id.form_crew))
        numericFields.add(view.findViewById(R.id.form_armour_front))
        numericFields.add(view.findViewById(R.id.form_armour_side))
        numericFields.add(view.findViewById(R.id.form_armour_rear))
        return numericFields
    }

    fun createTank(view: View, db: TankDao) {
        val name = view.findViewById<EditText>(R.id.form_name).text.toString()
        val country = view.findViewById<EditText>(R.id.form_country).text.toString()
        val type = view.findViewById<Spinner>(R.id.types_spinner).selectedItem.toString()
        val year = view.findViewById<EditText>(R.id.form_year).text.toString().toInt()
        val crew = view.findViewById<EditText>(R.id.form_crew).text.toString().toInt()
        val gun = view.findViewById<EditText>(R.id.form_gun).text.toString()
        val engine = view.findViewById<EditText>(R.id.form_engine).text.toString()
        val frontArmour = view.findViewById<EditText>(R.id.form_armour_front).text.toString().toInt()
        val sideArmour = view.findViewById<EditText>(R.id.form_armour_side).text.toString().toInt()
        val rearArmour = view.findViewById<EditText>(R.id.form_armour_rear).text.toString().toInt()
        val image = view.findViewById<EditText>(R.id.form_image_url).text.toString()
        val description = view.findViewById<EditText>(R.id.form_description).text.toString()

        val tank = Tank(name, country, type, year, gun, engine, frontArmour, sideArmour, rearArmour, crew, image, description)
        GlobalScope.launch {
            db.insert(tank)
        }
        Thread.sleep(1000)
    }

}