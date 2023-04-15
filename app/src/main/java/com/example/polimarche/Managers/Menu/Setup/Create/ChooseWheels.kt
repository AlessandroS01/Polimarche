package com.example.polimarche.Managers.Menu.Setup.Create

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.databinding.ActivityManagersChooseWheelsCreateSetupBinding
import com.example.polimarche.General_Adapters.DataWheels
import com.example.polimarche.General_Adapters.DataWheelsCodification
import com.example.polimarche.General_Adapters.WheelsAdapter
import com.example.polimarche.General_Adapters.WheelsCodificationAdapter
import com.example.polimarche.Managers.Menu.Setup.Create.ChoosingWheels.FirstWheelFragment

/*
Class used for the selection of the wheels used in a new setup
 */
class ChooseWheels: AppCompatActivity(), WheelsCodificationAdapter.OnCodificationClickListener {

    override fun onBackPressed(){
        moveTaskToBack(false)
    }

    private lateinit var binding : ActivityManagersChooseWheelsCreateSetupBinding

    private var wheelsCodificationList: MutableList<DataWheelsCodification> = insertCodification()
    private lateinit var adapterWheelsCodification: WheelsCodificationAdapter
    private lateinit var recyclerViewWheelsCodification: RecyclerView

    private var frontRightWheels: MutableList<DataWheels> = initializeFrontRight()
    private var frontLeftWheels: MutableList<DataWheels> = initializeFrontLeft()
    private var rearRightWheels: MutableList<DataWheels> = initializeRearRight()
    private var rearLeftWheels: MutableList<DataWheels> = initializeRearLeft()

    private lateinit var searchView: SearchView

    private lateinit var adapterFrontRightWheel: WheelsAdapter
    private lateinit var adapterFrontLeftWheel: WheelsAdapter
    private lateinit var adapterRearRightWheel: WheelsAdapter
    private lateinit var adapterRearLeftWheel: WheelsAdapter

    private lateinit var recyclerViewFrontRightWheel: RecyclerView
    private lateinit var recyclerViewFrontLeftWheel: RecyclerView
    private lateinit var recyclerViewRearRightWheel: RecyclerView
    private lateinit var recyclerViewRearLeftWheel: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagersChooseWheelsCreateSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        searchView = binding.searchViewWheelsCodification
        /*
        Initialize the adapter and the recycler view that allows the user
        to search for every wheel codification stocked inside the storage.
         */
        adapterWheelsCodification = WheelsCodificationAdapter(wheelsCodificationList, this)
        recyclerViewWheelsCodification = binding.listWheelsCodification
        val layoutManagerCodificationWheel = LinearLayoutManager(this)
        recyclerViewWheelsCodification.layoutManager = layoutManagerCodificationWheel
        recyclerViewWheelsCodification.adapter = adapterWheelsCodification
        recyclerViewWheelsCodification.visibility = View.GONE
        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the front right wheels stocked inside the storage.
         */
        adapterFrontRightWheel = WheelsAdapter(frontRightWheels)
        recyclerViewFrontRightWheel = binding.listFrontRightWheel
        val layoutManagerFrontRightWheel = LinearLayoutManager(this)
        recyclerViewFrontRightWheel.layoutManager = layoutManagerFrontRightWheel
        recyclerViewFrontRightWheel.adapter = adapterFrontRightWheel
        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the front left wheels stocked inside the storage.
         */
        adapterFrontLeftWheel = WheelsAdapter(frontLeftWheels)
        recyclerViewFrontLeftWheel = binding.listFrontLeftWheel
        val layoutManagerFrontLeftWheel = LinearLayoutManager(this)
        recyclerViewFrontLeftWheel.layoutManager = layoutManagerFrontLeftWheel
        recyclerViewFrontLeftWheel.adapter = adapterFrontLeftWheel
        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the rear right wheels stocked inside the storage.
         */
        adapterRearRightWheel = WheelsAdapter(rearRightWheels)
        recyclerViewRearRightWheel = binding.listRearRightWheel
        val layoutManagerRearRightWheel = LinearLayoutManager(this)
        recyclerViewRearRightWheel.layoutManager = layoutManagerRearRightWheel
        recyclerViewRearRightWheel.adapter = adapterRearRightWheel
        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the rear left wheels stocked inside the storage.
         */
        adapterRearLeftWheel = WheelsAdapter(rearLeftWheels)
        recyclerViewRearLeftWheel = binding.listRearLeftWheel
        val layoutManagerRearLeftWheel = LinearLayoutManager(this)
        recyclerViewRearLeftWheel.layoutManager = layoutManagerRearLeftWheel
        recyclerViewRearLeftWheel.adapter = adapterRearLeftWheel

