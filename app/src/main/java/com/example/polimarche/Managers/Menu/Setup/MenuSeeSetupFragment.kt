package com.example.polimarche.Managers.Menu.Setup

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
import com.example.mobileprogramming.databinding.FragmentManagersSetupSeeSetupBinding
import com.example.polimarche.Data.DataSeeSetupCode
import com.example.polimarche.General_Adapters.SeeSetupAdapter

class MenuSeeSetupFragment : Fragment(R.layout.fragment_managers_setup_see_setup){

    private val setupList: MutableList<DataSeeSetupCode> = insertSetup()
    private lateinit var searchView: SearchView
    private lateinit var adapter: SeeSetupAdapter
    private lateinit var recyclerView: RecyclerView

    private var _binding: FragmentManagersSetupSeeSetupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagersSetupSeeSetupBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchViewSetupSee
        recyclerView = binding.seeSetupList

        val layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager
        adapter = SeeSetupAdapter(setupList)
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

    private fun insertSetup(): MutableList<DataSeeSetupCode> {
        return mutableListOf(
            DataSeeSetupCode(1),
            DataSeeSetupCode(2),
            DataSeeSetupCode(3),
            DataSeeSetupCode(4),
            DataSeeSetupCode(5),
            DataSeeSetupCode(10),
            DataSeeSetupCode(11),
            DataSeeSetupCode(12),
            DataSeeSetupCode(7),
            DataSeeSetupCode(8),
            DataSeeSetupCode(9),
            DataSeeSetupCode(17),
            DataSeeSetupCode(6)
        )
    }

    private fun filterList(query: String?){
        if(query != null){
            val filteredList = setupList.filter { query in it.code.toString() }
            adapter.setFilteredList(filteredList.toMutableList())
        }
    }
}