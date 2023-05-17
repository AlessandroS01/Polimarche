package com.example.polimarche.data_container.balance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.data_container.spring.SpringRepository

class BalanceViewModel: ViewModel() {

    private val _balanceList: MutableLiveData<MutableList<DataBalance>> =
        BalanceRepository.balanceList
    val balanceList get() = _balanceList


    fun getBalanceCodes(): MutableList<Int>{
        val listCodes = emptyList<Int>().toMutableList()
        _balanceList.value?.forEach {
            if(!listCodes.contains(it.code)){
                listCodes.add(it.code)
            }
        }
        return listCodes
    }

    fun getFrontBalanceList(): MutableList<DataBalance>{
        return _balanceList.value?.filter {
            it.end == "Front"
        }?.toMutableList()!!
    }

    fun getBackBalanceList(): MutableList<DataBalance>{
        return _balanceList.value?.filter {
            it.end == "Back"
        }?.toMutableList()!!
    }

    fun setFrontBalanceParametersStocked(balanceParameters: DataBalance){
        BalanceRepository.setFrontBalanceParametersStocked(balanceParameters)
    }
    fun getFrontBalanceParametersStocked(): MutableLiveData<DataBalance>?{
        return BalanceRepository.getFrontBalanceParametersStocked()
    }

    fun setBackBalanceParametersStocked(balanceParameters: DataBalance){
        BalanceRepository.setBackBalanceParametersStocked(balanceParameters)
    }
    fun getBackBalanceParametersStocked(): MutableLiveData<DataBalance>?{
        return BalanceRepository.getBackBalanceParametersStocked()
    }

    fun clearStockedParameters(){
        BalanceRepository.clearStockedParameters()
    }

    fun getStockedParameters(): MutableList<DataBalance>{
        val listBalanceStockedParameters = emptyList<DataBalance>().toMutableList()
        return if(
            BalanceRepository.getFrontBalanceParametersStocked()?.value != null
            &&
            BalanceRepository.getBackBalanceParametersStocked()?.value != null
        ){
            listBalanceStockedParameters.add(BalanceRepository.getFrontBalanceParametersStocked()?.value!!)
            listBalanceStockedParameters.add(BalanceRepository.getBackBalanceParametersStocked()?.value!!)
            listBalanceStockedParameters
        } else listBalanceStockedParameters
    }


    fun addNewBalanceParameters(){
        BalanceRepository.addNewBalanceParameters(
            getStockedParameters()
        )
    }


}