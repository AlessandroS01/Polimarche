package com.example.polimarche.Managers.Menu.Setup.Create

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.databinding.ActivityManagersChooseDampersCreateSetupBinding
import com.example.polimarche.Managers.M_Adapters.DampersAdapter
import com.example.polimarche.Managers.M_Adapters.DampersCodificationAdapter
import com.example.polimarche.Data.DataDampers
import com.example.polimarche.Data.DataDampersCodification
import com.example.polimarche.Managers.Menu.Setup.Create.ChoosingDampers.FirstDamperFragment

/*
Class used for the selection of the dampers used in a new setup
 */
class ChooseDampersMain: AppCompatActivity(), DampersCodificationAdapter.OnDampersCodificationClickListener {

    private lateinit var binding : ActivityManagersChooseDampersCreateSetupBinding

    private lateinit var searchView: SearchView

    private var dampersCodificationList: MutableList<DataDampersCodification> = insertCodification()

    private lateinit var adapterDampersCodification: DampersCodificationAdapter
    private lateinit var recyclerViewDampersCodification: RecyclerView

    private var frontDampers: MutableList<DataDampers> = initializeFrontDampers()
    private var backDampers: MutableList<DataDampers> = initializeBackDampers()

    private lateinit var adapterFrontDampers: DampersAdapter
    private lateinit var adapterBackDampers: DampersAdapter

    private lateinit var recyclerViewFrontDampers: RecyclerView
    private lateinit var recyclerViewBackDampers: RecyclerView

    override fun onBackPressed(){
        moveTaskToBack(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagersChooseDampersCreateSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButtonChooseDampers.setOnClickListener {
            finish()
        }

        searchView = binding.searchViewDampersCode

        /*
        Initialize the adapter and the recycler view that allows the user
        to search for every damper codification stocked inside the storage.
         */
        adapterDampersCodification = DampersCodificationAdapter(dampersCodificationList, this)
        recyclerViewDampersCodification = binding.listDampersCode
        val layoutManagerCodificationDampers = LinearLayoutManager(this)
        recyclerViewDampersCodification.layoutManager = layoutManagerCodificationDampers
        recyclerViewDampersCodification.adapter = adapterDampersCodification
        recyclerViewDampersCodification.visibility = View.GONE

        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the front end dampers stocked inside the storage.
         */
        adapterFrontDampers = DampersAdapter(frontDampers)
        recyclerViewFrontDampers = binding.listFrontEndDampers
        val layoutManagerFrontDampers = LinearLayoutManager(this)
        recyclerViewFrontDampers.layoutManager = layoutManagerFrontDampers
        recyclerViewFrontDampers.adapter = adapterFrontDampers

        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the back end dampers stocked inside the storage.
         */
        adapterBackDampers = DampersAdapter(backDampers)
        recyclerViewBackDampers = binding.listBackEndDampers
        val layoutManagerBackDampers = LinearLayoutManager(this)
        recyclerViewBackDampers.layoutManager = layoutManagerBackDampers
        recyclerViewBackDampers.adapter = adapterBackDampers

        /*
        Change the behaviour of the different part of the layout while
        searching inside the searchView for a particular codification.
         */
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                recyclerViewFrontDampers.visibility = View.VISIBLE
                recyclerViewBackDampers.visibility = View.VISIBLE
                searchView.queryHint = query
                searchView.clearFocus()

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                recyclerViewDampersCodification.visibility = View.VISIBLE
                searchView.queryHint = newText
                filterList(newText)

                return true
            }
        })
        
        /*
        Initialize the fragment to be seen inside the second frame at the
        bottom of the layout containing and letting the user to decide whether
        to add a new set of parameters for the dampers or to use an existing one.
         */
        val firstDamper = FirstDamperFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.layoutChooseDampers.id, firstDamper).commit()
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
            recyclerViewDampersCodification.visibility = if (hasFocus) View.VISIBLE else View.GONE

            binding.frontEndDampersCodification.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewFrontDampers.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            binding.backEndDampersCodification.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewBackDampers.visibility = if (!hasFocus) View.VISIBLE else View.GONE

        }
    }

    /*
    Create another list that stores the different dampers codification that matches
    the input given to the search view.
     */
    private fun filterList(query: String?) {
        if(query != null){
            val filteredList = dampersCodificationList.filter { query in it.codification.toString() }
            adapterDampersCodification.setFilteredList(filteredList.toMutableList())
        }
    }

    /*
    Change the behaviour of the layout on an item click of the recycler view
    containing every wheel codifications.
     */
    override fun onCodificationClick(codification: Int) {
        searchView.queryHint = "Codification : $codification"
        recyclerViewDampersCodification.visibility = View.GONE
        searchView.clearFocus()

        binding.frontEndDampersCodification.text = "Front end : $codification"
        binding.backEndDampersCodification.text = "Back end : $codification"

        frontDampers = listDampers().filter { it.end == "Front" && it.code == codification }.toMutableList()
        backDampers = listDampers().filter { it.end == "End" && it.code == codification }.toMutableList()

        adapterFrontDampers.setFilteredList(frontDampers)
        adapterBackDampers.setFilteredList(backDampers)
    }


    private fun listDampers(): MutableList<DataDampers>{
        return mutableListOf(
            DataDampers(1, "Front", 1.3, 1.2, 4.1, 1.7),
            DataDampers(2, "Front", 1.3, 1.2, 4.1, 1.7),
            DataDampers(3, "Front", 1.3, 1.2, 4.1, 1.7),
            DataDampers(4, "Front", 1.3, 1.2, 4.1, 1.7),
            DataDampers(5, "Front", 1.3, 1.2, 4.1, 1.7),
            DataDampers(6, "Front", 1.3, 1.2, 4.1, 1.7),
            DataDampers(7, "End", 1.3, 1.2, 4.1, 1.7),
            DataDampers(8, "End", 1.3, 1.2, 4.1, 1.7),
            DataDampers(9, "End", 1.3, 1.2, 4.1, 1.7),
            DataDampers(10, "End", 1.3, 1.2, 4.1, 1.7),
            DataDampers(11, "End", 1.3, 1.2, 4.1, 1.7),
        )
    }

    private fun initializeFrontDampers(): MutableList<DataDampers>{
        return listDampers().filter { it.end == "Front" }.toMutableList()
    }
    private fun initializeBackDampers(): MutableList<DataDampers>{
        return listDampers().filter { it.end == "End" }.toMutableList()
    }


    private fun insertCodification(): MutableList<DataDampersCodification> {
        return mutableListOf(
            DataDampersCodification(1),
            DataDampersCodification(2),
            DataDampersCodification(3),
            DataDampersCodification(4),
            DataDampersCodification(5),
            DataDampersCodification(6),
            DataDampersCodification(7),
            DataDampersCodification(8),
            DataDampersCodification(9),
            DataDampersCodification(10),
            DataDampersCodification(11)
        )
    }



}