package com.example.polimarche.data_container.team_members_workshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.team_members_workshop.DataTeamMember
import com.example.polimarche.data_container.team_members_workshop.DataWorkshopArea
import com.example.polimarche.data_container.team_members_workshop.TeamRepository
import kotlinx.coroutines.launch

class TeamViewModel: ViewModel() {

    private val teamRepository: TeamRepository = TeamRepository()

    init {
        viewModelScope.launch {
            teamRepository.fetchDataFromFirestore()
        }
    }

    private val _listMembers: MutableLiveData<MutableList<DataTeamMember>> =
        teamRepository.listMembers
    val listMembers get() = _listMembers

    private val _listDepartments: MutableLiveData<MutableList<DataWorkshopArea>> =
        teamRepository.listDepartments
    val listDepartments get() = _listDepartments

}