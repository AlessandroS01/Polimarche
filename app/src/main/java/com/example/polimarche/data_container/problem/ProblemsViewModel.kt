package com.example.polimarche.data_container.problem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.ProblemsRepository
import com.example.polimarche.data_container.track.TracksRepository
import kotlinx.coroutines.launch

class ProblemsViewModel: ViewModel() {
    private val problemsRepository: ProblemsRepository = ProblemsRepository()

    init {
        viewModelScope.launch {
            problemsRepository.fetchProblemFromFirestore()
        }
    }

    fun initialize(){
        viewModelScope.launch {
            problemsRepository.fetchProblemFromFirestore()

        }
    }
    /*
    Used to keep the updated the list of problems.
     */
    //private val problemsRepository = ProblemsRepository()

    private val _listProblems: MutableLiveData<MutableList<DataProblem>> =
        problemsRepository.listProblems
    val listProblems get() = _listProblems

    fun addNewProblem(newProblem: DataProblem){
        problemsRepository.addNewProblem(newProblem)
    }


}