package com.example.polimarche.users.managers.menu.setup.create.choosing_wheels

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.databinding.ActivityManagersChooseWheelsCreateSetupBinding
import com.example.polimarche.data_container.wheel.DataWheel


/*
Class used for the selection of the wheels used in a new setup
 */
class ChooseWheelsMain: AppCompatActivity(), WheelsCodificationAdapter.OnWheelsCodificationClickListener {

    override fun onBackPressed(){
        moveTaskToBack(false)
    }

    private lateinit var binding : ActivityManagersChooseWheelsCreateSetupBinding

    private lateinit var searchView: SearchView

    /*
    Search inside the different wheels object the different codifications
    and then the wheel's quantity that has a particular codifation.
     */
    private var codificationWheelList: MutableList<String> = listCodification()
    private var codificationWheelQuantity: MutableList<Int> = listQuantityCodification(codificationWheelList)
    private lateinit var adapterWheelsCodification: WheelsCodificationAdapter
    private lateinit var recyclerViewWheelsCodification: RecyclerView

    private var frontRightWheels: MutableList<DataWheel> = initializeFrontRight()
    private var frontLeftWheels: MutableList<DataWheel> = initializeFrontLeft()
    private var rearRightWheels: MutableList<DataWheel> = initializeRearRight()
    private var rearLeftWheels: MutableList<DataWheel> = initializeRearLeft()

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

        binding.backButtonChooseWheels.setOnClickListener {
            finish()
        }

        searchView = binding.searchViewWheelsCodification
        /*
        Initialize the adapter and the recycler view that allows the user
        to search for every wheel codification stocked inside the storage.
         */
        adapterWheelsCodification = WheelsCodificationAdapter(codificationWheelList, codificationWheelQuantity, this)
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
        It changes the visibility and weight of the MainFrame when the search view change focus type
        by letting the other frame layout view to gone when it has focus.
        Then it sets the other recycler view and text view ( the ones that are used to show
        the wheels basing on the position and, sometimes, the codification ) hidden when the search
        view is focused and visible when it's not.
         */
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            binding.layoutContainer.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            if ( hasFocus) {
                val params = binding.frameMain.layoutParams as LinearLayout.LayoutParams
                params.weight = 2.0f
                params.bottomMargin = 20
                binding.frameMain.layoutParams = params
            }
            else {
                val params = binding.frameMain.layoutParams as LinearLayout.LayoutParams
                params.weight = 1.0f
                binding.frameMain.layoutParams = params
            }
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

        /*
        Initialize the fragment to be seen inside the second frame at the
        bottom of the layout containing and letting the user to decide whether
        to add a new set of parameters for the wheels or to use an existing one.
         */
        val firstWheel = FirstWheelFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.layoutChooseWheels.id, firstWheel).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }

    }

    /*
    Change the behaviour of the layout on an item click of the recycler view
    containing every wheel codifications.
     */
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
    Create another list that stores the different wheel's codification that matches
    the input given to the search view.
     */
    private fun filterList(query: String?) {
        if(query != null){
            val filteredList = codificationWheelList.filter { query in it }
            adapterWheelsCodification.setFilteredList(filteredList.toMutableList())
        }
    }

    /*
    Return a list of all the different wheel's codification.
     */
    private fun listCodification(): MutableList<String>{
        val list: MutableList<String> = emptyList<String>().toMutableList()
        for ( element in listWheels()){
            if ( ! list.contains(element.codification) )
                list.add(element.codification)
        }
        return list
    }

    /*
    Return a list of int that in position j contains the number of wheels
    that has the codification contained inside "codificationList" in position
    j.
     */
    private fun listQuantityCodification(codificationList: MutableList<String>): MutableList<Int>{
        val list: MutableList<Int> = emptyList<Int>().toMutableList()
        for ( string in codificationList){
            list.add( listWheels().filter { it.codification == string }.size)
        }
        return list
    }

    private fun listWheels(): MutableList<DataWheel>{
        return mutableListOf(
            DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
            DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
            DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
            DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
            DataWheel(5, "Front right", "B", "1", "-1+2piastrini", "1"),
            DataWheel(6, "Front left", "B", "1", "-1+2piastrini", "1"),
            DataWheel(7, "Rear right", "B", "1", "-1+2piastrini", "1"),
            DataWheel(8, "Rear left", "B", "1", "-1+2piastrini", "1"),
            DataWheel(9, "Front right", "C", "1", "-1+2piastrini", "1"),
            DataWheel(10, "Front left", "C", "1", "-1+2piastrini", "1"),
            DataWheel(11, "Rear right", "C", "1", "-1+2piastrini", "1"),
            DataWheel(12, "Rear left", "C", "1", "-1+2piastrini", "1"),
            DataWheel(13, "Front right", "D", "1", "-1+2piastrini", "1"),
            DataWheel(14, "Front left", "D", "1", "-1+2piastrini", "1"),
            DataWheel(15, "Rear right", "D", "1", "-1+2piastrini", "1"),
            DataWheel(16, "Rear left", "D", "1", "-1+2piastrini", "1"),
        )
    }
    private fun initializeFrontRight(): MutableList<DataWheel>{
        return listWheels().filter { it.position == "Front right" }.toMutableList()
    }
    private fun initializeFrontLeft(): MutableList<DataWheel>{
        return listWheels().filter { it.position == "Front left" }.toMutableList()
    }
    private fun initializeRearRight(): MutableList<DataWheel>{
        return listWheels().filter { it.position == "Rear right" }.toMutableList()
    }
    private fun initializeRearLeft(): MutableList<DataWheel>{
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


}