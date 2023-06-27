package com.example.polimarche.users.all.menu.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.team_members_workshop.DataTeamMember
import com.example.polimarche.data_container.team_members_workshop.DataWorkshopArea
import com.example.polimarche.data_container.team_members_workshop.TeamRepository
import com.example.polimarche.data_container.team_members_workshop.TeamViewModel
import kotlinx.coroutines.launch

class TeamFragment : Fragment(
    R.layout.fragment_general_main_team
), TeamAdapter.OnWorkshopAreaClickListener {

    private val teamViewModel: TeamViewModel by viewModels()

    private lateinit var adapter : TeamAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Create a MediatorLiveData to observe both LiveData objects
        val mediatorLiveData = MediatorLiveData<Pair<List<DataTeamMember>?, List<DataWorkshopArea>?>>()
        mediatorLiveData.addSource(teamViewModel.listMembers) { listMembers ->
            mediatorLiveData.value = Pair(listMembers, teamViewModel.listDepartments.value)
        }
        mediatorLiveData.addSource(teamViewModel.listDepartments) { listDepartments ->
            mediatorLiveData.value = Pair(teamViewModel.listMembers.value, listDepartments)
        }

        // Observe the MediatorLiveData
        mediatorLiveData.observe(viewLifecycleOwner) { (listMembers, listDepartments) ->
            // Perform your action when both values change
            if (listMembers != null && listDepartments != null) {
                val layoutManager = LinearLayoutManager(context)
                recyclerView = view.findViewById(R.id.membersWorkshopAreasRecyclerView)
                recyclerView.layoutManager = layoutManager
                adapter = TeamAdapter(teamViewModel, this)
                Log.d("TeamFragment", "Membro del team: $adapter")
                recyclerView.adapter = adapter
            }
        }


    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onWorkshopAreaClick(workshopAreaClicked: String) {
        val department: String = workshopAreaClicked
        adapter.switchingVisibility(department)
    }
}