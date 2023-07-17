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

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchTracksFromFirestore()
        }
    }

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    //_listTracks è una variabile privata di tipo MutableLiveData che contiene una lista mutabile
    // di oggetti DataTrack.
    private val _listTracks: MutableLiveData<MutableList<DataTrack>> = MutableLiveData()
    val listTracks get() = _listTracks
    // L'uso del modificatore get() indica che questa proprietà ha solo un'implementazione
    // per la lettura e non per la scrittura.


    suspend fun fetchTracksFromFirestore() {
        val tracksCollection = db.collection("track")

        val trackSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            //get() per ottenere i documenti delle tracks dalla collection track
            tracksCollection.get()
                //addOnSuccessListener per registrare un ascoltatore di successo che viene eseguito
                // quando il recupero dei dati della collezione delle tracks ha successo
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)//per riprendere la sospensione e
                    // restituire querySnapshot come risultato della funzione.
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)// per riprendere la sospensione
                    // e restituire un'eccezione come risultato della funzione.
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
            _listTracks.value = trackList //Assegna il valore della variabile trackList a _listTracks
        }
    }

    /*
    Modifies the length of a track given
     */
    fun modifyTrackLength(track: DataTrack, newLength: Double) {

        val collectionRef = db.collection("track")
        // Si cerca il documento nella collezione "track" in cui il valore del campo "name"
        // è uguale al valore di track.name
        val query = collectionRef.whereEqualTo("name", track.name)

        query.get().addOnSuccessListener { querySnapshot -> //se si è ottenuta la track correttamente
            if (!querySnapshot.isEmpty) {
                val documentSnapshot = querySnapshot.documents[0]
                val documentId = documentSnapshot.id

                // Si setta il valore di newLength in corrispondenza della chiave "length" dell'hashMap
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

        val collectionRef = db.collection("track")
        //La chiamata al metodo document() senza passare alcun argomento genera un nuovo ID univoco
        // per il documento e restituisce un riferimento a quel documento
        val trackRef = collectionRef.document()

        trackRef.set(newTrack) //imposta i dati del nuovo documento nel database utilizzando il metodo set()
            .addOnSuccessListener {
                val updatedList = _listTracks.value ?: mutableListOf() // Get the current list or initialize an empty list

                updatedList.add(newTrack) // Add the new track to the updated list
                _listTracks.postValue(updatedList) //Il valore di _listTracks viene aggiornato
                // con la lista aggiornata updatedList utilizzando postValue()
                // per garantire che l'aggiornamento venga eseguito in modo asincrono.
                println(listTracks.value)
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


