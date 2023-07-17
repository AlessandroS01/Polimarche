package com.example.polimarche.data_container.wheel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.balance.BalanceRepository
import com.example.polimarche.data_container.track.TracksRepository
import kotlinx.coroutines.launch

class WheelViewModel: ViewModel() {

    private val wheelRepository: WheelRepository = WheelRepository()

    init {
        viewModelScope.launch {
            wheelRepository.fetchWheelFromFirestore()
        }
    }

    fun initialize(){
        viewModelScope.launch {
            wheelRepository.fetchWheelFromFirestore()
        }
    }

    private val _listWheel: MutableLiveData<MutableList<DataWheel>> =
        wheelRepository.listWheel
    val listWheel get() = _listWheel

    private fun getCodificationList(): MutableList<String>{
        val codificationList = emptyList<String>().toMutableList()
        _listWheel.value?.forEach {
            if(! codificationList.contains(it.codification) ) {
                codificationList.add(it.codification)
            }
        }
        return codificationList
    }
    /*
    Calcola la quantità di ruote per ogni codifica e restituisce una mappa che associa
    ogni codifica alla sua quantità corrispondente
     */
    fun mapQuantityCodification(): MutableMap<String, Int>{
        val mappingCodificationQuantity = emptyMap<String, Int>().toMutableMap()
        getCodificationList().forEach {
            val codification = it
            val quantity = _listWheel.value?.filter {
                it.codification == codification
            }?.size!!
            mappingCodificationQuantity.put(codification, quantity)
        }
        return mappingCodificationQuantity
    }

    fun getFrontRightWheels(): MutableList<DataWheel> {
        return _listWheel.value?.filter {
            it.position == "Front right"
        }?.toMutableList() ?: mutableListOf()
    }

    fun getFrontLeftWheels(): MutableList<DataWheel>{
        return _listWheel.value?.filter {
            it.position == "Front left"
        }?.toMutableList() ?: mutableListOf()
    }
    fun getRearRightWheels(): MutableList<DataWheel>{
        return _listWheel.value?.filter {
            it.position == "Rear right"
        }?.toMutableList() ?: mutableListOf()
    }
    fun getRearLeftWheels(): MutableList<DataWheel>{
        return _listWheel.value?.filter {
            it.position == "Rear left"
        }?.toMutableList() ?: mutableListOf()
    }



    fun setFrontRightWheelParameters(wheelParameters: DataWheel){
        wheelRepository.setFrontRightWheelParameters(wheelParameters)
    }
    fun getFrontRightParametersStocked(): MutableLiveData<DataWheel>?{
        return wheelRepository.getFrontRightParametersStocked()
    }

    fun setFrontLeftWheelParameters(wheelParameters: DataWheel){
        wheelRepository.setFrontLeftWheelParameters(wheelParameters)
    }
    fun getFrontLeftParametersStocked(): MutableLiveData<DataWheel>?{
        return wheelRepository.getFrontLeftParametersStocked()
    }

    fun setRearRightWheelParameters(wheelParameters: DataWheel){
        wheelRepository.setRearRightWheelParameters(wheelParameters)
    }
    fun getRearRightParametersStocked(): MutableLiveData<DataWheel>?{
        return wheelRepository.getRearRightParametersStocked()
    }

    fun setRearLeftWheelParameters(wheelParameters: DataWheel){
        wheelRepository.setRearLeftWheelParameters(wheelParameters)
    }
    fun getRearLeftParametersStocked(): MutableLiveData<DataWheel>?{
        return wheelRepository.getRearLeftParametersStocked()
    }


    fun clearStockedParameters(){
        wheelRepository.clearStockedParameters()
    }

    fun getStockedParameters(): MutableList<DataWheel>{
        val listWheelStockedParameters = emptyList<DataWheel>().toMutableList()
        return if(
            wheelRepository.getFrontRightParametersStocked()?.value != null
            &&
            wheelRepository.getFrontLeftParametersStocked()?.value != null
            &&
            wheelRepository.getRearRightParametersStocked()?.value != null
            &&
            wheelRepository.getRearLeftParametersStocked()?.value != null
        ){
            listWheelStockedParameters.add(wheelRepository.getFrontRightParametersStocked()?.value !!)
            listWheelStockedParameters.add(wheelRepository.getFrontLeftParametersStocked()?.value !!)
            listWheelStockedParameters.add(wheelRepository.getRearRightParametersStocked()?.value !!)
            listWheelStockedParameters.add(wheelRepository.getRearLeftParametersStocked()?.value !!)
            listWheelStockedParameters
        } else listWheelStockedParameters
    }

    fun addNewWheelParameters(){
        wheelRepository.addNewWheelParameters(
            getStockedParameters()
        )
    }


}