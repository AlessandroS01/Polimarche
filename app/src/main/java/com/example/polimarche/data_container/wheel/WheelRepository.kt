package com.example.polimarche.data_container.wheel

import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.balance.DataBalance
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.ProblemsRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WheelRepository {

    init {
        initialize()
    }

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchWheelFromFirestore()
        }
    }

    private val db = FirebaseFirestore.getInstance()

    private val _listWheel: MutableLiveData<MutableList<DataWheel>> = MutableLiveData()
    val listWheel get() = _listWheel


    suspend fun fetchWheelFromFirestore() {
        val wheelCollection = db.collection("DataWheel")

        val wheelSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            wheelCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val dataList = mutableListOf<DataWheel>()
        val dataIdList = mutableListOf<String>()

        for (document in wheelSnapshot.documents) {
            val documentId = document.id // Ottieni l'ID del documento
            dataIdList.add(documentId)

            val camber = document.getString("camber").toString()!!
            val code = document.getLong("code")?.toInt()!!
            val codification = document.getString("codification")!!
            val expansion = document.getBoolean("expansion")!!
            val position = document.getString("position")!!
            val pressure = document.getString("pressure")!!
            val toe = document.getString("toe")!!

            val dataWheel = DataWheel(
                code,
                position,
                codification,
                pressure,
                camber,
                toe,
                expansion,
            )
            dataList.add(dataWheel)
        }
        withContext(Dispatchers.Main) {
            _listWheel.value = dataList // Utilizza postValue per aggiornare MutableLiveData nel thread principale
        }
    }




    fun addNewWheelParameters(listWheelStocked: MutableList<DataWheel>){
        listWheelStocked.forEach {
            _listWheel.value?.addAll(listWheelStocked) as MutableList<DataWheel>?
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

