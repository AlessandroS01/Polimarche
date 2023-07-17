package com.example.polimarche.data_container.balance

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

class BalanceRepository {
    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchBalanceFromFirestore()
        }
    }

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchBalanceFromFirestore()
        }
    }

    private val db = FirebaseFirestore.getInstance()

    private val _balanceList: MutableLiveData<MutableList<DataBalance>> =
        MutableLiveData()
    val balanceList get() = _balanceList

    suspend fun fetchBalanceFromFirestore() {
        val balanceCollection = db.collection("DataBalance")

        val balanceSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            balanceCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val balanceList = mutableListOf<DataBalance>()
        val balanceIdList = mutableListOf<String>()

        for (document in balanceSnapshot.documents) {
            val documentId = document.id // Get the document ID
            balanceIdList.add(documentId)

            val code = document.getLong("code")?.toInt() ?: 0
            val end = document.getString("end") ?: ""
            val brake = document.getDouble("brake") ?: 0.0
            val weight = document.getDouble("weight") ?: 0.0
            val expansion = document.getBoolean("expansion") ?: false

            val dataBalance = DataBalance(
                code,
                end,
                brake,
                weight,
                expansion
            )
            balanceList.add(dataBalance)
        }

        withContext(Dispatchers.Main) {
            _balanceList.value = balanceList
        }
    }


fun addNewBalanceParameters(listBalanceStocked: MutableList<DataBalance>) {
    listBalanceStocked.forEach {
        _balanceList.value =
            _balanceList.value?.plus(it) as MutableList<DataBalance>?
    }
}


private var stockedFrontBalanceParameters: MutableLiveData<DataBalance>? = null
fun setFrontBalanceParametersStocked(balanceParameters: DataBalance){
    if (stockedFrontBalanceParameters == null) {
        stockedFrontBalanceParameters = MutableLiveData()
    }
    stockedFrontBalanceParameters?.value = balanceParameters
}
fun getFrontBalanceParametersStocked(): MutableLiveData<DataBalance>?{
    return stockedFrontBalanceParameters
}

private var stockedBackBalanceParameters: MutableLiveData<DataBalance>? = null
fun setBackBalanceParametersStocked(balanceParameters: DataBalance){
    if (stockedBackBalanceParameters == null) {
        stockedBackBalanceParameters = MutableLiveData()
    }
    stockedBackBalanceParameters?.value = balanceParameters
}
fun getBackBalanceParametersStocked(): MutableLiveData<DataBalance>?{
    return stockedBackBalanceParameters
}


fun clearStockedParameters(){
    stockedFrontBalanceParameters = null
    stockedBackBalanceParameters = null
}


}