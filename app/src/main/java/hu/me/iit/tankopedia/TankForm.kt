package hu.me.iit.tankopedia

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.me.iit.tankopedia.dao.TankDao
import hu.me.iit.tankopedia.dao.TankDatabase
import hu.me.iit.tankopedia.model.Tank
import hu.me.iit.tankopedia.service.TankFormService
import kotlin.math.tan


class TankForm : Fragment() {

    private val tankFormService = TankFormService()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tank_form, container, false)
    }

    val args: TankFormArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = TankDatabase.getInstance(super.requireContext()).tankDao()
        var tank: Tank? = null

        populateTypesList(view)
        populateButtons(view, db)

        if(args.modify) {
            tank = tankFormService.queryDatabase(db, args.tank)
            tankFormService.populateFields(view, tank)
        }

    }



    private fun populateTypesList(view: View) {
        val spinner: Spinner = view.findViewById(R.id.types_spinner)
        ArrayAdapter.createFromResource(
                super.requireContext(),
                R.array.types_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun populateButtons(view: View, db: TankDao) {
        if(args.modify) {
            view.findViewById<Button>(R.id.form_ok_button).setOnClickListener {

            }
            view.findViewById<Button>(R.id.form_cancel_button).setOnClickListener {
                val action = TankFormDirections.actionTankFormToTankList()
                findNavController().navigate(action)
            }
        }
        else {
            view.findViewById<Button>(R.id.form_ok_button).setOnClickListener {
                addTank(view, db)
                val action = TankFormDirections.actionTankFormToFirstFragment()
                findNavController().navigate(action)
            }
            view.findViewById<Button>(R.id.form_cancel_button).setOnClickListener {
                val action = TankFormDirections.actionTankFormToFirstFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun addTank(view: View, db: TankDao) {
        val textFields = tankFormService.getTextFields(view)
        val numericFields = tankFormService.getNumericFields(view)

        if(!tankFormService.validateTextInput(textFields)) {
            val toast = Toast.makeText(super.requireContext(), "None of the text fields can be empty", Toast.LENGTH_SHORT)
            toast.show()
            return
        }
        if(!tankFormService.validateNumericInput(numericFields)) {
            val toast = Toast.makeText(super.requireContext(), "None of the numeric fields can be empty or less than zero", Toast.LENGTH_SHORT)
            toast.show()
            return
        }

        tankFormService.createTank(view, db)
    }

}