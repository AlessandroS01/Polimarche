package com.example.polimarche.data_container.damper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DamperViewModel: ViewModel() {

    private val _listDampers: MutableLiveData<MutableList<DataDamper>> =
        DamperRepository.listDampers
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
}