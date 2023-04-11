package com.example.polimarche.Managers.Menu.Setup.Create

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.databinding.ActivityChooseWheelsCreateSetupBinding
import com.example.polimarche.General_Adapters.DataWheels
import com.example.polimarche.General_Adapters.DataWheelsCodification
import com.example.polimarche.General_Adapters.WheelsAdapter
import com.example.polimarche.General_Adapters.WheelsCodificationAdapter

/*
Class used for the selection of the wheels used in a new setup
 */
class ChooseWheels: AppCompatActivity(), WheelsCodificationAdapter.OnCodificationClickListener {

    private lateinit var binding : ActivityChooseWheelsCreateSetupBinding
    private var wheelsCodificationlist: MutableList<DataWheelsCodification> = insertCodification()
    private var frontRightWheel: MutableList<DataWheels> = insertFrontRightWheels()
    private var frontLeftWheel: MutableList<DataWheels> = insertFrontLeftWheels()
    private var rearRightWheel: MutableList<DataWheels> = insertRearRightWheels()
    private var rearLeftWheel: MutableList<DataWheels> = insertRearLeftWheels()
    private lateinit var searchView: SearchView
    private lateinit var adapterWheelsCodification: WheelsCodificationAdapter
    private lateinit var adapterFrontRightWheel: WheelsAdapter
    private lateinit var adapterFrontLeftWheel: WheelsAdapter
    private lateinit var adapterRearRightWheel: WheelsAdapter
    private lateinit var adapterRearLeftWheel: WheelsAdapter
    private lateinit var recyclerViewWheelsCodification: RecyclerView
    private lateinit var recyclerViewFrontRightWheel: RecyclerView
    private lateinit var recyclerViewFrontLeftWheel: RecyclerView
    private lateinit var recyclerViewRearRightWheel: RecyclerView
    private lateinit var recyclerViewRearLeftWheel: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseWheelsCreateSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        val layoutManagerCodificationWheel = LinearLayoutManager(this)
        val layoutManagerFrontRightWheel = LinearLayoutManager(this)
        val layoutManagerFrontLeftWheel = LinearLayoutManager(this)
        val layoutManagerRearRightWheel = LinearLayoutManager(this)
        val layoutManagerRearLeftWheel = LinearLayoutManager(this)

        searchView = binding.searchViewWheelsCodification

        adapterWheelsCodification = WheelsCodificationAdapter(wheelsCodificationlist, this)
        recyclerViewWheelsCodification = binding.listWheelsCodification
        recyclerViewWheelsCodification.layoutManager = layoutManagerCodificationWheel
        recyclerViewWheelsCodification.adapter = adapterWheelsCodification


        adapterFrontRightWheel = WheelsAdapter(frontRightWheel)
        recyclerViewFrontRightWheel = binding.listFrontRightWheel
        recyclerViewFrontRightWheel.layoutManager = layoutManagerFrontRightWheel
        recyclerViewFrontRightWheel.adapter = adapterFrontRightWheel

        adapterFrontLeftWheel = WheelsAdapter(frontLeftWheel)
        recyclerViewFrontLeftWheel = binding.listFrontLeftWheel
        recyclerViewFrontLeftWheel.layoutManager = layoutManagerFrontLeftWheel
        recyclerViewFrontLeftWheel.adapter = adapterFrontLeftWheel

        adapterRearRightWheel = WheelsAdapter(rearRightWheel)
        recyclerViewRearRightWheel = binding.listRearRightWheel
        recyclerViewRearRightWheel.layoutManager = layoutManagerRearRightWheel
        recyclerViewRearRightWheel.adapter = adapterRearRightWheel

        adapterRearLeftWheel = WheelsAdapter(rearLeftWheel)
        recyclerViewRearLeftWheel = binding.listRearLeftWheel
        recyclerViewRearLeftWheel.layoutManager = layoutManagerRearLeftWheel
        recyclerViewRearLeftWheel.adapter = adapterRearLeftWheel

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recyclerViewWheelsCodification.visibility = View.VISIBLE
                filterList(newText)
                return true
            }
        })
        /*
        Change the visibility of the recyclerView when the searchView is focused or not
         */
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            recyclerViewWheelsCodification.visibility = if (hasFocus) View.VISIBLE else View.GONE
        }
    }

    private fun filterList(query: String?) {
        if(query != null){
            val filteredList = wheelsCodificationlist.filter { query in it.codification }
            adapterWheelsCodification.setFilteredList(filteredList.toMutableList())
        }
    }

    private fun insertCodification(): MutableList<DataWheelsCodification>{
        return mutableListOf(
            DataWheelsCodification("A", 12),
            DataWheelsCodification("B", 16),
            DataWheelsCodification("C", 8),
            DataWheelsCodification("D", 4),
            DataWheelsCodification("E", 12),
            DataWheelsCodification("F", 20),
            DataWheelsCodification("G", 20),
            DataWheelsCodification("H", 20),
            DataWheelsCodification("L", 20)
        )
    }


    private fun listWheels(): MutableList<DataWheels>{
        return mutableListOf(
            DataWheels(1, "Front right", "A", "1", "-1+2piastrini", "1"),
            DataWheels(2, "Front Left", "A", "1", "-1+2piastrini", "1"),
            DataWheels(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
            DataWheels(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
            DataWheels(5, "Front right", "B", "1", "-1+2piastrini", "1"),
            DataWheels(6, "Front Left", "B", "1", "-1+2piastrini", "1"),
            DataWheels(7, "Rear right", "B", "1", "-1+2piastrini", "1"),
            DataWheels(8, "Rear left", "B", "1", "-1+2piastrini", "1"),
            DataWheels(9, "Front right", "C", "1", "-1+2piastrini", "1"),
            DataWheels(10, "Front Left", "C", "1", "-1+2piastrini", "1"),
            DataWheels(11, "Rear right", "C", "1", "-1+2piastrini", "1"),
            DataWheels(12, "Rear left", "C", "1", "-1+2piastrini", "1"),
            DataWheels(13, "Front right", "D", "1", "-1+2piastrini", "1"),
            DataWheels(14, "Front Left", "D", "1", "-1+2piastrini", "1"),
            DataWheels(15, "Rear right", "D", "1", "-1+2piastrini", "1"),
            DataWheels(16, "Rear left", "D", "1", "-1+2piastrini", "1"),
        )
    }

    private fun insertFrontRightWheels(): MutableList<DataWheels>{
        return listWheels().filter { it.position == "Front right" }.toMutableList()
    }
    private fun insertFrontLeftWheels(): MutableList<DataWheels>{
        return listWheels().filter { it.position == "Front left" }.toMutableList()
    }
    private fun insertRearRightWheels(): MutableList<DataWheels>{
        return listWheels().filter { it.position == "Rear right" }.toMutableList()
    }
    private fun insertRearLeftWheels(): MutableList<DataWheels>{
        return listWheels().filter { it.position == "Rear left" }.toMutableList()
    }

    /*
        This method is used to set the status
        completely transparent but keeping the icon at the top
        of the layout
     */
    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    override fun onCodificationClick(codification: String) {
        recyclerViewWheelsCodification.visibility = View.GONE
        searchView.clearFocus()
        binding.frontRightWheelCodification.text = "Front Right : $codification"
        binding.frontLeftWheelCodification.text = "Front left : $codification"
        binding.rearRightWheelCodification.text = "Rear Right : $codification"
        binding.rearLeftWheelCodification.text = "Rear left : $codification"
    }
}