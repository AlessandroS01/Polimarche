package com.example.polimarche.data_container.damper

import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.data_container.wheel.DataWheel
import com.example.polimarche.data_container.wheel.WheelRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DamperRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchDamperFromFirestore()
        }
    }

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchDamperFromFirestore()
        }
    }

    private val db = FirebaseFirestore.getInstance()

    private val _listDampers: MutableLiveData<MutableList<DataDamper>> =
        MutableLiveData()
    val listDampers get() = _listDampers

    suspend fun fetchDamperFromFirestore() {
        val damperCollection = db.collection("DataDamper")

        val damperSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            damperCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val damperList = mutableListOf<DataDamper>()
        val damperIdList = mutableListOf<String>()

        for (document in damperSnapshot.documents) {
            val documentId = document.id // Get the document ID
            damperIdList.add(documentId)

            val code = document.getLong("code")?.toInt() ?: 0
            val end = document.getString("end") ?: ""
            val hsr = document.getDouble("hsr") ?: 0.0
            val hsc = document.getDouble("hsc") ?: 0.0
            val lsr = document.getDouble("lsr") ?: 0.0
            val lsc = document.getDouble("lsc") ?: 0.0
            val expansion = document.getBoolean("expansion") ?: false

            val dataDamper = DataDamper(
                code,
                end,
                hsr,
                hsc,
                lsr,
                lsc,
                expansion
            )
            damperList.add(dataDamper)
        }

        withContext(Dispatchers.Main) {
            _listDampers.value = damperList
        }
    }


    fun addNewDamperParameters(listDamperStocked: MutableList<DataDamper>){
        listDamperStocked.forEach {
            _listDampers.value =
                _listDampers.value?.plus(it) as MutableList<DataDamper>?
        }
    }


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