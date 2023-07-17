package com.example.polimarche.data_container.problem.solved_problem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.problem.ProblemsRepository
import kotlinx.coroutines.launch

class SolvedProblemViewModel: ViewModel() {

    private val problemsRepository: ProblemsRepository = ProblemsRepository()

    init {
        viewModelScope.launch {
            problemsRepository.fetchSolvedProblemFromFirestore()
        }
    }

    fun initialize(){
        viewModelScope.launch {
            problemsRepository.fetchSolvedProblemFromFirestore()
        }
    }

    //private val problemsRepository = ProblemsRepository()

    private var _listSolvedProblemData: MutableLiveData<MutableList<DataSolvedProblem>> =
        problemsRepository.listSolvedProblems
        //problemsRepository.listSolvedProblems
    val listSolvedProblem: MutableLiveData<MutableList<DataSolvedProblem>>
        get() = _listSolvedProblemData


    suspend fun removeItemFromList(item: DataSolvedProblem, description: String){
        problemsRepository.removeItemFromSolvedProblem(item, description)
    }

    /*
    Questo metodo restituisce un nuovo elenco MutableLiveData in cui tutti gli elementi
    sono un'istanza della classe DataSolvedProblem con l'attributo
    problemCode impostato su problemCode cliccato su ProblemsSetupFragment.
     */
    fun filterListByProblemCode(problemSolvedCode: Int): MutableLiveData<MutableList<DataSolvedProblem>>{
        val mutableLiveData: MutableLiveData<MutableList<DataSolvedProblem>> =
            MutableLiveData<MutableList<DataSolvedProblem>>().apply {
                value= emptyList<DataSolvedProblem>().toMutableList()
            }
        _listSolvedProblemData.value?.forEach {
            if(it.problemCode == problemSolvedCode) {
                mutableLiveData.value = mutableLiveData.value?.plus(it)
                        as MutableList<DataSolvedProblem>?
            }
        }
        return mutableLiveData
    }


}