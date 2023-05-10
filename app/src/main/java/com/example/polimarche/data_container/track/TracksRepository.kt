package com.example.polimarche.data_container.track

import androidx.lifecycle.MutableLiveData

object TracksRepository {

    private val _listTracks: MutableLiveData<MutableList<DataTrack>> =
        MutableLiveData(
            mutableListOf(
                DataTrack("Imola", 5.209),
                DataTrack("Monza", 6.317),
            )
        )
    val listTracks get() = _listTracks

    /*
    Modifies the length of a track given
     */
    fun modifyTrackLength(track: DataTrack, newLength: Double){
        _listTracks.value?.forEach {
            if ( it.name == track.name)
                it.length = newLength
        }
    }

    /*
    Adds a new track to the list
     */
    fun addNewTrack(newTrack: DataTrack){
        _listTracks.value =
            _listTracks.value?.plus(newTrack) as MutableList<DataTrack>?
    }

    /*
    Removes an existing track from the list
     */
    fun removeTrack(trackToDelete: DataTrack){
        _listTracks.value?.remove(trackToDelete)
    }
}