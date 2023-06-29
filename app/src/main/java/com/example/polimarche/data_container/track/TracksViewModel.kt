package com.example.polimarche.data_container.track

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.data_container.track.TracksRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class TracksViewModel: ViewModel() {

    private val tracksRepository: TracksRepository = TracksRepository()

    init {
        viewModelScope.launch {
            tracksRepository.fetchTracksFromFirestore()
        }
    }

    fun initialize(){
        viewModelScope.launch {
            tracksRepository.fetchTracksFromFirestore()
        }
    }


    private val _listTracks: MutableLiveData<MutableList<DataTrack>> =
        tracksRepository.listTracks
    val listTracks get() = _listTracks

    /*
    Returns a new list of MutableLiveData<MutableList<DataTrack>> in which all elements
    have a name that contains a created by a search view.
     */
    fun filterList(query: String): MutableLiveData<MutableList<DataTrack>> {
        val mutableLiveData: MutableLiveData<MutableList<DataTrack>> =
            MutableLiveData<MutableList<DataTrack>>().apply {
                value= emptyList<DataTrack>().toMutableList()
            }
        _listTracks.value?.forEach {
            if(query.lowercase() in it.name.lowercase()) {
                mutableLiveData.value = mutableLiveData.value?.plus(it)
                        as MutableList<DataTrack>?
            }
        }
        return mutableLiveData
    }

    /*
    Recalls directly the repository to change the length of a track given
     */
    fun modifyTrackLength(track: DataTrack, newLength: Double) {
        track.length = newLength.toString()
        tracksRepository.modifyTrackLength(track, newLength)
    }



    /*
    Recalls directly the repository to change the length of a track given
     */
    fun addNewTrack(newTrack: DataTrack){
        tracksRepository.addNewTrack(newTrack)
    }

    /*
    Recalls directly the repository to delete a track given
     */
    fun removeTrack(trackToDelete: DataTrack){
        tracksRepository.removeTrack(trackToDelete)
    }

}