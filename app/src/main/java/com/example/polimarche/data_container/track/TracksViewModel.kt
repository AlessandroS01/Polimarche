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

    //_listTracks è una variabile privata di tipo MutableLiveData che contiene una lista mutabile
    // di oggetti DataTrack.
    // Viene inizializzata con il valore di teamRepository.listMembers
    private val _listTracks: MutableLiveData<MutableList<DataTrack>> =
        tracksRepository.listTracks
    val listTracks get() = _listTracks
    // L'uso del modificatore get() indica che questa proprietà ha solo un'implementazione
    // per la lettura e non per la scrittura.

    /*
    Ritorna un MutableLiveData<MutableList<DataTrack>> contenente la lista delle tracks
    filtrata in base al nome inserito dall'utente nella search view (barra di ricerca).
     */
    fun filterList(query: String): MutableLiveData<MutableList<DataTrack>> { //query è ciò che
        val mutableLiveData: MutableLiveData<MutableList<DataTrack>> =        //l'utente inserisce
            MutableLiveData<MutableList<DataTrack>>().apply {
                //L'uso di apply consente di configurare l'oggetto senza dover fare riferimento
                // esplicito ad esso.

                // Viene utilizzata la funzione emptyList<DataTrack>() per creare una lista vuota
                // di tipo DataTrack.
                value = emptyList<DataTrack>().toMutableList()
            }
        /* Filtra _listTracks in base alla corrispondenza tra il valore di query e il nome degli elementi.
        Gli elementi che soddisfano la condizione vengono aggiunti a mutableLiveData*/
        _listTracks.value?.forEach {
            if(query.lowercase() in it.name.lowercase()) {
                mutableLiveData.value = mutableLiveData.value?.plus(it)
                        as MutableList<DataTrack>?
            }
        }
        return mutableLiveData
    }

    /*
        Richiama direttamente la Repository per cambiare la lunghezza della track
     */
    fun modifyTrackLength(track: DataTrack, newLength: Double) {
        track.length = newLength.toString()
        tracksRepository.modifyTrackLength(track, newLength)
    }



    /*
       Richiama direttamente la Repository per aggiungere una nuova track
     */
    fun addNewTrack(newTrack: DataTrack){
        tracksRepository.addNewTrack(newTrack)
    }

    /*
       Richiama direttamente la Repository per eliminare una track
     */
    fun removeTrack(trackToDelete: DataTrack){
        tracksRepository.removeTrack(trackToDelete)
    }

}