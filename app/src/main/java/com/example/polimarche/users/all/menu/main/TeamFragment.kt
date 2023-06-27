package com.example.polimarche.users.all.menu.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.team_members_workshop.TeamRepository
import com.example.polimarche.data_container.team_members_workshop.TeamViewModel
import kotlinx.coroutines.launch

class TeamFragment : Fragment(
    R.layout.fragment_general_main_team
), TeamAdapter.OnWorkshopAreaClickListener {

    private val teamViewModel: TeamViewModel by viewModels()

    private lateinit var adapter : TeamAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            TeamRepository.fetchDataFromFirestore()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.membersWorkshopAreasRecyclerView)
        recyclerView.layoutManager = layoutManager
        adapter = TeamAdapter(teamViewModel, this)
        Log.d("TeamFragment", "Membro del team:$adapter")
        recyclerView.adapter = adapter
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onWorkshopAreaClick(workshopAreaClicked: String) {
        val department: String = workshopAreaClicked
        adapter.switchingVisibility(department)
    }
}