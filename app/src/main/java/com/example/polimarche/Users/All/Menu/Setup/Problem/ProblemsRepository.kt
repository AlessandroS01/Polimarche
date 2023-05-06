package com.example.polimarche.Users.All.Menu.Setup.Problem

import androidx.lifecycle.MutableLiveData
import com.example.polimarche.Data.DataOccurringProblem
import com.example.polimarche.Data.DataProblem
import com.example.polimarche.Data.DataSolvedProblem

/*
Class that will contain all the data from which the various viewModel
referring to any kind of DataProblem, like DataSolvedProblem or even DataProblem,
can get and set their list values.
 */
object ProblemsRepository {

    /*
    Contains the list of all the problems faced by all the different setups
     */
    private val _listProblemsData: MutableLiveData<MutableList<DataProblem>> =
        MutableLiveData(
            mutableListOf(
                DataProblem(1, "Vite mancante"),
                DataProblem(2, "Bullone"),
                DataProblem(3, "Sdrogo"),
                DataProblem(4, "Aiuto"),
                DataProblem(5, "Vero"),
            )
        )
    val listProblems get()= _listProblemsData

    /*
    Contains the list of setups that face the problem clicked
     */
    private val _listOccurringProblemsData: MutableLiveData<MutableList<DataOccurringProblem>> =
        MutableLiveData(
            mutableListOf(
                DataOccurringProblem(1, 1, "Rottura posteriore"),
                DataOccurringProblem(1, 3, "Rottura posteriore"),
                DataOccurringProblem(2, 1, "Rottura posteriore"),
                DataOccurringProblem(3, 1, "Rottura posteriore"),
                DataOccurringProblem(4, 1, "Rottura posteriore")
            )
        )
    val listOccurringProblems get()=_listOccurringProblemsData

    /*
    Contains the list of setups that solved the problem clicked
     */
    private val _listSolvedProblemsData: MutableLiveData<MutableList<DataSolvedProblem>> =
        MutableLiveData(
            emptyList<DataSolvedProblem>().toMutableList()
        )
    val listSolvedProblems get() = _listSolvedProblemsData

    /*
    It adds a new problem to _listProblemsData when the user
    decides to create a new problem.
     */
    fun addNewProblem(
        newProblem: DataProblem
    ){
        _listProblemsData.value =
            _listProblemsData.value?.plus(newProblem) as MutableList<DataProblem>?
    }

    /*
    It firstly removes the item from the list of OccurringProblems and then
    creates a new object of DataSolvedProblem to add to the list _listSolvedProblemsData.
    It calls a method in which the new element is formally added.
     */
    fun removeItemFromOccurringProblem(
        occurredProblem: DataOccurringProblem,
        description: String
    ){
        _listOccurringProblemsData.value?.remove(occurredProblem)

        val newSolvedProblem = DataSolvedProblem(
            occurredProblem.problemCode,
            occurredProblem.setupCode,
            description
        )
        addNewSolvedProblem(newSolvedProblem)
    }
    private fun addNewSolvedProblem(newSolvedProblem: DataSolvedProblem){
        _listSolvedProblemsData.value =
            _listSolvedProblemsData.value?.plus(newSolvedProblem) as MutableList<DataSolvedProblem>?
    }


    /*
    It firstly removes the item from the list of SolvedProblems
    and then creates a new object of of DataOccurringProblem to add
    to the list _listOccurringProblemsData.
    It calls a method in which the new element is formally added.
     */
    fun removeItemFromSolvedProblem(
        solvedProblem: DataSolvedProblem,
        description: String
    ){
        _listSolvedProblemsData.value?.remove(solvedProblem)

        val newOccurringProblem = DataOccurringProblem(
            solvedProblem.problemCode,
            solvedProblem.setupCode,
            description
        )
        addNewOccurringProblem(newOccurringProblem)
    }
    fun addNewOccurringProblem(newOccurringProblem: DataOccurringProblem){
        _listOccurringProblemsData.value =
            _listOccurringProblemsData.value?.plus(newOccurringProblem) as MutableList<DataOccurringProblem>?
    }





}