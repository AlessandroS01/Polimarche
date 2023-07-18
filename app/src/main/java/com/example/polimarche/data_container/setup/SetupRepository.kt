package com.example.polimarche.data_container.setup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.balance.DataBalance
import com.example.polimarche.data_container.damper.DataDamper
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.wheel.DataWheel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.CollectionReference

class SetupRepository {

    init {
        initialize()
    }

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchSetupFromFirestore()
        }
    }

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val _listSetup: MutableLiveData<MutableList<DataSetup>> = MutableLiveData()
    val listSetup get() = _listSetup

    suspend fun fetchSetupFromFirestore() {

        val setupCollection = db.collection("setup")
        val setupSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            setupCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val setupList = mutableListOf<DataSetup>() // Initialize with an empty list

        for (document in setupSnapshot) {

            val code = document.getLong("code")?.toInt() ?: 0
            val frontRightWheelRef = document.getDocumentReference("frontRightWheel")
            // await() blocca il flusso dell'esecuzione fino a quando non viene completata
            // l'operazione asincrona
            val frontRightWheelDocument = frontRightWheelRef?.get()?.await()
            // Converte i dati di un documento Firestore in un oggetto di classe DataWheel
            val frontRightWheel = frontRightWheelDocument?.toObject(DataWheel::class.java)

            val frontLeftWheelRef = document.getDocumentReference("frontLeftWheel")
            val frontLeftWheelDocument = frontLeftWheelRef?.get()?.await()
            val frontLeftWheel = frontLeftWheelDocument?.toObject(DataWheel::class.java)

            val rearRightWheelRef = document.getDocumentReference("rearRightWheel")
            val rearRightWheelDocument = rearRightWheelRef?.get()?.await()
            val rearRightWheel = rearRightWheelDocument?.toObject(DataWheel::class.java)

            val rearLeftWheelRef = document.getDocumentReference("rearLeftWheel")
            val rearLeftWheelDocument = rearLeftWheelRef?.get()?.await()
            val rearLeftWheel = rearLeftWheelDocument?.toObject(DataWheel::class.java)

            val frontDamperRef = document.getDocumentReference("frontDamper")
            val frontDamperDocument = frontDamperRef?.get()?.await()
            val frontDamper = frontDamperDocument?.toObject(DataDamper::class.java)

            val backDamperRef = document.getDocumentReference("backDamper")
            val backDamperDocument = backDamperRef?.get()?.await()
            val backDamper = backDamperDocument?.toObject(DataDamper::class.java)

            val frontSpringRef = document.getDocumentReference("frontSpring")
            val frontSpringDocument = frontSpringRef?.get()?.await()
            val frontSpring = frontSpringDocument?.toObject(DataSpring::class.java)

            val backSpringRef = document.getDocumentReference("backSpring")
            val backSpringDocument = backSpringRef?.get()?.await()
            val backSpring = backSpringDocument?.toObject(DataSpring::class.java)

            val frontBalanceRef = document.getDocumentReference("frontBalance")
            val frontBalanceDocument = frontBalanceRef?.get()?.await()
            val frontBalance = frontBalanceDocument?.toObject(DataBalance::class.java)

            val backBalanceRef = document.getDocumentReference("backBalance")
            val backBalanceDocument = backBalanceRef?.get()?.await()
            val backBalance = backBalanceDocument?.toObject(DataBalance::class.java)

            val preferredEvent = document.getString("preferredEvent") ?: ""

            val frontWingHole = document.getString("frontWingHole") ?: ""

            val notes = mutableListOf<String>() // Initialize an empty list for notes

            // Recupera eventuali note e aggiungile alla lista
            if (document.contains("notes")) {
                val notesArray = document.get("notes") as ArrayList<String>
                notes.addAll(notesArray)
            }

            // Viene creato l'oggetto setup con tutti i rispettivi oggetti e
            // se un oggetto è nullo, il blocco di codice all'interno del let non viene eseguito
            // e il risultato finale sarà null
            val setup = frontRightWheel?.let { frontRightWheel ->
                frontLeftWheel?.let { frontLeftWheel ->
                    rearRightWheel?.let { rearRightWheel ->
                        rearLeftWheel?.let { rearLeftWheel ->
                            frontDamper?.let { frontDamper ->
                                backDamper?.let { backDamper ->
                                    frontSpring?.let { frontSpring ->
                                        backSpring?.let { backSpring ->
                                            frontBalance?.let { frontBalance ->
                                                backBalance?.let { backBalance ->
                                                    DataSetup(
                                                        code,
                                                        frontRightWheel,
                                                        frontLeftWheel,
                                                        rearRightWheel,
                                                        rearLeftWheel,
                                                        frontDamper,
                                                        backDamper,
                                                        frontSpring,
                                                        backSpring,
                                                        frontBalance,
                                                        backBalance,
                                                        preferredEvent,
                                                        frontWingHole,
                                                        notes
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Verifica se l'oggetto setup non è nullo e, in caso affermativo,
            // lo aggiunge alla lista setupList
            setup?.let { setupList.add(it) }
        }

        withContext(Dispatchers.Main) {
            _listSetup.value =
                setupList // Use postValue to update MutableLiveData on the main thread
        }

    }

    fun removeSetup(setup: DataSetup) {

        val collectionRef = db.collection("setup")

        collectionRef.whereEqualTo("code", setup.code)
            .get() // querySnapshot viene ottenuta da questo get su collectionRef
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Si ottiene il primo documento dalla query snapshot
                    val documentSnapshot = querySnapshot.documents[0]
                    val documentId = documentSnapshot.id

                    collectionRef.document(documentId).delete()
                        .addOnSuccessListener {
                            // Rimuovi il setup dalla lista locale
                            _listSetup.value?.remove(setup)

                            // Aggiorna il valore di listSetup per attivare gli osservatori
                            _listSetup.value = _listSetup.value

                        }
                        .addOnFailureListener {
                        }
                } else {
                }
            }
            .addOnFailureListener { exception ->
                Log.e("SetupRepository", "Errore durante la ricerca del documento", exception)
            }

        val collectionRefSolved = db.collection("DataSolvedProblem")
        
        val queryRefSolved = collectionRefSolved.whereEqualTo("setupCode", setup.code)
        queryRefSolved.get().addOnSuccessListener { querySnapshot ->
            for (documentSnapshot in querySnapshot) {
                // Delete each document in the query results
                documentSnapshot.reference.delete()
                    .addOnSuccessListener {
                        // Document successfully deleted
                    }
                    .addOnFailureListener { exception ->
                        // An error occurred while deleting the document
                        // Handle the error accordingly
                    }
            }
        }.addOnFailureListener { exception ->
            // An error occurred while executing the query
            // Handle the error accordingly
        }


        val collectionRefOccurring = db.collection("DataOccurringProblem")
        // Query the collection based on the parameter value
        val query = collectionRefOccurring.whereEqualTo("setupCode", setup.code)

        query.get().addOnSuccessListener { querySnapshot ->
            for (documentSnapshot in querySnapshot) {
                // Delete each document in the query results
                //reference viene utilizzato per ottenere il riferimento
                // al documento corrente all'interno del ciclo for
                documentSnapshot.reference.delete()
                    .addOnSuccessListener {
                        // Document successfully deleted
                    }
                    .addOnFailureListener { exception ->
                        // An error occurred while deleting the document
                        // Handle the error accordingly
                    }
            }
        }.addOnFailureListener { exception ->
            // An error occurred while executing the query
            // Handle the error accordingly
        }
    }
}
