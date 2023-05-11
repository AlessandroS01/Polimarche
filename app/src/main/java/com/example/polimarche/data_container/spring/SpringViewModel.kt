package com.example.polimarche.data_container.spring

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SpringViewModel: ViewModel() {

    private val _listSpring: MutableLiveData<MutableList<DataSpring>> =
        SpringRepository.listSpring
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

}