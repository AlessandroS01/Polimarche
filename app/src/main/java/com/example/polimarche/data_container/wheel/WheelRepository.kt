package com.example.polimarche.data_container.wheel

import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.balance.DataBalance
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.ProblemsRepository

object WheelRepository {

    private val _listWheel: MutableLiveData<MutableList<DataWheel>> =
        MutableLiveData(
            mutableListOf(
                DataWheel(1, "Front right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(2, "Front left", "A", "1", "-1+2piastrini", "1"),
                DataWheel(3, "Rear right", "A", "1", "-1+2piastrini", "1"),
                DataWheel(4, "Rear left", "A", "1", "-1+2piastrini", "1"),
                DataWheel(5, "Front right", "B", "1", "-1+2piastrini", "1"),
                DataWheel(6, "Front left", "B", "1", "-1+2piastrini", "1"),
                DataWheel(7, "Rear right", "B", "1", "-1+2piastrini", "1"),
                DataWheel(8, "Rear left", "B", "1", "-1+2piastrini", "1"),
                DataWheel(9, "Front right", "C", "1", "-1+2piastrini", "1"),
                DataWheel(10, "Front left", "C", "1", "-1+2piastrini", "1"),
                DataWheel(11, "Rear right", "C", "1", "-1+2piastrini", "1"),
                DataWheel(12, "Rear left", "C", "1", "-1+2piastrini", "1"),
                DataWheel(13, "Front right", "D", "1", "-1+2piastrini", "1"),
                DataWheel(14, "Front left", "D", "1", "-1+2piastrini", "1"),
                DataWheel(15, "Rear right", "D", "1", "-1+2piastrini", "1"),
                DataWheel(16, "Rear left", "D", "1", "-1+2piastrini", "1"),
            )
        )
    val listWheel get() = _listWheel

    fun addNewWheelParameters(listWheelStocked: MutableList<DataWheel>){
        listWheelStocked.forEach {
            _listWheel.value =
                _listWheel.value?.plus(it) as MutableList<DataWheel>?
        }
    }


    private var stockedFrontRightWheelParameters: MutableLiveData<DataWheel>? = null
    fun setFrontRightWheelParameters(wheelParameters: DataWheel){
        if (stockedFrontRightWheelParameters == null) {
            stockedFrontRightWheelParameters = MutableLiveData()
        }
        stockedFrontRightWheelParameters?.value = wheelParameters
    }
    fun getFrontRightParametersStocked(): MutableLiveData<DataWheel>?{
        return stockedFrontRightWheelParameters
    }

    private var stockedFrontLeftWheelParameters: MutableLiveData<DataWheel>? = null
    fun setFrontLeftWheelParameters(wheelParameters: DataWheel){
        if (stockedFrontLeftWheelParameters == null) {
            stockedFrontLeftWheelParameters = MutableLiveData()
        }
        stockedFrontLeftWheelParameters?.value = wheelParameters
    }
    fun getFrontLeftParametersStocked(): MutableLiveData<DataWheel>?{
        return stockedFrontLeftWheelParameters
    }

    private var stockedRearRightWheelParameters: MutableLiveData<DataWheel>? = null
    fun setRearRightWheelParameters(wheelParameters: DataWheel){
        if (stockedRearRightWheelParameters == null) {
            stockedRearRightWheelParameters = MutableLiveData()
        }
        stockedRearRightWheelParameters?.value = wheelParameters
    }
    fun getRearRightParametersStocked(): MutableLiveData<DataWheel>?{
        return stockedRearRightWheelParameters
    }

    private var stockedRearLeftWheelParameters: MutableLiveData<DataWheel>? = null
    fun setRearLeftWheelParameters(wheelParameters: DataWheel){
        if (stockedRearLeftWheelParameters == null) {
            stockedRearLeftWheelParameters = MutableLiveData()
        }
        stockedRearLeftWheelParameters?.value = wheelParameters
    }
    fun getRearLeftParametersStocked(): MutableLiveData<DataWheel>?{
        return stockedRearLeftWheelParameters
    }


    fun clearStockedParameters(){
        stockedFrontRightWheelParameters = null
        stockedFrontLeftWheelParameters = null
        stockedRearRightWheelParameters = null
        stockedRearLeftWheelParameters = null
    }
}