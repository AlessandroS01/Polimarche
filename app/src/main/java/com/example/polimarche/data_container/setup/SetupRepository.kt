package com.example.polimarche.data_container.setup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.balance.DataBalance
import com.example.polimarche.data_container.damper.DataDamper
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.track.DataTrack
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

class SetupRepository {

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

    private val db = FirebaseFirestore.getInstance()

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
        val setupIdList = mutableListOf<String>() // Initialize with an empty list for IDs

        for (document in setupSnapshot) {
            val documentId = document.id // Get the document ID
            setupIdList.add(documentId)

            val code = document.getLong("code")?.toInt() ?: 0
            val frontRightWheel = document.get("frontRightWheel") as DataWheel
            val frontLeftWheel = document.get("frontLeftWheel") as DataWheel
            val rearRightWheel = document.get("rearRightWheel") as DataWheel
            val rearLeftWheel = document.get("rearLeftWheel") as DataWheel
            val frontDamper = document.get("frontDamper") as DataDamper
            val backDamper = document.get("backDamper") as DataDamper
            val frontSpring = document.get("frontSpring") as DataSpring
            val backSpring = document.get("backSpring") as DataSpring
            val frontBalance = document.get("frontBalance") as DataBalance
            val backBalance = document.get("backBalance") as DataBalance
            val preferredEvent = document.getString("preferredEvent") ?: ""
            val frontWingHole = document.getString("frontWingHole") ?: ""
            val notes = mutableListOf<String>() // Initialize an empty list for notes

            // Recupera eventuali note e aggiungile alla lista
            if (document.contains("notes")) {
                val notesArray = document.get("notes") as ArrayList<String>
                notes.addAll(notesArray)
            }

            val setup = DataSetup(
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

            setupList.add(setup)
        }

        withContext(Dispatchers.Main) {
            _listSetup.value = setupList // Use postValue to update MutableLiveData on the main thread
        }

        // Process the trackIdList as needed
        // ...
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
                val updatedList = _listSetup.value ?: mutableListOf() // Get the current list or initialize an empty list

                updatedList.add(newSetup) // Add the new setup to the updated list
                _listSetup.postValue(updatedList) // Use postValue to update the MutableLiveData asynchronously

                Log.e("SetupRepository", "Nuovo setup aggiunto con successo")
            }
            .addOnFailureListener { exception ->
                Log.e("SetupRepository", "Impossibile aggiungere il nuovo setup", exception)
            }
    }


}