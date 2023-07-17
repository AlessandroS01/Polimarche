package com.example.polimarche.data_container.problem.occurring_problem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.problem.ProblemsRepository
import kotlinx.coroutines.launch

class OccurringProblemViewModel: ViewModel() {

    private val problemsRepository: ProblemsRepository = ProblemsRepository()

    init {
        viewModelScope.launch {
            problemsRepository.fetchOccurringProblemFromFirestore()
        }
    }

    fun initialize(){
        viewModelScope.launch {
            problemsRepository.fetchOccurringProblemFromFirestore()

        }
    }

    //private val problemsRepository = ProblemsRepository()

    private var _listOccurringProblemData: MutableLiveData<MutableList<DataOccurringProblem>> =
        problemsRepository.listOccurringProblems
        //problemsRepository.listOccurringsProblem
    val listOccurringProblem: MutableLiveData<MutableList<DataOccurringProblem>>
        get() = _listOccurringProblemData


    fun addNewOccurringProblem(problem: DataOccurringProblem){
        problemsRepository.addNewOccurringProblem(problem)
        //problemsRepository.addNewOccurringProblem(problem)
    }

    suspend fun removeItemFromList(item: DataOccurringProblem, newDescription: String){
        problemsRepository.removeItemFromOccurringProblem(item, newDescription)
        //problemsRepository.removeItemFromOccurringProblem(item, newDescription)
    }

    /*
    Questo metodo restituisce un nuovo elenco MutableLiveData in cui tutti gli elementi
    sono un'istanza della classe DataOccurringProblem con l'attributo
    problemCode impostato su problemCode cliccato su ProblemsSetupFragment.
     */
    fun filterListByProblemCode(problemCode: Int): MutableLiveData<MutableList<DataOccurringProblem>>{
        val mutableLiveData: MutableLiveData<MutableList<DataOccurringProblem>> =
            MutableLiveData<MutableList<DataOccurringProblem>>().apply {
                value= emptyList<DataOccurringProblem>().toMutableList()
            }
        _listOccurringProblemData.value?.forEach {
            if(it.problemCode == problemCode) {
                mutableLiveData.value = mutableLiveData.value?.plus(it)
                        as MutableList<DataOccurringProblem>?
            }
        }
        return mutableLiveData
    }



}