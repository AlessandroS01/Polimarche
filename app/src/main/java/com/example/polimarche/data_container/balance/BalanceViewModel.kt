package com.example.polimarche.data_container.balance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.spring.SpringRepository
import com.example.polimarche.data_container.track.TracksRepository
import kotlinx.coroutines.launch

class BalanceViewModel: ViewModel() {

    private val balanceRepository: BalanceRepository = BalanceRepository()

    init {
        initialize()
    }

    fun initialize(){
        viewModelScope.launch {
            balanceRepository.fetchBalanceFromFirestore()
        }
    }

    private val _balanceList: MutableLiveData<MutableList<DataBalance>> =
        balanceRepository.balanceList
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
        }?.toMutableList()?: mutableListOf()
    }

    fun getBackBalanceList(): MutableList<DataBalance>{
        return _balanceList.value?.filter {
            it.end == "Back"
        }?.toMutableList()?: mutableListOf()
    }


    fun setFrontBalanceParametersStocked(balanceParameters: DataBalance){
        balanceRepository.setFrontBalanceParametersStocked(balanceParameters)
    }
    fun getFrontBalanceParametersStocked(): MutableLiveData<DataBalance>?{
        return balanceRepository.getFrontBalanceParametersStocked()
    }

    fun setBackBalanceParametersStocked(balanceParameters: DataBalance){
        balanceRepository.setBackBalanceParametersStocked(balanceParameters)
    }
    fun getBackBalanceParametersStocked(): MutableLiveData<DataBalance>?{
        return balanceRepository.getBackBalanceParametersStocked()
    }

    fun clearStockedParameters(){
        balanceRepository.clearStockedParameters()
    }

    fun getStockedParameters(): MutableList<DataBalance>{
        val listBalanceStockedParameters = emptyList<DataBalance>().toMutableList()
        return if(
            balanceRepository.getFrontBalanceParametersStocked()?.value != null
            &&
            balanceRepository.getBackBalanceParametersStocked()?.value != null
        ){
            listBalanceStockedParameters.add(balanceRepository.getFrontBalanceParametersStocked()?.value!!)
            listBalanceStockedParameters.add(balanceRepository.getBackBalanceParametersStocked()?.value!!)
            listBalanceStockedParameters
        } else listBalanceStockedParameters
    }


    fun addNewBalanceParameters(){
        balanceRepository.addNewBalanceParameters(
            getStockedParameters()
        )
    }


}