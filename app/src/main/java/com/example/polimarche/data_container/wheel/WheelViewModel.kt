package com.example.polimarche.data_container.wheel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polimarche.data_container.balance.BalanceRepository

class WheelViewModel: ViewModel() {

    private val _listWheel: MutableLiveData<MutableList<DataWheel>> =
        WheelRepository.listWheel
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
    Used to assign to every codification the wheels quantity having the same codification
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

    fun getFrontRightWheels(): MutableList<DataWheel>{
        return _listWheel.value?.filter {
            it.position == "Front right"
        }?.toMutableList()!!
    }
    fun getFrontLeftWheels(): MutableList<DataWheel>{
        return _listWheel.value?.filter {
            it.position == "Front left"
        }?.toMutableList()!!
    }
    fun getRearRightWheels(): MutableList<DataWheel>{
        return _listWheel.value?.filter {
            it.position == "Rear right"
        }?.toMutableList()!!
    }
    fun getRearLeftWheels(): MutableList<DataWheel>{
        return _listWheel.value?.filter {
            it.position == "Rear left"
        }?.toMutableList()!!
    }



    fun setFrontRightWheelParameters(wheelParameters: DataWheel){
        WheelRepository.setFrontRightWheelParameters(wheelParameters)
    }
    fun getFrontRightParametersStocked(): MutableLiveData<DataWheel>?{
        return WheelRepository.getFrontRightParametersStocked()
    }

    fun setFrontLeftWheelParameters(wheelParameters: DataWheel){
        WheelRepository.setFrontLeftWheelParameters(wheelParameters)
    }
    fun getFrontLeftParametersStocked(): MutableLiveData<DataWheel>?{
        return WheelRepository.getFrontLeftParametersStocked()
    }

    fun setRearRightWheelParameters(wheelParameters: DataWheel){
        WheelRepository.setRearRightWheelParameters(wheelParameters)
    }
    fun getRearRightParametersStocked(): MutableLiveData<DataWheel>?{
        return WheelRepository.getRearRightParametersStocked()
    }

    fun setRearLeftWheelParameters(wheelParameters: DataWheel){
        WheelRepository.setRearLeftWheelParameters(wheelParameters)
    }
    fun getRearLeftParametersStocked(): MutableLiveData<DataWheel>?{
        return WheelRepository.getRearLeftParametersStocked()
    }


    fun clearStockedParameters(){
        WheelRepository.clearStockedParameters()
    }

    fun getStockedParameters(): MutableList<DataWheel>{
        val listWheelStockedParameters = emptyList<DataWheel>().toMutableList()
        return if(
            WheelRepository.getFrontRightParametersStocked()?.value != null
            &&
            WheelRepository.getFrontLeftParametersStocked()?.value != null
            &&
            WheelRepository.getRearRightParametersStocked()?.value != null
            &&
            WheelRepository.getRearLeftParametersStocked()?.value != null
        ){
            listWheelStockedParameters.add(WheelRepository.getFrontRightParametersStocked()?.value !!)
            listWheelStockedParameters.add(WheelRepository.getFrontLeftParametersStocked()?.value !!)
            listWheelStockedParameters.add(WheelRepository.getRearRightParametersStocked()?.value !!)
            listWheelStockedParameters.add(WheelRepository.getRearLeftParametersStocked()?.value !!)
            listWheelStockedParameters
        } else listWheelStockedParameters
    }
}