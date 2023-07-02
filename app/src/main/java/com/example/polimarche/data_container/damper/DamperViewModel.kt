package com.example.polimarche.data_container.damper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.track.TracksRepository
import kotlinx.coroutines.launch

class DamperViewModel: ViewModel() {

    private val damperRepository: DamperRepository = DamperRepository()

    init {
        viewModelScope.launch {
            damperRepository.fetchDamperFromFirestore()
        }
    }

    fun initialize(){
        viewModelScope.launch {
            damperRepository.fetchDamperFromFirestore()
        }
    }

    private val _listDampers: MutableLiveData<MutableList<DataDamper>> =
        damperRepository.listDampers
    val listDampers get() = _listDampers

    /*
    Returns a list of all the different damper code
     */
    fun getDampersCode(): MutableList<Int>{
        val listDamperCode: MutableList<Int> = emptyList<Int>().toMutableList()
        _listDampers.value?.forEach {
            if(!listDamperCode.contains(it.code)){
                listDamperCode.add(it.code)
            }
        }
        return listDamperCode
    }

    /*
    Returns a list of all the different damper settings for front dampers.
     */
    fun getFrontDampers(): MutableList<DataDamper>{
        return _listDampers.value?.filter {
            it.end == "Front"
        }?.toMutableList()!!
    }

    /*
    Returns a list of all the different damper settings for back dampers.
     */
    fun getEndDampers(): MutableList<DataDamper>{
        return _listDampers.value?.filter {
            it.end == "End"
        }?.toMutableList()!!
    }


    fun setFrontDamperParametersStocked(damperParameters: DataDamper){
        damperRepository.setFrontDamperParametersStocked(damperParameters)
    }
    fun getFrontDamperParametersStocked(): MutableLiveData<DataDamper>?{
        return damperRepository.getFrontDamperParametersStocked()
    }

    fun setBackDamperParametersStocked(damperParameters: DataDamper){
        damperRepository.setBackDamperParametersStocked(damperParameters)
    }
    fun getBackDamperParametersStocked(): MutableLiveData<DataDamper>?{
        return damperRepository.getBackDamperParametersStocked()
    }

    fun clearStockedParameters(){
        damperRepository.clearStockedParameters()
    }

    fun getStockedParameters(): MutableList<DataDamper>{
        val listBalanceStockedParameters = emptyList<DataDamper>().toMutableList()
        return if(
            damperRepository.getFrontDamperParametersStocked()?.value != null
            &&
            damperRepository.getBackDamperParametersStocked()?.value != null
        ){
            listBalanceStockedParameters.add(damperRepository.getFrontDamperParametersStocked()?.value!!)
            listBalanceStockedParameters.add(damperRepository.getBackDamperParametersStocked()?.value!!)
            listBalanceStockedParameters
        } else listBalanceStockedParameters
    }

    fun addNewDamperParameters(){
        damperRepository.addNewDamperParameters(
            getStockedParameters()
        )
    }
}