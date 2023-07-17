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

class ProblemsRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchProblemFromFirestore()
            fetchOccurringProblemFromFirestore()
            fetchSolvedProblemFromFirestore()
        }
    }

    fun initialize() {
        /*CoroutineScope(Dispatchers.IO).launch {
            fetchProblemFromFirestore()
            fetchOccurringProblemFromFirestore()
            fetchSolvedProblemFromFirestore()
        }*/
    }

    private val db = FirebaseFirestore.getInstance()

    /*
    Contiene l'elenco di tutti i problems
     */
    private val _listProblemsData: MutableLiveData<MutableList<DataProblem>> =
        MutableLiveData()
    val listProblems get()= _listProblemsData

    /*
    Contiene l'elenco degli occurringProblem
     */
    private val _listOccurringProblemsData: MutableLiveData<MutableList<DataOccurringProblem>> =
        MutableLiveData(
            mutableListOf(
            )
        )
    val listOccurringProblems get()= _listOccurringProblemsData

    /*
    Contiene l'elenco degli solvedProblem
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

        for (document in occurringProblemSnapshot) {

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

        for (document in solvedProblemSnapshot) {
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
        }

        withContext(Dispatchers.Main) {
            _listSolvedProblemsData.value = solvedProblemList
        }
    }



    /*
   Aggiunge un nuovo problema a _listProblemsData quando l'utente
    decide di creare un nuovo problema e alla collection DataProblem.
     */
    fun addNewProblem(newProblem: DataProblem) {
        val collectionRef = db.collection("DataProblem")
        val problemRef = collectionRef.document()

        problemRef.set(newProblem)
            .addOnSuccessListener {
                val updatedList = _listProblemsData.value ?: mutableListOf() // Get the current list or initialize an empty list

                updatedList.add(newProblem) // Add the new problem to the updated list
                _listProblemsData.postValue(updatedList) // Use postValue to update the MutableLiveData asynchronously
                println(_listProblemsData.value)
                Log.e("ProblemsRepository", "New problem added successfully")

            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to add new problem", exception)
            }
    }


    /*
    Rimuove l'elemento dall'elenco di OccurringProblems e poi
    crea un nuovo oggetto di DataSolvedProblem da aggiungere _listSolvedProblemsData.
     */
    suspend fun removeItemFromOccurringProblem(
        occurredProblem: DataOccurringProblem,
        description: String
    ) {
        val collectionRef = db.collection("DataOccurringProblem")
        val collectionRefSolvedProblem = db.collection("DataSolvedProblem")

        // Crea una query per trovare il documento corrispondente a occurredProblem
        collectionRef
            .whereEqualTo("problemCode", occurredProblem.problemCode)
            .whereEqualTo("setupCode", occurredProblem.setupCode)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val documentId = document.id

                    // Rimuovi l'elemento dalla collezione DataOccurringProblem
                    collectionRef.document(documentId)
                        .delete()
                        .addOnSuccessListener {
                            Log.e("ProblemsRepository", "Problem removed successfully")

                            // Aggiungi il nuovo problema risolto alla collezione DataSolvedProblem
                            val newSolvedProblem = DataSolvedProblem(
                                occurredProblem.problemCode,
                                occurredProblem.setupCode,
                                description
                            )

                            addNewSolvedProblem(newSolvedProblem)
                        }
                        .addOnFailureListener { exception ->
                            Log.e("ProblemsRepository", "Failed to remove problem", exception)
                        }
                } else {
                    Log.e("ProblemsRepository", "Problem not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to query problem", exception)
            }
    }

    // Aggiunge un nuovo SolvedProblem passato in ingresso nella collection DataSolvedProblem
    fun addNewSolvedProblem(newSolvedProblem: DataSolvedProblem){
        val collectionRef = db.collection("DataSolvedProblem")
        val newSolvedProblemRef = collectionRef.document()

        newSolvedProblemRef.set(newSolvedProblem)
            .addOnSuccessListener {
                val updatedList = _listSolvedProblemsData.value ?: mutableListOf() // Get the current list or initialize an empty list

                updatedList.add(newSolvedProblem) // Add the new track to the updated list
                _listSolvedProblemsData.postValue(updatedList)
                listSolvedProblems.value = updatedList// Use postValue to update the MutableLiveData asynchronously
                println(_listSolvedProblemsData.value)
                Log.e("ProblemsRepository", "New problem added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to add new problem", exception)
            }
    }


    /*
    Rimuove l'elemento dall'elenco _listSolvedProblemsData
    e crea un nuovo oggetto di DataOccurringProblem da aggiungere
    all'elenco _listOccurringProblemsData.
     */
    suspend fun removeItemFromSolvedProblem(
        solvedProblem: DataSolvedProblem,
        description: String
    ) {
        val collectionRef = db.collection("DataSolvedProblem")
        val collectionRefOccurringProblem = db.collection("DataOccurringProblem")

        // Crea una query per trovare il documento corrispondente a solvedProblem
        collectionRef
            .whereEqualTo("problemCode", solvedProblem.problemCode)
            .whereEqualTo("setupCode", solvedProblem.setupCode)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val documentId = document.id

                    // Rimuovi l'elemento dalla collezione DataSolvedProblem
                    collectionRef.document(documentId)
                        .delete()
                        .addOnSuccessListener {
                            Log.e("ProblemsRepository", "Solved problem removed successfully")
                            // Aggiungi il nuovo problema in corso alla collezione DataOccurringProblem
                            val newOccurringProblem = DataOccurringProblem(
                                solvedProblem.problemCode,
                                solvedProblem.setupCode,
                                description
                            )

                            addNewOccurringProblem(newOccurringProblem)
                        }
                        .addOnFailureListener { exception ->
                            Log.e("ProblemsRepository", "Failed to remove solved problem", exception)
                        }
                } else {
                    Log.e("ProblemsRepository", "Solved problem not found")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to query solved problem", exception)
            }
    }

    // Aggiunge un nuovo OccurringProblem passato in ingresso nella collection DataOccurringProblem
    fun addNewOccurringProblem(newOccurringProblem: DataOccurringProblem){
        val collectionRef = db.collection("DataOccurringProblem")
        val ProblemRef = collectionRef.document()

        ProblemRef.set(newOccurringProblem)
            .addOnSuccessListener {
                val updatedList = _listOccurringProblemsData.value ?: mutableListOf() // Get the current list or initialize an empty list

                updatedList.add(newOccurringProblem) // Add the new track to the updated list
                _listOccurringProblemsData.postValue(updatedList)
                listOccurringProblems.value = updatedList// Use postValue to update the MutableLiveData asynchronously
                println(_listOccurringProblemsData.value)
                Log.e("ProblemsRepository", "New problem added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("ProblemsRepository", "Failed to add new problem", exception)
            }
    }





}