package com.example.polimarche.data_container.setup

import androidx.lifecycle.ViewModel
import com.example.polimarche.data_container.balance.DataBalance
import com.example.polimarche.data_container.damper.DataDamper
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.wheel.DataWheel

class SetupViewModel: ViewModel() {

    private val _setupListData: MutableList<DataSetup> =
        SetupRepository.setupList
    val setupList: MutableList<DataSetup>
        get()= _setupListData


    fun getSetupCodes(): MutableList<Int>{
        val listSetupCodes = mutableListOf<Int>()
        _setupListData.forEach {
            listSetupCodes.add(it.code)
        }
        return listSetupCodes
    }

    fun deleteSetup(setup: DataSetup){
        SetupRepository.removeSetup(setup)
    }

    fun addNewSetup(newSetup: DataSetup){
        SetupRepository.addNewSetup(newSetup)
    }

}