package com.example.polimarche.Users.All.Menu.Setup.Problem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.Data.DataSolvedProblem

class SolvedProblemViewModel: ViewModel() {

    //private val problemsRepository = ProblemsRepository()

    private var _listSolvedProblemData: MutableLiveData<MutableList<DataSolvedProblem>> =
        ProblemsRepository.listSolvedProblems
        //problemsRepository.listSolvedProblems
    val listSolvedProblem: MutableLiveData<MutableList<DataSolvedProblem>>
        get() = _listSolvedProblemData


    fun removeItemFromList(item: DataSolvedProblem, description: String){
        ProblemsRepository.removeItemFromSolvedProblem(item, description)
    }

    /*
    This method return a new MutableLiveData list in which all the elements
    are an instance of DataOccurringProblem class having the attribute
    problemCode set to the problemCode clicked on ProblemsSetupFragment.
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