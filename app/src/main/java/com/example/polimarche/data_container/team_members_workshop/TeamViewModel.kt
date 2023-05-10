package com.example.polimarche.data_container.team_members_workshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.data_container.team_members_workshop.DataTeamMember
import com.example.polimarche.data_container.team_members_workshop.DataWorkshopArea
import com.example.polimarche.data_container.team_members_workshop.TeamRepository

class TeamViewModel: ViewModel() {

    private val _listMembers: MutableLiveData<MutableList<DataTeamMember>> =
        TeamRepository.listMembers
    val listMembers get() = _listMembers

    private val _listDepartments: MutableLiveData<MutableList<DataWorkshopArea>> =
        TeamRepository.listDepartments
    val listDepartments get() = _listDepartments

}