package com.example.polimarche.data_container.spring

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.track.TracksRepository
import com.example.polimarche.data_container.wheel.WheelRepository
import kotlinx.coroutines.launch

class SpringViewModel: ViewModel() {

    private val springRepository: SpringRepository = SpringRepository()

    init {
        viewModelScope.launch {
            springRepository.fetchSpringFromFirestore()
        }
    }

    fun initialize(){
        viewModelScope.launch {
            springRepository.fetchSpringFromFirestore()
        }
    }
    private val _listSpring: MutableLiveData<MutableList<DataSpring>> =
        springRepository.listSpring
    val listSpring get() = _listSpring

    fun getCodificationList(): MutableList<String>{
        val codificationList = emptyList<String>().toMutableList()
        _listSpring.value?.forEach {
            if(! codificationList.contains(it.codification) ) {
                codificationList.add(it.codification)
            }
        }
        return codificationList
    }

    fun getFrontSpringList(): MutableList<DataSpring>{
        return _listSpring.value?.filter {
            it.end == "Front"
        }?.toMutableList()!!
    }

    fun getBackSpringList(): MutableList<DataSpring>{
        return _listSpring.value?.filter {
            it.end == "End"
        }?.toMutableList()!!
    }


    fun setFrontSpringParametersStocked(springParameters: DataSpring){
        springRepository.setFrontSpringParametersStocked(springParameters)
    }
    fun getFrontSpringParametersStocked(): MutableLiveData<DataSpring>?{
        return springRepository.getFrontSpringParametersStocked()
    }

    fun setBackSpringParametersStocked(springParameters: DataSpring){
        springRepository.setBackSpringParametersStocked(springParameters)
    }
    fun getBackSpringParametersStocked(): MutableLiveData<DataSpring>?{
        return springRepository.getBackSpringParametersStocked()
    }

    fun clearStockedParameters(){
        springRepository.clearStockedParameters()
    }

    fun getStockedParameters(): MutableList<DataSpring>{
        val listSpringStockedParameters = emptyList<DataSpring>().toMutableList()
        return if(
            springRepository.getFrontSpringParametersStocked()?.value != null
            &&
            springRepository.getBackSpringParametersStocked()?.value != null
        ){
            listSpringStockedParameters.add(springRepository.getFrontSpringParametersStocked()?.value!!)
            listSpringStockedParameters.add(springRepository.getBackSpringParametersStocked()?.value!!)
            listSpringStockedParameters
        } else listSpringStockedParameters
    }


    fun addNewSpringParameters(){
        springRepository.addNewSpringParameters(
            getStockedParameters()
        )
    }
}