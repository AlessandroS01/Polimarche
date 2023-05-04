package com.example.polimarche.Users.All.Menu.Setup.Problem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.Data.DataOccurringProblem

class OccurringProblemViewModel: ViewModel() {

    private var _listOccurringProblemData: MutableLiveData<MutableList<DataOccurringProblem>> =
        MutableLiveData(
            mutableListOf(
                DataOccurringProblem(1, 1, "Rottura posteriore"),
                DataOccurringProblem(1, 3, "Rottura posteriore"),
                DataOccurringProblem(2, 1, "Rottura posteriore"),
                DataOccurringProblem(3, 1, "Rottura posteriore"),
                DataOccurringProblem(4, 1, "Rottura posteriore")
            )
        )
    val listOccurringProblem: MutableLiveData<MutableList<DataOccurringProblem>>
        get() = _listOccurringProblemData


    fun addNewOccurringProblem(problem: DataOccurringProblem){
        _listOccurringProblemData.value =
            _listOccurringProblemData.value?.plus(problem) as MutableList<DataOccurringProblem>?
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

    /*
    Removes an item passed by view only after it ascertains if
    the item passed exist or not
     */
    fun removeItemFromList(item: DataOccurringProblem){
        if (checkItemExist(item)) {
            listOccurringProblem.value?.remove(item)
        }
    }

    private fun checkItemExist(item: DataOccurringProblem): Boolean{
        return listOccurringProblem.value?.contains(item) !!
    }

    // TODO AGGIUNGERE IL PACKAGE REPOSITORY PER PRENDERE I DATI DA UN DATABASE
    // TODO DOPO AVER AGGIUNTO IL PACKAGE CREARE UNA CLASSE SERVICE
}