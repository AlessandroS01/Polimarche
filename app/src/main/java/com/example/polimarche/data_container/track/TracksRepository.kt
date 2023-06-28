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


    private var trackIds: MutableList<String> = mutableListOf()


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
            val length = document.getString("length").toString().toDouble()
            val track = DataTrack(
                name,
                length
            )

            trackList.add(track)
        }

        withContext(Dispatchers.Main) {
            _listTracks.value = trackList // Use postValue to update MutableLiveData on the main thread
        }

        // Process the trackIdList as needed
        // ...
    }

    /*
    Modifies the length of a track given
     */
    fun modifyTrackLength(track: DataTrack, newLength: Double) {
        val trackIndex = _listTracks.value?.indexOfFirst { it.name == track.name }

        if (trackIndex != null) {
            val trackId = trackIds.getOrNull(trackIndex)

            if (trackId != null) {
                val trackData = hashMapOf(
                    "length" to newLength
                )

                val trackRef = db.collection("track").document(trackId)
                trackRef.update(trackData as Map<String, Any>)
                    .addOnSuccessListener {
                        Log.e("TracksRepository", "Track length updated successfully")

                        // Update the track length in the local list
                        _listTracks.value?.get(trackIndex)?.length = newLength
                    }
                    .addOnFailureListener { exception ->
                        Log.e("TracksRepository", "Failed to update track length", exception)
                    }
            }
        }
    }


    fun addNewTrack(newTrack: DataTrack) {
        // Add the new track to the Firestore database
        val trackRef = db.collection("track").document()
        newTrack.name = trackRef.id
        trackRef.set(newTrack)
            .addOnSuccessListener {
                // Add the new track to the local list
                _listTracks.value?.add(newTrack)
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }

    fun removeTrack(trackToDelete: DataTrack) {
        // Delete the track from the Firestore database
        val trackRef = db.collection("track").document(trackToDelete.name)
        trackRef.delete()
            .addOnSuccessListener {
                // Remove the track from the local list
                _listTracks.value?.remove(trackToDelete)
            }
            .addOnFailureListener { exception ->
                // Handle the error
            }
    }



}
