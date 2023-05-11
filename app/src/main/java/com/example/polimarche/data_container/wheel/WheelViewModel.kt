package com.example.polimarche.data_container.wheel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

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
}