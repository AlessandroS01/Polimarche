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

        for (document in trackSnapshot) {

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
    trackList.forEach {
        Log.d("TrackRepository", it.name)
    }

    }
        /*
    Modifies the length of a track given
     */
        fun modifyTrackLength(track: DataTrack, newLength: Double) {
            _listTracks.value?.forEach {
                if (it.name == track.name)
                    it.length = newLength
            }
        }

        /*
    Adds a new track to the list
     */
        fun addNewTrack(newTrack: DataTrack) {
            _listTracks.value =
                _listTracks.value?.plus(newTrack) as MutableList<DataTrack>?
        }

        /*
    Removes an existing track from the list
     */
        fun removeTrack(trackToDelete: DataTrack) {
            _listTracks.value?.remove(trackToDelete)
        }
}