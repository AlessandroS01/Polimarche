package com.example.polimarche.users.all.menu.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.team_members_workshop.TeamViewModel

class TeamFragment : Fragment(
    R.layout.fragment_general_main_team
), TeamAdapter.OnWorkshopAreaClickListener {

    private val teamViewModel: TeamViewModel by viewModels()

    private lateinit var adapter : TeamAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.membersWorkshopAreasRecyclerView)
        recyclerView.layoutManager = layoutManager
        adapter = TeamAdapter(teamViewModel, this)
        recyclerView.adapter = adapter
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onWorkshopAreaClick(workshopAreaClicked: String) {
        val department: String = workshopAreaClicked
        adapter.switchingVisibility(department)
    }
}