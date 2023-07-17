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

    // Utilizziamo viewModelScope.launch per avviare un'operazione asincrona all'interno
    // del ViewModelScope.
    init {
        viewModelScope.launch {
            //questo metodo è responsabile del recupero dei dati relativi al team da Firestore
            teamRepository.fetchDataFromFirestore()
        }
    }

    //_listMembers è una variabile privata di tipo MutableLiveData che contiene una lista mutabile
    // di oggetti DataTeamMember.
    // Viene inizializzata con il valore di teamRepository.listMembers
    private val _listMembers: MutableLiveData<MutableList<DataTeamMember>> =
        teamRepository.listMembers
    val listMembers get() = _listMembers
    // L'uso del modificatore get() indica che questa proprietà ha solo un'implementazione
    // per la lettura e non per la scrittura.

    private val _listDepartments: MutableLiveData<MutableList<DataWorkshopArea>> =
        teamRepository.listDepartments
    val listDepartments get() = _listDepartments

}