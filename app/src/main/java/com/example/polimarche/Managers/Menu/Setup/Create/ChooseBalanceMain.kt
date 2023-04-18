package com.example.polimarche.Managers.Menu.Setup.Create

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.databinding.ActivityManagersChooseBalanceCreateSetupBinding
import com.example.polimarche.Data.DataBalance
import com.example.polimarche.Data.DataBalanceCode
import com.example.polimarche.Data.DataDampers
import com.example.polimarche.Managers.M_Adapters.BalanceAdapter
import com.example.polimarche.Managers.M_Adapters.BalanceCodeAdapter
import com.example.polimarche.Managers.Menu.Setup.Create.ChoosingBalance.FirstBalanceFragment

/*
Class used for the selection of the dampers used in a new setup
 */
class ChooseBalanceMain: AppCompatActivity(), BalanceCodeAdapter.OnBalanceCodeClickListener {

    private lateinit var binding : ActivityManagersChooseBalanceCreateSetupBinding

    private lateinit var searchView: SearchView

    private var balanceCodeList: MutableList<DataBalanceCode> = insertCode()

    private lateinit var adapterBalanceCode: BalanceCodeAdapter
    private lateinit var recyclerViewBalanceCode: RecyclerView

    private var frontBalance: MutableList<DataBalance> = initializeFrontBalance()
    private var backBalance: MutableList<DataBalance> = initializeBackBalance()

    private lateinit var adapterFrontBalance: BalanceAdapter
    private lateinit var adapterBackBalance: BalanceAdapter

    private lateinit var recyclerViewFrontBalance: RecyclerView
    private lateinit var recyclerViewBackBalance: RecyclerView





    override fun onBackPressed(){
        moveTaskToBack(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagersChooseBalanceCreateSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButtonChooseBalance.setOnClickListener {
            finish()
        }

        searchView = binding.searchViewBalanceCode

        /*
        Initialize the adapter and the recycler view that allows the user
        to search for every balance code stocked inside the storage.
         */
        adapterBalanceCode = BalanceCodeAdapter(balanceCodeList, this)
        recyclerViewBalanceCode = binding.listBalanceCode
        val layoutManagerCodificationDampers = LinearLayoutManager(this)
        recyclerViewBalanceCode.layoutManager = layoutManagerCodificationDampers
        recyclerViewBalanceCode.adapter = adapterBalanceCode
        recyclerViewBalanceCode.visibility = View.GONE

        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the front end balance parameters stocked inside the storage.
         */
        adapterFrontBalance = BalanceAdapter(frontBalance)
        recyclerViewFrontBalance= binding.listFrontEndBalance
        val layoutManagerFrontDampers = LinearLayoutManager(this)
        recyclerViewFrontBalance.layoutManager = layoutManagerFrontDampers
        recyclerViewFrontBalance.adapter = adapterFrontBalance

        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the back end dampers parameters stocked inside the storage.
         */
        adapterBackBalance = BalanceAdapter(backBalance)
        recyclerViewBackBalance = binding.listBackEndBalance
        val layoutManagerBackDampers = LinearLayoutManager(this)
        recyclerViewBackBalance.layoutManager = layoutManagerBackDampers
        recyclerViewBackBalance.adapter = adapterBackBalance

        /*
        Change the behaviour of the different part of the layout while
        searching inside the searchView for a particular codification.
         */
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                recyclerViewFrontBalance.visibility = View.VISIBLE
                recyclerViewBackBalance.visibility = View.VISIBLE
                searchView.queryHint = query
                searchView.clearFocus()

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                recyclerViewBalanceCode.visibility = View.VISIBLE
                searchView.queryHint = newText
                filterList(newText)

                return true
            }
        })

        /*
        Initialize the fragment to be seen inside the second frame at the
        bottom of the layout containing and letting the user to decide whether
        to add a new set of parameters for the balance or to use an existing one.
         */
        val firstDamper = FirstBalanceFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(binding.layoutChooseBalance.id, firstDamper).commit()
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
            recyclerViewBalanceCode.visibility = if (hasFocus) View.VISIBLE else View.GONE

            binding.frontEndBalanceCode.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewFrontBalance.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            binding.backEndBalanceCode.visibility = if (!hasFocus) View.VISIBLE else View.GONE
            recyclerViewBackBalance.visibility = if (!hasFocus) View.VISIBLE else View.GONE

        }

    }

    /*
    Create another list that stores the different dampers codification that matches
    the input given to the search view.
     */
    private fun filterList(query: String?) {
        if(query != null){
            val filteredList = balanceCodeList.filter { query in it.code.toString() }
            adapterBalanceCode.setFilteredList(filteredList.toMutableList())
        }
    }

    /*
    Change the behaviour of the layout on an item click of the recycler view
    containing every balance code.
     */
    override fun onCodificationClick(code: Int) {
        searchView.queryHint = "Code : $code"
        recyclerViewBalanceCode.visibility = View.GONE
        searchView.clearFocus()

        binding.frontEndBalanceCode.text = "Front Right : $code"
        binding.backEndBalanceCode.text = "Front left : $code"

        frontBalance = listBalance().filter { it.end == "Front" && it.code == code }.toMutableList()
        backBalance = listBalance().filter { it.end == "Back" && it.code == code }.toMutableList()

        adapterFrontBalance.setFilteredList(frontBalance)
        adapterBackBalance.setFilteredList(backBalance)
    }

    private fun insertCode(): MutableList<DataBalanceCode> {
        return mutableListOf(
            DataBalanceCode(1),
            DataBalanceCode(2),
            DataBalanceCode(3),
            DataBalanceCode(4),
            DataBalanceCode(5),
            DataBalanceCode(6),
            DataBalanceCode(7),
            DataBalanceCode(8),
            DataBalanceCode(9),
            DataBalanceCode(10),
            DataBalanceCode(11)
        )
    }


    private fun listBalance(): MutableList<DataBalance>{
        return mutableListOf(
            DataBalance(1, "Front", 44.0, 56.0),
            DataBalance(2, "Front", 44.0, 56.0),
            DataBalance(3, "Front", 44.0, 56.0),
            DataBalance(4, "Front", 44.0, 56.0),
            DataBalance(5, "Front", 44.0, 56.0),
            DataBalance(6, "Back", 44.0, 56.0),
            DataBalance(7, "Back", 44.0, 56.0),
            DataBalance(8, "Back", 44.0, 56.0),
            DataBalance(9, "Back", 44.0, 56.0),
            DataBalance(10, "Back", 44.0, 56.0)
        )
    }

    private fun initializeFrontBalance(): MutableList<DataBalance>{
        return listBalance().filter { it.end == "Front" }.toMutableList()
    }
    private fun initializeBackBalance(): MutableList<DataBalance>{
        return listBalance().filter { it.end == "Back" }.toMutableList()
    }


}