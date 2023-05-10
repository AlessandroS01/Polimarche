package com.example.polimarche.data_container.problem.occurring_problem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.data_container.problem.ProblemsRepository

class OccurringProblemViewModel: ViewModel() {

    //private val problemsRepository = ProblemsRepository()

    private var _listOccurringProblemData: MutableLiveData<MutableList<DataOccurringProblem>> =
        ProblemsRepository.listOccurringProblems
        //problemsRepository.listOccurringsProblem
    val listOccurringProblem: MutableLiveData<MutableList<DataOccurringProblem>>
        get() = _listOccurringProblemData


    fun addNewOccurringProblem(problem: DataOccurringProblem){
        ProblemsRepository.addNewOccurringProblem(problem)
        //problemsRepository.addNewOccurringProblem(problem)
    }

    fun removeItemFromList(item: DataOccurringProblem, newDescription: String){
        ProblemsRepository.removeItemFromOccurringProblem(item, newDescription)
        //problemsRepository.removeItemFromOccurringProblem(item, newDescription)
    }

    /*
    This method return a new MutableLiveData list in which all the elements
    are an instance of DataOccurringProblem class having the attribute
    problemCode set to the problemCode clicked on ProblemsSetupFragment.
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