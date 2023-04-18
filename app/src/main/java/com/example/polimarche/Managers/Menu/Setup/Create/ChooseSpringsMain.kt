package com.example.polimarche.Managers.Menu.Setup.Create

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.databinding.ActivityManagersChooseSpringsCreateSetupBinding
import com.example.polimarche.Data.DataSprings
import com.example.polimarche.Data.DataSpringsCodification
import com.example.polimarche.Managers.M_Adapters.SpringsAdapter
import com.example.polimarche.Managers.M_Adapters.SpringsCodificationAdapter
import com.example.polimarche.Managers.Menu.Setup.Create.ChoosingDampers.FirstSpringsFragment

/*
Class used for the selection of the springs used in a new setup
 */
class ChooseSpringsMain: AppCompatActivity(), SpringsCodificationAdapter.OnSpringsCodificationClickListener {

    private lateinit var binding : ActivityManagersChooseSpringsCreateSetupBinding

    private lateinit var searchView: SearchView

    private var springCodificationList: MutableList<DataSpringsCodification> = insertCodification()

    private lateinit var adapterSpringsCodification: SpringsCodificationAdapter
    private lateinit var recyclerViewSpringsCodification: RecyclerView

    private var frontSprings: MutableList<DataSprings> = initializeFrontSprings()
    private var backSprings: MutableList<DataSprings> = initializeBackSprings()

    private lateinit var adapterFrontSprings: SpringsAdapter
    private lateinit var adapterBackSprings: SpringsAdapter

    private lateinit var recyclerViewFrontSprings: RecyclerView
    private lateinit var recyclerViewBackSprings: RecyclerView

