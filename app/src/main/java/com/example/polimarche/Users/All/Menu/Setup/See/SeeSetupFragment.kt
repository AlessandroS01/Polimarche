package com.example.polimarche.Users.All.Menu.Setup.See

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.mobileprogramming.databinding.FragmentGeneralSetupSeeSetupBinding
import com.example.polimarche.Data.*
import com.example.polimarche.Users.All.Adapters.SeeSetupAdapter

class SeeSetupFragment : Fragment(R.layout.fragment_general_setup_see_setup){

    private val setupListCode: MutableList<Int> = initializeSetupCodes()
    private lateinit var searchView: SearchView
    private lateinit var adapter: SeeSetupAdapter
    private lateinit var recyclerView: RecyclerView

    private var _binding: FragmentGeneralSetupSeeSetupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneralSetupSeeSetupBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchViewSetupSee
        recyclerView = binding.seeSetupList

        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        adapter = SeeSetupAdapter(setupListCode)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

    }

    private fun initializeSetupCodes(): MutableList<Int> {
        val listCodes: MutableList<Int> = emptyList<Int>().toMutableList()
        val listSetup = mutableListOf( DataSetup(
                1,
                DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
                DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
                DataDamper(6, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(7, "End", 1.3, 1.2, 4.1, 1.7),
                DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
                DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
                DataBalance(5, "Front", 44.0, 56.0),
                DataBalance(6, "Back", 44.0, 56.0),
                "Endurance",
                "1°",
                mutableListOf("")
            ),
            DataSetup(
                2,
                DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
                DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
                DataDamper(6, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(7, "End", 1.3, 1.2, 4.1, 1.7),
                DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
                DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
                DataBalance(5, "Front", 44.0, 56.0),
                DataBalance(6, "Back", 44.0, 56.0),
                "Endurance",
                "1°",
                mutableListOf("")
            ),
            DataSetup(
                3,
                DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
                DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
                DataDamper(6, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(7, "End", 1.3, 1.2, 4.1, 1.7),
                DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
                DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
                DataBalance(5, "Front", 44.0, 56.0),
                DataBalance(6, "Back", 44.0, 56.0),
                "Endurance",
                "1°",
                mutableListOf("")
            )
        )
        for ( element in listSetup){
            listCodes.add(element.code)
        }
        return listCodes

    }

    private fun filterList(query: String?){
        if(query != null){
            val filteredList = setupListCode.filter { query in it.toString() }
            adapter.setFilteredList(filteredList.toMutableList())
        }
    }
}