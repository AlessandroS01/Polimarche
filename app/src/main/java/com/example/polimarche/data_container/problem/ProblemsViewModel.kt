package com.example.polimarche.data_container.problem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.ProblemsRepository

class ProblemsViewModel: ViewModel() {

    /*
    Used to keep the updated the list of problems.
     */
    //private val problemsRepository = ProblemsRepository()

    private val _listProblems: MutableLiveData<MutableList<DataProblem>> =
        ProblemsRepository.listProblems
        //problemsRepository.listProblems
    val listProblems get() = _listProblems

    fun addNewProblem(newProblem: DataProblem){
        ProblemsRepository.addNewProblem(newProblem)
        //problemsRepository.addNewProblem(newProblem)
    }


}