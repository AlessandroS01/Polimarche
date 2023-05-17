package com.example.polimarche.data_container.damper

import androidx.lifecycle.MutableLiveData

object DamperRepository {

    private val _listDampers: MutableLiveData<MutableList<DataDamper>> =
        MutableLiveData(
            mutableListOf(
                DataDamper(1, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(2, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(10, "End", 1.3, 1.2, 4.1, 1.7),
                DataDamper(11, "End", 1.3, 1.2, 4.1, 1.7),
                DataDamper(6, "Front", 1.3, 1.2, 4.1, 1.7),
                DataDamper(7, "End", 1.3, 1.2, 4.1, 1.7),
            )
        )
    val listDampers get() = _listDampers


    private var stockedFrontDamperParameters: MutableLiveData<DataDamper>? = null
    fun setFrontDamperParametersStocked(damperParameters: DataDamper){
        if (stockedFrontDamperParameters == null) {
            stockedFrontDamperParameters = MutableLiveData()
        }
        stockedFrontDamperParameters?.value = damperParameters
    }
    fun getFrontDamperParametersStocked(): MutableLiveData<DataDamper>?{
        return stockedFrontDamperParameters
    }

    private var stockedBackDamperParameters: MutableLiveData<DataDamper>? = null
    fun setBackDamperParametersStocked(balanceParameters: DataDamper){
        if (stockedBackDamperParameters == null) {
            stockedBackDamperParameters = MutableLiveData()
        }
        stockedBackDamperParameters?.value = balanceParameters
    }
    fun getBackDamperParametersStocked(): MutableLiveData<DataDamper>?{
        return stockedBackDamperParameters
    }


    fun clearStockedParameters(){
        stockedFrontDamperParameters = null
        stockedBackDamperParameters = null
    }


}