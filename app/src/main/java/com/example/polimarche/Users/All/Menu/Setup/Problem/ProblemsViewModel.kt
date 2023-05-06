package com.example.polimarche.Users.All.Menu.Setup.Problem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.Data.DataProblem

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