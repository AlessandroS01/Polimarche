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
import com.example.mobileprogramming.databinding.FragmentManagersSetupDeleteSetupBinding
import com.example.polimarche.Data.DataSeeSetupCode
import com.example.polimarche.Managers.M_Adapters.DeleteSetupAdapter
import com.example.polimarche.Managers.Menu.Setup.Delete.VisualizeSetupFragment
/*
    Fragment used to show all the setup and their parameters that, unlike
    the fragment seeSetup, add the possibility to delete the setups.
 */
class MenuDeleteSetupFragment : Fragment(R.layout.fragment_managers_setup_delete_setup),
    DeleteSetupAdapter.OnSetupCodeClickListener{

    private val setupList: MutableList<DataSeeSetupCode> = insertSetup()
    private lateinit var searchView: SearchView
    private lateinit var adapter: DeleteSetupAdapter
    private lateinit var recyclerViewDeleteSetup: RecyclerView

    private var _binding: FragmentManagersSetupDeleteSetupBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagersSetupDeleteSetupBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = binding.searchViewSetupDelete
        recyclerViewDeleteSetup = binding.deleteSetupList

        val layoutManager = LinearLayoutManager(context)

        recyclerViewDeleteSetup.layoutManager = layoutManager
        adapter = DeleteSetupAdapter(setupList, this)
        recyclerViewDeleteSetup.adapter = adapter

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

    /*
    Method used by the search view that allows the adapter to show the items
    that matches with the given input.
     */
    private fun filterList(query: String?){
        if(query != null){
            val filteredList = setupList.filter { query in it.code.toString() }
            adapter.setFilteredList(filteredList.toMutableList())
        }
    }

    /*
    Override the method contained inside the interface OnSetupCodeClickListener
    that let the user see the details of the setup clicked.
    Add the possibility to delete the setup.
     */
    override fun onSetupCodeClickListener(position: Int) {
        val visualizeSetup = VisualizeSetupFragment(position, setupList, adapter)
        parentFragmentManager.beginTransaction().apply{
            replace(binding.frameLayoutDeleteSetupVisualizeSetup.id, visualizeSetup).commit()
        }
    }

}