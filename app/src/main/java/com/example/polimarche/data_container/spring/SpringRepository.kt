package com.example.polimarche.data_container.spring

import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.damper.DamperRepository
import com.example.polimarche.data_container.damper.DataDamper

object SpringRepository {

    private val _listSpring: MutableLiveData<MutableList<DataSpring>> =
        MutableLiveData(
            mutableListOf(
                DataSpring(5, "E", "Front", 1.21, "12.4", "Center"),
                DataSpring(6, "F", "End", 1.21, "12.4", "Center"),
                DataSpring(1, "A", "Front", 1.21, "12.4", "Center"),
                DataSpring(2, "B", "Front", 1.21, "12.4", "Center"),
                DataSpring(8, "H", "End", 1.21, "12.4", "Center"),
                DataSpring(9, "I", "End", 1.21, "12.4", "Center"),
            )
        )
    val listSpring get() = _listSpring

    fun addNewSpringParameters(listSpringStocked: MutableList<DataSpring>){
        listSpringStocked.forEach {
            _listSpring.value =
                _listSpring.value?.plus(it) as MutableList<DataSpring>?
        }
    }


    private var stockedFrontSpringParameters: MutableLiveData<DataSpring>? = null
    fun setFrontSpringParametersStocked(balanceParameters: DataSpring){
        if (stockedFrontSpringParameters == null) {
            stockedFrontSpringParameters = MutableLiveData()
        }
        stockedFrontSpringParameters?.value = balanceParameters
    }
    fun getFrontSpringParametersStocked(): MutableLiveData<DataSpring>?{
        return stockedFrontSpringParameters
    }

    private var stockedBackSpringParameters: MutableLiveData<DataSpring>? = null
    fun setBackSpringParametersStocked(balanceParameters: DataSpring){
        if (stockedBackSpringParameters == null) {
            stockedBackSpringParameters = MutableLiveData()
        }
        stockedBackSpringParameters?.value = balanceParameters
    }
    fun getBackSpringParametersStocked(): MutableLiveData<DataSpring>?{
        return stockedBackSpringParameters
    }


    fun clearStockedParameters(){
        stockedFrontSpringParameters = null
        stockedBackSpringParameters = null
    }
}