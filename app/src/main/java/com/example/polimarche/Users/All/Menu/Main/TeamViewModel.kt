package com.example.polimarche.Users.All.Menu.Main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.Data.DataTeamMember
import com.example.polimarche.Data.DataWorkshopArea

class TeamViewModel: ViewModel() {

    private val _listMembers: MutableLiveData<MutableList<DataTeamMember>> =
        TeamRepository.listMembers
    val listMembers get() = _listMembers

    private val _listDepartments: MutableLiveData<MutableList<DataWorkshopArea>> =
        TeamRepository.listDepartments
    val listDepartments get() = _listDepartments

}