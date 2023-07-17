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

class TeamFragment : Fragment(R.layout.fragment_general_main_team), TeamAdapter.OnWorkshopAreaClickListener {

    // TeamFragment implementa TeamAdapter.OnWorkshopAreaClickListener per gestire i click sulle
    // aree di lavoro del team
    private val teamViewModel: TeamViewModel by viewModels() // fornisce un'istanza del TeamViewModel

    private lateinit var adapter : TeamAdapter // Dichiarazione di una variabile di tipo TeamAdapter
    private lateinit var recyclerView: RecyclerView // Dichiarazione di una variabile di tipo RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Viene creato un oggetto MediatorLiveData che osserva due LiveData: listMembers e listDepartments.

        // Quando uno dei due oggetti cambia, viene aggiornato il valore di mediatorLiveData
        val mediatorLiveData = MediatorLiveData<Pair<List<DataTeamMember>?, List<DataWorkshopArea>?>>()
        mediatorLiveData.addSource(teamViewModel.listMembers) { listMembers ->
            // Aggiunge listMembers come sorgente di dati per mediatorLiveData
            // viene creata una coppia di liste di membri del team e dipartimenti e viene assegnata
            // a mediatorLiveData.value
            mediatorLiveData.value = Pair(listMembers, teamViewModel.listDepartments.value)
        }
        mediatorLiveData.addSource(teamViewModel.listDepartments) { listDepartments ->
            // Aggiunge listDepartments come sorgente di dati per mediatorLiveData
            // viene creata una coppia di liste di membri del team e dipartimenti e viene assegnata
            // a mediatorLiveData.value
            mediatorLiveData.value = Pair(teamViewModel.listMembers.value, listDepartments)
        }

       // Viene aggiunto un observer a mediatorLiveData per ricevere gli aggiornamenti dei valori
        // di listMembers e listDepartments
        mediatorLiveData.observe(viewLifecycleOwner) { (listMembers, listDepartments) ->
            // Quando entrambi i valori cambiano e sono diversi da null
            if (listMembers != null && listDepartments != null) {
                // Crea un oggetto LinearLayoutManager che verrà utilizzato come layout manager
                // per la RecyclerView
                val layoutManager = LinearLayoutManager(context)
                recyclerView = view.findViewById(R.id.membersWorkshopAreasRecyclerView) // riferimento
                // all'elemento RecyclerView
                recyclerView.layoutManager = layoutManager // Imposta il layout manager
                // alla recyclerView
                // Crea un'istanza di TeamAdapter passando il teamViewModel e l'implementazione
                // di OnWorkshopAreaClickListener
                adapter = TeamAdapter(teamViewModel, this)
                recyclerView.adapter = adapter // Imposta l'adapter sulla RecyclerView
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    // Implementazione del metodo onWorkshopAreaClick dell'interfaccia OnWorkshopAreaClickListener
    override fun onWorkshopAreaClick(workshopAreaClicked: String) {
        val department: String = workshopAreaClicked // Ottiene l'area di lavoro del team selezionata
        // dal parametro workshopAreaClicked
        adapter.switchingVisibility(department) // Chiama il metodo switchingVisibility sull'adapter
                                                // per gestire la visibilità degli elementi
    }
}