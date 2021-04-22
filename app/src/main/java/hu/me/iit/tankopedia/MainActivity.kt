package hu.me.iit.tankopedia

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.room.Room
import hu.me.iit.tankopedia.dao.TankDatabase
import hu.me.iit.tankopedia.model.Tank
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    /*val datas = listOf(
            Tank("T-34-76", "Soviet Union", "Medium Tank", 1940, "76.2 mm F-34 tank gun", "Model V-2-34",
                    47, 40, 45, 4, "https://www.super-hobby.hu/zdjecia/7/7/0/28383_rd.jpg", "The T-34 is a Soviet medium tank introduced in 1940, famously deployed with the Red Army during World War II against Operation Barbarossa. "),
            Tank("Tiger I", "Germany", "Heavy Tank", 1942, "8.8 cm KwK 36 L/56", "Maybach HL230 P45 V-12",
                    100, 80, 80, 5, "https://upload.wikimedia.org/wikipedia/commons/b/ba/Bundesarchiv_Bild_101I-299-1805-16%2C_Nordfrankreich%2C_Panzer_VI_%28Tiger_I%29.2.jpg", "German heavy tank of World War II, operated from 1942 in Africa and in the Soviet Union, usually in independent heavy tank battalions."),
            Tank("M3 Stuart", "USA", "Light Tank", 1941, "37 mm Gun M6", "Twin Cadillac Series 42",
                    51, 13, 13, 4, "https://tanks-encyclopedia.com/wp-content/uploads/2014/11/M5_stuart-US_Europe1944.jpg", "The M3 Stuart, officially Light Tank, M3, was an American light tank of World War II.")
            )*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val db = TankDatabase.getInstance(this).tankDao()
        var tanks: List<Tank>? = null
        GlobalScope.launch {
            tanks = db.getAll()
            Log.d("INSCOPE", tanks.toString())
        }
        Log.d("MAIN", tanks.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}