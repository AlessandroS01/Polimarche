package com.example.polimarche.data_container.track

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.team_members_workshop.DataTeamMember
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class TracksRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchTracksFromFirestore()
        }
    }

    private val db = FirebaseFirestore.getInstance()

    private val _listTracks: MutableLiveData<MutableList<DataTrack>> = MutableLiveData()
    val listTracks get() = _listTracks


    suspend fun fetchTracksFromFirestore() {
        val tracksCollection = db.collection("track")

        val trackSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            tracksCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val trackList = mutableListOf<DataTrack>() // Initialize with an empty list
        val trackIdList = mutableListOf<String>() // Initialize with an empty list for IDs

        for (document in trackSnapshot) {
            val documentId = document.id // Get the document ID
            trackIdList.add(documentId)

            val name = document.getString("name")!!
            val length = document.getString("length")!!
            val track = DataTrack(
                name,
                length
            )

            trackList.add(track)
        }

        withContext(Dispatchers.Main) {
            _listTracks.value =
                trackList // Use postValue to update MutableLiveData on the main thread
        }

        // Process the trackIdList as needed
        // ...
    }

    /*
    Modifies the length of a track given
     */
    fun modifyTrackLength(track: DataTrack, newLength: Double) {

        val collectionRef = db.collection("track")
        val query = collectionRef.whereEqualTo("name", track.name)

        query.get().addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                val documentId = documentSnapshot.id

                // Update the value
                val newData = hashMapOf<String, Any>("length" to newLength.toString())
                collectionRef.document(documentId).update(newData)
                    .addOnSuccessListener {
                        Log.e("TracksRepository", "Track length updated successfully")

                        // Update the track length in the local list or perform any other necessary actions
                    }
                    .addOnFailureListener { exception ->
                        Log.e("TracksRepository", "Failed to update track length", exception)
                    }
            } else {
                Log.e("TracksRepository", "No matching document found")
            }
        }


    }


    fun addNewTrack(newTrack: DataTrack) {
        Log.d("SUCA", "SUCA:${newTrack.name}")
        val collectionRef = db.collection("track")
        val trackRef = collectionRef.document()

        trackRef.set(newTrack)
            .addOnSuccessListener {
                // Add the new track to the local list
                _listTracks.value?.add(newTrack)

                // Update the value of listTracks to trigger observers
                _listTracks.value = _listTracks.value

                Log.e("TracksRepository", "New track added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("TracksRepository", "Failed to add new track", exception)
            }
    }


    fun removeTrack(track: DataTrack) {
        val collectionRef = db.collection("track")

        collectionRef.whereEqualTo("name", track.name)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val documentSnapshot = querySnapshot.documents[0]
                    val documentId = documentSnapshot.id

                    collectionRef.document(documentId).delete()
                        .addOnSuccessListener {
                            // Rimuovi la traccia dalla lista locale
                            _listTracks.value?.remove(track)

                            // Aggiorna il valore di listTracks per attivare gli osservatori
                            _listTracks.value = _listTracks.value

                            Log.e("TracksViewModel", "Traccia eliminata con successo")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("TracksViewModel", "Impossibile eliminare la traccia", exception)
                        }
                } else {
                    Log.e("TracksRepository", "Nessun documento corrispondente trovato")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("TracksRepository", "Errore durante la ricerca del documento", exception)
            }
    }

}


