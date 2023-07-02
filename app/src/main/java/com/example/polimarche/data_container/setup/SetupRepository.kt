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

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchSetupFromFirestore()
        }
    }

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchSetupFromFirestore()
        }
    }

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _listSetup: MutableLiveData<MutableList<DataSetup>> = MutableLiveData()
    val listSetup get() = _listSetup

    suspend fun fetchSetupFromFirestore() {

        val setupCollection = db.collection("setup")
        Log.d("DB:", "DB:$setupCollection")
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
        val setupIdList = mutableListOf<String>() // Initialize with an empty list for IDs

        for (document in setupSnapshot) {
            //val documentId = document.id // Get the document ID
            // setupIdList.add(documentId)
            //Log.d("IDSETUP", "IDSETUP:$documentId")
            val code = document.getLong("code")?.toInt() ?: 0
            val frontRightWheelRef = document.getDocumentReference("frontRightWheel")
            val frontRightWheelDocument = frontRightWheelRef?.get()?.await()

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
            Log.d("notes", "notes:$notes")

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

            setup?.let { setupList.add(it) }
            Log.d("SetupRepository", "frontRightWheel: $frontRightWheel")
            Log.d("SetupRepository", "frontLeftWheel: $frontLeftWheel")
            Log.d("SetupRepository", "rearRightWheel: $rearRightWheel")
            Log.d("SetupRepository", "rearLeftWheel: $rearLeftWheel")
            Log.d("SetupRepository", "frontDamper: $frontDamper")
            Log.d("SetupRepository", "backDamper: $backDamper")
            Log.d("SetupRepository", "frontSpring: $frontSpring")
            Log.d("SetupRepository", "backSpring: $backSpring")
            Log.d("SetupRepository", "frontBalance: $frontBalance")
            Log.d("SetupRepository", "backBalance: $backBalance")

            Log.d("preferredEvent", "preferredEvent:$preferredEvent")
            Log.d("frontWingHole", "frontWingHole:$frontWingHole")
            Log.d("setup", "setup:$setup")
        }

        withContext(Dispatchers.Main) {
            _listSetup.value = setupList // Use postValue to update MutableLiveData on the main thread
        }
    }

    fun removeSetup(setup: DataSetup) {
        val collectionRef = db.collection("setup")

        collectionRef.whereEqualTo("code", setup.code)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documentSnapshot = querySnapshot.documents[0]
                    val documentId = documentSnapshot.id

                    collectionRef.document(documentId).delete()
                        .addOnSuccessListener {
                            // Rimuovi il setup dalla lista locale
                            _listSetup.value?.remove(setup)

                            // Aggiorna il valore di listSetup per attivare gli osservatori
                            _listSetup.value = _listSetup.value

                            Log.e("SetupViewModel", "Setup eliminato con successo")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("SetupViewModel", "Impossibile eliminare il setup", exception)
                        }
                } else {
                    Log.e("SetupRepository", "Nessun documento corrispondente trovato")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("SetupRepository", "Errore durante la ricerca del documento", exception)
            }
    }

    fun addNewSetup(newSetup: DataSetup) {
        val collectionRef = db.collection("setup")
        val setupRef = collectionRef.document()

        setupRef.set(newSetup)
            .addOnSuccessListener {
                val updatedList = _listSetup.value
                    ?: mutableListOf() // Get the current list or initialize an empty list

                updatedList.add(newSetup) // Add the new setup to the updated list
                _listSetup.postValue(updatedList) // Use postValue to update the MutableLiveData asynchronously

                Log.e("SetupRepository", "Nuovo setup aggiunto con successo")
            }
            .addOnFailureListener { exception ->
                Log.e("SetupRepository", "Impossibile aggiungere il nuovo setup", exception)
            }
    }
}
