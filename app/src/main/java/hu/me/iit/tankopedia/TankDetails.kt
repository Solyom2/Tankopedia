package hu.me.iit.tankopedia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import hu.me.iit.tankopedia.dao.TankDatabase
import hu.me.iit.tankopedia.model.Tank
import hu.me.iit.tankopedia.service.TankDetailsService

class TankDetails: Fragment() {

    private val tankDetailsService = TankDetailsService()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tank_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = TankDatabase.getInstance(super.requireContext()).tankDao()
        val args: TankDetailsArgs by navArgs()
        val tank = tankDetailsService.queryDatabase(db, args.tank)

        if (tank != null) {
            populateDetails(view, tank)
        }

    }

    private fun populateDetails(view: View, tank: Tank) {
        view.findViewById<TextView>(R.id.name_textview).text = tank.name
        view.findViewById<TextView>(R.id.country_textview).text = getString(R.string.details_country, tank.nation)
        view.findViewById<TextView>(R.id.year_textview).text = getString(R.string.details_year, tank.year)
        view.findViewById<TextView>(R.id.type_textview).text = getString(R.string.details_type, tank.type)
        view.findViewById<TextView>(R.id.gun_textview).text = getString(R.string.details_gun, tank.gun)
        view.findViewById<TextView>(R.id.engine_textview).text = getString(R.string.details_engine, tank.engine)
        view.findViewById<TextView>(R.id.crew_textview).text = getString(R.string.details_crew, tank.crew)
        view.findViewById<TextView>(R.id.front_armour_textview).text = getString(R.string.details_front_armour, tank.frontArmour)
        view.findViewById<TextView>(R.id.side_armour_textview).text = getString(R.string.details_side_armour, tank.sideArmour)
        view.findViewById<TextView>(R.id.rear_armour_textview).text = getString(R.string.details_rear_armour, tank.rearArmour)
        view.findViewById<TextView>(R.id.description_textview).text = tank.description
        Picasso.get().load(tank.image).into(view.findViewById<ImageView>(R.id.picture_imageview))
    }

}