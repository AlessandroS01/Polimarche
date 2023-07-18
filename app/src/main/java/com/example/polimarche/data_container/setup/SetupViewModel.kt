package com.example.polimarche.data_container.setup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.balance.DataBalance
import com.example.polimarche.data_container.damper.DataDamper
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.data_container.track.TracksRepository
import com.example.polimarche.data_container.wheel.DataWheel
import kotlinx.coroutines.launch

class SetupViewModel: ViewModel() {

    private val setupRepository: SetupRepository = SetupRepository()
    init {
        viewModelScope.launch {
            setupRepository.fetchSetupFromFirestore()
        }
    }

    fun initialize(){
        viewModelScope.launch {
            setupRepository.fetchSetupFromFirestore()
        }
    }

    private val _setupList: MutableLiveData<MutableList<DataSetup>> =
        setupRepository.listSetup
    val setupList get()= _setupList


    fun getSetupCodes(): MutableList<Int> {
        val listSetupCodes = mutableListOf<Int>()
        //Per ogni elemento setup nella lista _setupList.value aggiunge il valore del campo
        // code dell'oggetto setup alla lista listSetupCodes
        _setupList.value?.forEach { setup ->
            listSetupCodes.add(setup.code)
        }
        return listSetupCodes
    }

    fun deleteSetup(setup: DataSetup){
        setupRepository.removeSetup(setup)
    }

}