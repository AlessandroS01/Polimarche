package com.example.polimarche.data_container.problem

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.problem.occurring_problem.DataOccurringProblem
import com.example.polimarche.data_container.problem.solved_problem.DataSolvedProblem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/*
Class that will contain all the data from which the various viewModel
referring to any kind of DataProblem, like DataSolvedProblem or even DataProblem,
can get and set their list values.
 */
class ProblemsRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchProblemFromFirestore()
            fetchOccurringProblemFromFirestore()
            fetchSolvedProblemFromFirestore()
        }
    }

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchProblemFromFirestore()
            fetchOccurringProblemFromFirestore()
            fetchSolvedProblemFromFirestore()
        }
    }




    private val db = FirebaseFirestore.getInstance()


    /*
    Contains the list of all the problems faced by all the different setups
     */
    private val _listProblemsData: MutableLiveData<MutableList<DataProblem>> =
        MutableLiveData()
    val listProblems get()= _listProblemsData

    /*
    Contains the list of setups that face the problem clicked
     */
    private val _listOccurringProblemsData: MutableLiveData<MutableList<DataOccurringProblem>> =
        MutableLiveData(
            mutableListOf(
            )
        )
    val listOccurringProblems get()= _listOccurringProblemsData

    /*
    Contains the list of setups that solved the problem clicked
     */
    private val _listSolvedProblemsData: MutableLiveData<MutableList<DataSolvedProblem>> =
        MutableLiveData(
            emptyList<DataSolvedProblem>().toMutableList()
        )
    val listSolvedProblems get() = _listSolvedProblemsData
    suspend fun fetchProblemFromFirestore() {
        val problemCollection = db.collection("DataProblem")

        val problemSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            problemCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val problemList = mutableListOf<DataProblem>()
        val problemIdList = mutableListOf<String>()

        for (document in problemSnapshot.documents) {
            val documentId = document.id // Get the document ID
            problemIdList.add(documentId)

            val code = document.getLong("code")?.toInt() ?: 0
            val description = document.getString("description") ?: ""
            val expansion = document.getBoolean("expansion") ?: false

            val dataProblem = DataProblem(
                code,
                description,
                expansion
            )
            problemList.add(dataProblem)
        }

        withContext(Dispatchers.Main) {
            _listProblemsData.value = problemList
        }
    }

    suspend fun fetchOccurringProblemFromFirestore() {
        val occurringProblemCollection = db.collection("DataOccurringProblem")

        val occurringProblemSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            occurringProblemCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val occurringProblemList = mutableListOf<DataOccurringProblem>()
        val occurringProblemIdList = mutableListOf<String>()

        for (document in occurringProblemSnapshot.documents) {

            val documentId = document.id // Get the document ID
            occurringProblemIdList.add(documentId)

            val problemCode = document.getLong("problemCode")?.toInt() ?: 0
            val setupCode = document.getLong("setupCode")?.toInt() ?: 0
            val description = document.getString("description") ?: ""
            val expansion = document.getBoolean("expansion") ?: false

            val dataOccurringProblem = DataOccurringProblem(
                problemCode,
                setupCode,
                description,
                expansion
            )
            occurringProblemList.add(dataOccurringProblem)
        }

        withContext(Dispatchers.Main) {
            _listOccurringProblemsData.value = occurringProblemList
        }
    }

    suspend fun fetchSolvedProblemFromFirestore() {
        val solvedProblemCollection = db.collection("DataSolvedProblem")

        val solvedProblemSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            solvedProblemCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val solvedProblemList = mutableListOf<DataSolvedProblem>()
        val solvedProblemIdList = mutableListOf<String>()

        for (document in solvedProblemSnapshot.documents) {
            val documentId = document.id // Get the document ID
            solvedProblemIdList.add(documentId)

            val problemCode = document.getLong("problemCode")?.toInt() ?: 0
            val setupCode = document.getLong("setupCode")?.toInt() ?: 0
            val description = document.getString("description") ?: ""
            val expansion = document.getBoolean("expansion") ?: false

            val dataSolvedProblem = DataSolvedProblem(
                problemCode,
                setupCode,
                description,
                expansion
            )
            solvedProblemList.add(dataSolvedProblem)
            Log.d("PROBLEMREPO","dataSolvedProblem:$dataSolvedProblem")
        }

        withContext(Dispatchers.Main) {
            _listSolvedProblemsData.value = solvedProblemList
        }
    }



    /*
    It adds a new problem to _listProblemsData when the user
    decides to create a new problem.
     */
    fun addNewProblem(newProblem: DataProblem) {
        val collectionRef = db.collection("DataProblem")
        val problemRef = collectionRef.document()

        problemRef.set(newProblem)
            .addOnSuccessListener {
                val updatedList = _listProblemsData.value ?: mutableListOf() // Get the current list or initialize an empty list

                updatedList.add(newProblem) // Add the new track to the updated list
                _listProblemsData.postValue(updatedList) // Use postValue to update the MutableLiveData asynchronously
                println(_listProblemsData.value)
                Log.e("ProblemsRepository", "New problem added successfully")

                // Aggiorna la lista locale o esegui altre azioni necessarie
            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to add new problem", exception)
            }
    }


    /*
    It firstly removes the item from the list of OccurringProblems and then
    creates a new object of DataSolvedProblem to add to the list _listSolvedProblemsData.
    It calls a method in which the new element is formally added.
     */
    fun removeItemFromOccurringProblem(
        occurredProblem: DataOccurringProblem,
        description: String
    ) {
        val collectionRef = db.collection("DataOccurringProblem")
        val collectionRefSolvedProblem = db.collection("DataSolvedProblem")

        // Rimuovi l'elemento dalla lista _listOccurringProblemsData
        _listOccurringProblemsData.value?.remove(occurredProblem)

        // Crea un nuovo oggetto DataSolvedProblem
        val newSolvedProblem = DataSolvedProblem(
            occurredProblem.problemCode,
            occurredProblem.setupCode,
            description
        )

        // Aggiungi il nuovo problema risolto alla collezione DataSolvedProblem
        collectionRefSolvedProblem.add(newSolvedProblem)
            .addOnSuccessListener {
                Log.e("ProblemsRepository", "New solved problem added successfully")

                // Aggiorna la lista locale o esegui altre azioni necessarie
            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to add new solved problem", exception)
            }
    }

    private fun addNewSolvedProblem(newSolvedProblem: DataSolvedProblem){
        val collectionRef = db.collection("DataSolvedProblem")
        val newSolvedProblemRef = collectionRef.document()

        newSolvedProblemRef.set(newSolvedProblem)
            .addOnSuccessListener {
                val updatedList = _listSolvedProblemsData.value ?: mutableListOf() // Get the current list or initialize an empty list

                updatedList.add(newSolvedProblem) // Add the new track to the updated list
                _listSolvedProblemsData.postValue(updatedList) // Use postValue to update the MutableLiveData asynchronously
                println(_listSolvedProblemsData.value)
                Log.e("ProblemsRepository", "New problem added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to add new problem", exception)
            }
    }


    /*
    It firstly removes the item from the list of SolvedProblems
    and then creates a new object of of DataOccurringProblem to add
    to the list _listOccurringProblemsData.
    It calls a method in which the new element is formally added.
     */
    fun removeItemFromSolvedProblem(
        solvedProblem: DataSolvedProblem,
        description: String
    ) {
        val collectionRef = db.collection("DataSolvedProblem")
        val collectionRefOccurringProblem = db.collection("DataOccurringProblem")

        // Rimuovi l'elemento dalla lista _listSolvedProblemsData
        _listSolvedProblemsData.value?.remove(solvedProblem)

        // Crea un nuovo oggetto DataOccurringProblem
        val newOccurringProblem = DataOccurringProblem(
            solvedProblem.problemCode,
            solvedProblem.setupCode,
            description
        )

        // Aggiungi il nuovo problema in corso alla collezione DataOccurringProblem
        collectionRefOccurringProblem.add(newOccurringProblem)
            .addOnSuccessListener {
                Log.e("ProblemsRepository", "New occurring problem added successfully")

                // Aggiorna la lista locale o esegui altre azioni necessarie
            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to add new occurring problem", exception)
            }
    }

    fun addNewOccurringProblem(newOccurringProblem: DataOccurringProblem){
        val collectionRef = db.collection("DataOccurringProblem")
        val ProblemRef = collectionRef.document()

        ProblemRef.set(newOccurringProblem)
            .addOnSuccessListener {
                val updatedList = _listOccurringProblemsData.value ?: mutableListOf() // Get the current list or initialize an empty list

                updatedList.add(newOccurringProblem) // Add the new track to the updated list
                _listOccurringProblemsData.postValue(updatedList) // Use postValue to update the MutableLiveData asynchronously
                println(_listOccurringProblemsData.value)
                Log.e("ProblemsRepository", "New problem added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to add new problem", exception)
            }
    }





}