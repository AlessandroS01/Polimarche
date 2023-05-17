package com.example.polimarche.users.managers.menu.setup.create.choosing_balance

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.databinding.ActivityManagersChooseBalanceCreateSetupBinding
import com.example.polimarche.data_container.balance.BalanceViewModel

/*
Class used for the selection of the dampers used in a new setup
 */
class ChooseBalanceMain: AppCompatActivity(), BalanceCodeAdapter.OnBalanceCodeClickListener {

    private lateinit var binding : ActivityManagersChooseBalanceCreateSetupBinding

    private val balanceViewModel: BalanceViewModel by viewModels()

    private lateinit var searchView: SearchView

    private lateinit var adapterBalanceCode: BalanceCodeAdapter
    private lateinit var recyclerViewBalanceCode: RecyclerView

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
            // if the parameters of the balance are stocked whenever
            // the user closes this fragment, they'll be erased.
            balanceViewModel.clearStockedParameters()
            finish()
        }

        searchView = binding.searchViewBalanceCode

        /*
        Initialize the adapter and the recycler view that allows the user
        to search for every balance code stocked inside the storage.
         */
        adapterBalanceCode = BalanceCodeAdapter(balanceViewModel, this)
        recyclerViewBalanceCode = binding.listBalanceCode
        val layoutManagerCodificationDampers = LinearLayoutManager(this)
        recyclerViewBalanceCode.layoutManager = layoutManagerCodificationDampers
        recyclerViewBalanceCode.adapter = adapterBalanceCode
        recyclerViewBalanceCode.visibility = View.GONE

        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the front end balance parameters stocked inside the storage.
         */
        adapterFrontBalance = BalanceAdapter("Front", balanceViewModel)
        recyclerViewFrontBalance= binding.listFrontEndBalance
        val layoutManagerFrontDampers = LinearLayoutManager(this)
        recyclerViewFrontBalance.layoutManager = layoutManagerFrontDampers
        recyclerViewFrontBalance.adapter = adapterFrontBalance

        /*
        Initialize the adapter and the recycler view that allows the user
        to see all the back end dampers parameters stocked inside the storage.
         */
        adapterBackBalance = BalanceAdapter("Back", balanceViewModel)
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
        val firstDamper = FirstBalanceFragment(this)
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
                params.bottomMargin = 20
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
            val filteredList = balanceViewModel.getBalanceCodes().filter {
                query.lowercase() in it.toString().lowercase()
            }.toMutableList()
            adapterBalanceCode.setNewList(filteredList)
        }
    }

    /*
    Change the behaviour of the layout on an item click of the recycler view
    containing every balance code.
     */
    override fun onCodeClick(code: Int) {
        searchView.queryHint = "Code : $code"
        recyclerViewBalanceCode.visibility = View.GONE
        searchView.clearFocus()


        val frontBalance = balanceViewModel.getFrontBalanceList().filter {
            it.end == "Front" && it.code == code
        }.toMutableList()

        val backBalance = balanceViewModel.getFrontBalanceList().filter {
            it.end == "Back" && it.code == code
        }.toMutableList()

        adapterFrontBalance.setNewList(frontBalance)
        adapterBackBalance.setNewList(backBalance)
    }





}