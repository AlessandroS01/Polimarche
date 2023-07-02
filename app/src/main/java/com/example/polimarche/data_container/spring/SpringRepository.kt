package com.example.polimarche.data_container.spring

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SpringRepository {

    private val db = FirebaseFirestore.getInstance()

    private val _listSpring: MutableLiveData<MutableList<DataSpring>> = MutableLiveData()
    val listSpring get() = _listSpring

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchSpringFromFirestore()
        }
    }

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchSpringFromFirestore()
        }
    }

    suspend fun fetchSpringFromFirestore() {
        val springCollection = db.collection("DataSpring")

        val springSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            springCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val dataList = mutableListOf<DataSpring>()
        val springIdList = mutableListOf<String>()

        for (document in springSnapshot.documents) {
            val documentId = document.id // Get the document ID
            springIdList.add(documentId)

            val code = document.getLong("code")?.toInt() ?: 0
            val codification = document.getString("codification") ?: ""
            val end = document.getString("end") ?: ""
            val height = document.getDouble("height") ?: 0.0
            val arbStiffness = document.getString("arb_stiffness") ?: ""
            val arbPosition = document.getString("arb_position") ?: ""
            val expansion = document.getBoolean("expansion") ?: false

            val dataSpring = DataSpring(
                code,
                codification,
                end,
                height,
                arbStiffness,
                arbPosition,
                expansion
            )
            dataList.add(dataSpring)
        }

        withContext(Dispatchers.Main) {
            _listSpring.value = dataList
        }
    }

    fun addNewSpringParameters(listSpringStocked: MutableList<DataSpring>) {
        _listSpring.value?.addAll(listSpringStocked)
    }

    private var stockedFrontSpringParameters: MutableLiveData<DataSpring>? = null
    fun setFrontSpringParametersStocked(springParameters: DataSpring) {
        if (stockedFrontSpringParameters == null) {
            stockedFrontSpringParameters = MutableLiveData()
        }
        stockedFrontSpringParameters?.value = springParameters
    }

    fun getFrontSpringParametersStocked(): MutableLiveData<DataSpring>? {
        return stockedFrontSpringParameters
    }

    private var stockedBackSpringParameters: MutableLiveData<DataSpring>? = null
    fun setBackSpringParametersStocked(springParameters: DataSpring) {
        if (stockedBackSpringParameters == null) {
            stockedBackSpringParameters = MutableLiveData()
        }
        stockedBackSpringParameters?.value = springParameters
    }

    fun getBackSpringParametersStocked(): MutableLiveData<DataSpring>? {
        return stockedBackSpringParameters
    }

    fun clearStockedParameters() {
        stockedFrontSpringParameters = null
        stockedBackSpringParameters = null
    }
}
