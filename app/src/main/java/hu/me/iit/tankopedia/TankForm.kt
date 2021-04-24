package hu.me.iit.tankopedia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs


class TankForm : Fragment() {

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

        val modify = args.modify

        populateTypesList(view)
        populateButtons(view, modify)
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

    private fun populateButtons(view: View, modify: Boolean) {
        if(modify) {
            view.findViewById<Button>(R.id.form_ok_button).setOnClickListener {

            }
            view.findViewById<Button>(R.id.form_cancel_button).setOnClickListener {
                val action = TankFormDirections.actionTankFormToTankList()
                findNavController().navigate(action)
            }
        }
        else {
            view.findViewById<Button>(R.id.form_ok_button).setOnClickListener {

            }
            view.findViewById<Button>(R.id.form_cancel_button).setOnClickListener {
                val action = TankFormDirections.actionTankFormToFirstFragment()
                findNavController().navigate(action)
            }
        }
    }

}