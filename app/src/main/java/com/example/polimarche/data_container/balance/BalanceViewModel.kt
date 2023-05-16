package com.example.polimarche.data_container.balance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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

    fun setFrontBalanceParameters(balanceParameters: DataBalance){
        BalanceRepository.setFrontBalanceParameters(balanceParameters)
    }
    fun getFrontBalanceParameters(): MutableLiveData<DataBalance>?{
        return BalanceRepository.getFrontBalanceParameters()
    }

    fun setBackBalanceParameters(balanceParameters: DataBalance){
        BalanceRepository.setBackBalanceParameters(balanceParameters)
    }
    fun getBackBalanceParameters(): MutableLiveData<DataBalance>?{
        return BalanceRepository.getBackBalanceParameters()
    }



}