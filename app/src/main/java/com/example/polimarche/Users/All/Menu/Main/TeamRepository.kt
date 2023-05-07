package com.example.polimarche.Users.All.Menu.Main

import androidx.lifecycle.MutableLiveData
import com.example.polimarche.Data.DataTeamMember
import com.example.polimarche.Data.DataWorkshopArea

object TeamRepository {

    private val _listMembers: MutableLiveData<MutableList<DataTeamMember>> =
        MutableLiveData(
            mutableListOf(
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
        )
    val listMembers get() = _listMembers

    private val _listDepartments: MutableLiveData<MutableList<DataWorkshopArea>> =
        MutableLiveData(
            mutableListOf(
                DataWorkshopArea("Telaio", 1028373),
                DataWorkshopArea("Aereodinamica", 1097931),
                DataWorkshopArea("Marketing", 1097931),
                DataWorkshopArea("Elettronica", 1097931),
                DataWorkshopArea("Dinamica", 1088392),
                DataWorkshopArea("Battery Pack", 1028373),
            )
        )
    val listDepartments get() = _listDepartments


}