        /*
        Change the behaviour of the different part of the layout while
        searching inside the searchView for a particular codification.
         */
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                recyclerViewFrontRightWheel.visibility = View.VISIBLE
                recyclerViewFrontLeftWheel.visibility = View.VISIBLE
                recyclerViewRearRightWheel.visibility = View.VISIBLE
                recyclerViewRearLeftWheel.visibility = View.VISIBLE
                searchView.queryHint = query
                searchView.clearFocus()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                recyclerViewWheelsCodification.visibility = View.VISIBLE
                searchView.queryHint = newText
                filterList(newText)
                return true
            }
        })

        /*
        Change the visibility of the recyclerView when the searchView is focused or not.
        The recycler view is hidden if the search view is not focused and visible if focused.
        Then it sets the other recycler view and text view ( the ones that are used to show
        the wheels basing on the position and, sometimes, the codification ) hidden when the search
        view is focused and visible when it's not.
         */
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            recyclerViewWheelsCodification.visibility = if (hasFocus) View.VISIBLE else View.GONE
            binding.frontRightWheelCodification.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewFrontRightWheel.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            binding.frontLeftWheelCodification.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewFrontLeftWheel.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            binding.rearRightWheelCodification.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewRearRightWheel.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            binding.rearLeftWheelCodification.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewRearLeftWheel.visibility = if (!hasFocus) View.VISIBLE else View.GONE
        }

        val fragment = FirstWheelFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.layoutChooseWheels.id, fragment).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }

        binding.backButtonChooseWheels.setOnClickListener {
            finish()
        }
    }

    /*
    Create another list that stores the different wheel's codification that matches
    the input given to the search view.
     */
    private fun filterList(query: String?) {
        if(query != null){
            val filteredList = wheelsCodificationList.filter { query in it.codification }
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
            DataWheelsCodification("F", 20)
        )
    }

    private fun listWheels(): MutableList<DataWheels>{
        return mutableListOf(
            DataWheels(1, "Front right", "A", "1", "-1+2piastrini", "1"),
            DataWheels(2, "Front left", "A", "1", "-1+2piastrini", "1"),
            DataWheels(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
            DataWheels(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
            DataWheels(5, "Front right", "B", "1", "-1+2piastrini", "1"),
            DataWheels(6, "Front left", "B", "1", "-1+2piastrini", "1"),
            DataWheels(7, "Rear right", "B", "1", "-1+2piastrini", "1"),
            DataWheels(8, "Rear left", "B", "1", "-1+2piastrini", "1"),
            DataWheels(9, "Front right", "C", "1", "-1+2piastrini", "1"),
            DataWheels(10, "Front left", "C", "1", "-1+2piastrini", "1"),
            DataWheels(11, "Rear right", "C", "1", "-1+2piastrini", "1"),
            DataWheels(12, "Rear left", "C", "1", "-1+2piastrini", "1"),
            DataWheels(13, "Front right", "D", "1", "-1+2piastrini", "1"),
            DataWheels(14, "Front left", "D", "1", "-1+2piastrini", "1"),
            DataWheels(15, "Rear right", "D", "1", "-1+2piastrini", "1"),
            DataWheels(16, "Rear left", "D", "1", "-1+2piastrini", "1"),
        )
    }

    private fun initializeFrontRight(): MutableList<DataWheels>{
        return listWheels().filter { it.position == "Front right" }.toMutableList()
    }
    private fun initializeFrontLeft(): MutableList<DataWheels>{
        return listWheels().filter { it.position == "Front left" }.toMutableList()
    }
    private fun initializeRearRight(): MutableList<DataWheels>{
        return listWheels().filter { it.position == "Rear right" }.toMutableList()
    }
    private fun initializeRearLeft(): MutableList<DataWheels>{
        return listWheels().filter { it.position == "Rear left" }.toMutableList()
    }

    override fun onCodificationClick(codification: String) {

        searchView.queryHint = "Codification : $codification"
        recyclerViewWheelsCodification.visibility = View.GONE
        searchView.clearFocus()
        binding.frontRightWheelCodification.text = "Front Right : $codification"
        binding.frontLeftWheelCodification.text = "Front left : $codification"
        binding.rearRightWheelCodification.text = "Rear Right : $codification"
        binding.rearLeftWheelCodification.text = "Rear left : $codification"

        frontRightWheels = listWheels().filter { it.position == "Front right" && it.codification == codification }.toMutableList()
        frontLeftWheels = listWheels().filter { it.position == "Front left" && it.codification == codification }.toMutableList()
        rearRightWheels = listWheels().filter { it.position == "Rear right" && it.codification == codification }.toMutableList()
        rearLeftWheels = listWheels().filter { it.position == "Rear left" && it.codification == codification }.toMutableList()

        adapterFrontRightWheel.setFilteredList(frontRightWheels)
        adapterFrontLeftWheel.setFilteredList(frontLeftWheels)
        adapterRearRightWheel.setFilteredList(rearRightWheels)
        adapterRearLeftWheel.setFilteredList(rearLeftWheels)
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


}