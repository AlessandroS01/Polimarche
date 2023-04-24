package com.example.polimarche.Users.All.Menu.Main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataTeamMember
import com.example.polimarche.Data.DataWorkshopArea
import com.example.polimarche.Users.All.Adapters.TeamMembersManagerAdapter

class TeamFragment : Fragment(R.layout.fragment_general_main_team), TeamMembersManagerAdapter.OnWorkshopAreaClickListener {

    private var listMembers : MutableList<DataTeamMember> = insertMembers()
    private var listDepartment: MutableList<DataWorkshopArea> = insertDepartments()
    private var membersToggled : MutableList<DataTeamMember> = mutableListOf()
    private lateinit var adapter : TeamMembersManagerAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.membersWorkshopAreasRecyclerView)
        recyclerView.layoutManager = layoutManager
        adapter = TeamMembersManagerAdapter(listMembers, listDepartment, this)
        recyclerView.adapter = adapter
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onWorkshopAreaClick(workshopAreaClicked: String) {
        val department: String = workshopAreaClicked
        switchingVisibility(department)
        adapter.notifyDataSetChanged()
    }

    /*
    Method used for toggle visibility of the team members after the click on
    the department name inside the fragment.
     */
    private fun switchingVisibility(department : String){
        if (membersToggled.none{ it.workshopArea == department }) {
            membersToggled += listMembers.filter { it.workshopArea == department }
            listMembers -= membersToggled.toSet()
        }
        else {
            listMembers.addAll(membersToggled.filter { it.workshopArea == department })
            membersToggled -= listMembers.toSet()
        }
    }

    private fun insertMembers(): MutableList<DataTeamMember> {
        return mutableListOf(
            DataTeamMember(1028373, "Antonio", "C", "10-10-2001",
                "@", "291342392",  "Telaio"),
            DataTeamMember(1088392, "Andrea", "C", "10-10-2001",
                "@", "291342392",  "Telaio"),
            DataTeamMember(1097931, "Giacomo", "C", "10-10-2001",
                "@", "291342392",  "Aereodinamica"),
            DataTeamMember(1088392, "Luca", "C", "10-10-2001",
                "@", "291342392",  "Aereodinamica"),
            DataTeamMember(1097931, "Alessandro", "C", "10-10-2001",
                "@", "291342392",  "Marketing"),
            DataTeamMember(1088392, "Francesco", "C", "10-10-2001",
                "@", "291342392",  "Marketing"),
            DataTeamMember(1097931, "Las", "C", "10-10-2001",
                "@", "291342392",  "Elettronica"),
            DataTeamMember(1088392, "Francesco", "C", "10-10-2001",
                "@", "291342392",  "Dinamica"),
            DataTeamMember(1097931, "Alessandro", "C", "10-10-2001",
                "@", "291342392",  "Dinamica"),
            DataTeamMember(1097931, "Francesco", "C", "10-10-2001",
                "@", "291342392",  "Battery Pack"),
            DataTeamMember(1097931, "Al", "C", "10-10-2001",
                "@", "291342392",  "Battery Pack"),
            DataTeamMember(1097931, "Aasd", "C", "10-10-2001",
                "@", "291342392",  "Battery Pack"),
            DataTeamMember(1097931, "Luca", "C", "10-10-2001",
                "@", "291342392",  "Battery Pack"),
            DataTeamMember(1097931, "Antonio", "C", "10-10-2001",
                "@", "291342392",  "Battery Pack"),
            DataTeamMember(1097931, "Asdrubale", "C", "10-10-2001",
                "@", "291342392",  "Dinamica"),
        )
    }

    private fun insertDepartments(): MutableList<DataWorkshopArea>{
        return mutableListOf(
            DataWorkshopArea("Telaio", 1028373),
            DataWorkshopArea("Aereodinamica", 1097931),
            DataWorkshopArea("Marketing", 1097931),
            DataWorkshopArea("Elettronica", 1097931),
            DataWorkshopArea("Dinamica", 1088392),
            DataWorkshopArea("Battery Pack", 1028373),
        )
    }
}