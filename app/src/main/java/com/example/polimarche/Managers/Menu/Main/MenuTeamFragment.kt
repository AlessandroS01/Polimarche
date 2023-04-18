package com.example.polimarche.Managers.Menu.Main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataModelTeamMembers
import com.example.polimarche.Managers.M_Adapters.TeamMembersManagerAdapter

class MenuTeamFragment : Fragment(R.layout.fragment_managers_main_team), TeamMembersManagerAdapter.OnWorkshopAreaClickListener {

    private var listMembers : MutableList<DataModelTeamMembers> = insertMembers()
    private var membersToggled : MutableList<DataModelTeamMembers> = mutableListOf()
    private lateinit var adapter : TeamMembersManagerAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.membersWorkshopAreasRecyclerView)
        recyclerView.layoutManager = layoutManager
        adapter = TeamMembersManagerAdapter(listMembers, this)
        recyclerView.adapter = adapter
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onWorkshopAreaClick(position: Int) {
        val clickedWorkshopArea : DataModelTeamMembers = listMembers[position]
        val department: String = clickedWorkshopArea.identifyItem
        switchingVisibility(department)
        adapter.notifyDataSetChanged()
    }

    private fun switchingVisibility(department : String){
        if (membersToggled.none{ it.department == department }) {
            membersToggled += listMembers.filter { it.department == department }
            listMembers -= membersToggled.toSet()
        }
        else {
            for (index in 0 until listMembers.size){
                if (listMembers[index].identifyItem == department){
                    listMembers.addAll(index + 1, membersToggled.filter { it.department == department })
                    membersToggled -= listMembers.toSet()
                }
            }

        }
    }

    private fun insertMembers(): MutableList<DataModelTeamMembers> {
        return mutableListOf(
            DataModelTeamMembers("Telaio", 0, ""),
            DataModelTeamMembers("1028373 : Antonio", 1, "Telaio"),
            DataModelTeamMembers("1088392 : Andrea", 1, "Telaio"),
            DataModelTeamMembers("Aereodinamica", 0, ""),
            DataModelTeamMembers("1097931 : Giacomo", 1, "Aereodinamica"),
            DataModelTeamMembers("1088392 : Luca", 1, "Aereodinamica"),
            DataModelTeamMembers("Marketing", 0, ""),
            DataModelTeamMembers("1097931 : Alessandro", 1, "Marketing"),
            DataModelTeamMembers("1088392 : Francesco", 1, "Marketing"),
            DataModelTeamMembers("Elettronica", 0, ""),
            DataModelTeamMembers("1097931 : Alessandro", 1, "Elettronica"),
            DataModelTeamMembers("Dinamica", 0, ""),
            DataModelTeamMembers("1088392 : Francesco", 1, "Dinamica"),
            DataModelTeamMembers("1097931 : Alessandro", 1, "Dinamica"),
            DataModelTeamMembers("Battery Pack", 0, ""),
            DataModelTeamMembers("1097931 : Francesco", 1, "Battery Pack"),
            DataModelTeamMembers("1097931 : Al", 1, "Battery Pack"),
            DataModelTeamMembers("1097931 : Aasd", 1, "Battery Pack"),
            DataModelTeamMembers("1097931 : Luca", 1, "Battery Pack"),
            DataModelTeamMembers("1097931 : Antonio", 1, "Battery Pack")
        )
    }

}