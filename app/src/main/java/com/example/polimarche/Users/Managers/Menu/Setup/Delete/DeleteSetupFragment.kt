package com.example.polimarche.Users.Managers.Menu.Setup.Delete

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
import com.example.polimarche.Data.*
import com.example.polimarche.Users.Managers.Adapters.DeleteSetupAdapter

/*
    Fragment used to show all the setup and their parameters that, unlike
    the fragment seeSetup, add the possibility to delete the setups.
 */
class DeleteSetupFragment : Fragment(R.layout.fragment_managers_setup_delete_setup),
    DeleteSetupAdapter.OnSetupCodeClickListener{

    private val setupListCodes: MutableList<DataSetup> = insertSetupCodes()
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
        adapter = DeleteSetupAdapter(setupListCodes, this)
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

    private fun insertSetupCodes(): MutableList<DataSetup> {
        return mutableListOf(
            DataSetup(
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
    }

    /*
    Method used by the search view that allows the adapter to show the items
    that matches with the given input.
     */
    private fun filterList(query: String?){
        if(query != null){
            val filteredList = setupListCodes.filter { query in it.code.toString() }
            adapter.setFilteredList(filteredList.toMutableList())
        }
    }

    /*
    Override the method contained inside the interface OnSetupCodeClickListener
    that let the user see the details of the setup clicked.
    Add the possibility to delete the setup.
     */
    override fun onSetupCodeClickListener(position: Int) {
        val visualizeSetup = VisualizeSetupFragment(position, setupListCodes, adapter)
        parentFragmentManager.beginTransaction().apply{
            replace(binding.frameLayoutDeleteSetupVisualizeSetup.id, visualizeSetup).commit()
        }
    }

}