    override fun onBackPressed(){
        moveTaskToBack(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagersChooseSpringsCreateSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButtonChooseSprings.setOnClickListener {
            finish()
        }

        searchView = binding.searchViewSpringCodification

        /*
        Initialize the adapter and the recycler view that allows the user
        to search for every spring codification stocked inside the storage.
         */
        adapterSpringsCodification = SpringsCodificationAdapter(springCodificationList, this)
        recyclerViewSpringsCodification = binding.listSpringsCodification
        val layoutManagerCodificationSprings = LinearLayoutManager(this)
        recyclerViewSpringsCodification.layoutManager = layoutManagerCodificationSprings
        recyclerViewSpringsCodification.adapter = adapterSpringsCodification
        recyclerViewSpringsCodification.visibility = View.GONE

        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the front end springs stocked inside the storage.
         */
        adapterFrontSprings = SpringsAdapter(frontSprings)
        recyclerViewFrontSprings = binding.listFrontEndSprings
        val layoutManagerFrontDampers = LinearLayoutManager(this)
        recyclerViewFrontSprings.layoutManager = layoutManagerFrontDampers
        recyclerViewFrontSprings.adapter = adapterFrontSprings

        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the back end springs stocked inside the storage.
         */
        adapterBackSprings = SpringsAdapter(backSprings)
        recyclerViewBackSprings = binding.listBackEndSprings
        val layoutManagerBackDampers = LinearLayoutManager(this)
        recyclerViewBackSprings.layoutManager = layoutManagerBackDampers
        recyclerViewBackSprings.adapter = adapterBackSprings

        /*
        Change the behaviour of the different part of the layout while
        searching inside the searchView for a particular codification.
         */
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                recyclerViewFrontSprings.visibility = View.VISIBLE
                recyclerViewBackSprings.visibility = View.VISIBLE
                searchView.queryHint = query
                searchView.clearFocus()

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                recyclerViewSpringsCodification.visibility = View.VISIBLE
                searchView.queryHint = newText
                filterList(newText)

                return true
            }
        })
        
        /*
        Initialize the fragment to be seen inside the second frame at the
        bottom of the layout containing and letting the user to decide whether
        to add a new set of parameters for the springs or to use an existing one.
         */
        val firstSpring = FirstSpringsFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.layoutChooseSprings.id, firstSpring).commit()
            setReorderingAllowed(true)
            addToBackStack(null)
        }

        /*
        Change the visibility of the recyclerView when the searchView is focused or not.
        The recycler view is hidden if the search view is not focused and visible if focused.
        It changes the visibility and weight of the MainFrame when the search view change focus type
        by letting the other frame layout view to gone when it has focus.
        Then it sets the other recycler view and text view ( the ones that are used to show
        the dampers basing on the end and, sometimes, the codification ) hidden when the search
        view is focused and visible when it's not.
         */
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            binding.layoutContainer.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            if ( hasFocus) {
                val params = binding.frameMain.layoutParams as LinearLayout.LayoutParams
                params.weight = 2.0f
                binding.frameMain.layoutParams = params
            }
            else {
                val params = binding.frameMain.layoutParams as LinearLayout.LayoutParams
                params.weight = 1.0f
                binding.frameMain.layoutParams = params
            }
            recyclerViewSpringsCodification.visibility = if (hasFocus) View.VISIBLE else View.GONE

            binding.frontEndSpringsCodification.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewFrontSprings.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            binding.backEndSpringsCodification.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewBackSprings.visibility = if (!hasFocus) View.VISIBLE else View.GONE

        }
    }

    /*
    Create another list that stores the different dampers codification that matches
    the input given to the search view.
     */
    private fun filterList(query: String?) {
        if(query != null){
            val filteredList = springCodificationList.filter { query in it.codification }
            adapterSpringsCodification.setFilteredList(filteredList.toMutableList())
        }
    }

    /*
    Change the behaviour of the layout on an item click of the recycler view
    containing every wheel codifications.
     */
    override fun onCodificationClick(codification: String) {
        searchView.queryHint = "Codification : $codification"
        recyclerViewSpringsCodification.visibility = View.GONE
        searchView.clearFocus()

        binding.frontEndSpringsCodification.text = "Front Right : $codification"
        binding.backEndSpringsCodification.text = "Front left : $codification"

        frontSprings = listSprings().filter { it.end == "Front" && it.codification == codification }.toMutableList()
        backSprings = listSprings().filter { it.end == "End" && it.codification == codification }.toMutableList()

        adapterFrontSprings.setFilteredList(frontSprings)
        adapterBackSprings.setFilteredList(backSprings)
    }


    private fun listSprings(): MutableList<DataSprings>{
        return mutableListOf(
            DataSprings(1, "A", "Front", 1.21, "12.4", "Center"),
            DataSprings(2, "B", "Front", 1.21, "12.4", "Center"),
            DataSprings(3, "C", "Front", 1.21, "12.4", "Center"),
            DataSprings(4, "D", "Front", 1.21, "12.4", "Center"),
            DataSprings(5, "E", "Front", 1.21, "12.4", "Center"),
            DataSprings(6, "F", "End", 1.21, "12.4", "Center"),
            DataSprings(7, "G", "End", 1.21, "12.4", "Center"),
            DataSprings(8, "H", "End", 1.21, "12.4", "Center"),
            DataSprings(9, "I", "End", 1.21, "12.4", "Center"),
        )
    }

    private fun initializeFrontSprings(): MutableList<DataSprings> {
        return listSprings().filter { it.end == "Front" }.toMutableList()
    }
    private fun initializeBackSprings(): MutableList<DataSprings> {
        return listSprings().filter { it.end == "End" }.toMutableList()
    }


    private fun insertCodification(): MutableList<DataSpringsCodification> {
        return mutableListOf(
            DataSpringsCodification("A"),
            DataSpringsCodification("B"),
            DataSpringsCodification("C"),
            DataSpringsCodification("D"),
            DataSpringsCodification("E"),
            DataSpringsCodification("F"),
            DataSpringsCodification("G"),
            DataSpringsCodification("H"),
            DataSpringsCodification("I"),
            DataSpringsCodification("L"),
            DataSpringsCodification("M"),
        )
    }


}