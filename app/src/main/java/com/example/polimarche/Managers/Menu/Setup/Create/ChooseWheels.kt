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
import com.example.polimarche.General_Adapters.DataWheelsCodification
import com.example.polimarche.General_Adapters.WheelsCodificationAdapter

/*
Class used for the selection of the wheels used in a new setup
 */
class ChooseWheels: AppCompatActivity(), WheelsCodificationAdapter.OnCodificationClickListener {

    private lateinit var binding : ActivityChooseWheelsCreateSetupBinding
    private var wheelsCodificationlist = insertCodification()
    private lateinit var searchView: SearchView
    private lateinit var adapterWheelsCodification: WheelsCodificationAdapter
    private lateinit var recyclerViewWheelsCodification: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseWheelsCreateSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

        searchView = binding.searchViewWheelsCodification
        adapterWheelsCodification = WheelsCodificationAdapter(wheelsCodificationlist, this)
        recyclerViewWheelsCodification = binding.listWheelsCodification

        val layoutManager = LinearLayoutManager(this)
        recyclerViewWheelsCodification.layoutManager = layoutManager
        recyclerViewWheelsCodification.adapter = adapterWheelsCodification


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

    /*
        This method is used to set the status bar
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