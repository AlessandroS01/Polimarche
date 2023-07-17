import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.practice_session.DataPracticeSession
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PracticeSessionRepository {

    init {//blocco di inizializzazione
        CoroutineScope(Dispatchers.IO).launch {
            fetchSessionFromFirestore()
        }
    }

    fun initialize() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchSessionFromFirestore()
        }
    }

    private val db = FirebaseFirestore.getInstance()

    //_listPracticeSessions è una variabile privata di tipo MutableLiveData che contiene una lista mutabile
    // di oggetti DataPracticeSession.
    private val _listPracticeSessions: MutableLiveData<MutableList<DataPracticeSession>> =
        MutableLiveData()
    val listPracticeSession get() = _listPracticeSessions
    // L'uso del modificatore get() indica che questa proprietà ha solo un'implementazione
    // per la lettura e non per la scrittura.


    suspend fun fetchSessionFromFirestore() {
        val practiceSessionsCollection = db.collection("practiceSessions")

        val sessionSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            //get() per ottenere i documenti delle tracks dalla collection track
            practiceSessionsCollection.get()
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

        val practiceSessionsList = mutableListOf<DataPracticeSession>() // Initialize with an empty list
        val practiceSessionsIdList = mutableListOf<String>() // Initialize with an empty list for IDs

                for (document in sessionSnapshot) {
                    val documentId = document.id // Get the document ID
                    practiceSessionsIdList.add(documentId)

                    Log.d("ID","$documentId")

                    //Assegniamo il valore della stringa associata alla chiave "eventType"
                    // nell'oggetto document alla variabile eventType, se presente,
                    // altrimenti assegna una stringa vuota come valore predefinito.
                    val eventType = document.getString("eventType") ?: ""
                    val dateStr = document.getString("date") ?: ""
                    // DateTimeFormatter.ofPattern("yyyy-MM-dd") specifica il formato atteso
                    // della stringa di input
                    val date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                    val startingTimeStr = document.getString("startingTime") ?: ""
                    // DateTimeFormatter.ofPattern("yyyy-MM-dd") specifica il formato atteso
                    // della stringa di input
                    val startingTime = LocalTime.parse(startingTimeStr, DateTimeFormatter.ofPattern("HH:mm:ss"))

                    val endingTimeStr = document.getString("endingTime") ?: ""
                    // DateTimeFormatter.ofPattern("yyyy-MM-dd") specifica il formato atteso
                    // della stringa di input
                    val endingTime = LocalTime.parse(endingTimeStr, DateTimeFormatter.ofPattern("HH:mm:ss"))

                    val trackName = document.getString("trackName") ?: ""
                    val weather = document.getString("weather") ?: ""
                    val trackCondition = document.getString("trackCondition") ?: ""
                    val trackTemperature = document.getDouble("trackTemperature") ?: 0.0
                    val ambientPressure = document.getDouble("ambientPressure") ?: 0.0
                    val airTemperature = document.getDouble("airTemperature") ?: 0.0

                    val practiceSession = DataPracticeSession(
                        eventType, date, startingTime, endingTime, trackName,
                        weather, trackCondition, trackTemperature, ambientPressure, airTemperature
                    )
                    practiceSessionsList.add(practiceSession)
                }
        withContext(Dispatchers.Main) {
            _listPracticeSessions.value =
                practiceSessionsList // Use postValue to update MutableLiveData on the main thread
        }
    }

    fun addNewPracticeSession(newSession: DataPracticeSession) {
        // Accesso a Firestore
        val db = FirebaseFirestore.getInstance()


        // Creazione di un nuovo documento con un ID generato automaticamente
        val newDocumentRef = db.collection("practiceSessions").document()

        // Creazione di un oggetto Map contenente i dati della nuova sessione di pratica
        val practiceSessionData = hashMapOf(
            "eventType" to newSession.eventType,
            "date" to newSession.date.toString(),
            "startingTime" to newSession.startingTime.toString(),
            "endingTime" to newSession.endingTime.toString(),
            "trackName" to newSession.trackName,
            "weather" to newSession.weather,
            "trackCondition" to newSession.trackCondition,
            "trackTemperature" to newSession.trackTemperature,
            "ambientPressure" to newSession.ambientPressure,
            "airTemperature" to newSession.airTemperature
        )
        // Esegue la formattazione di newSession.date in una stringa nel formato "yyyy-MM-dd"
        val dateStr = newSession.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        practiceSessionData["date"] = dateStr

        val startingTimeStr = newSession.startingTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        practiceSessionData["startingTime"] = startingTimeStr

        val endingTimeStr = newSession.endingTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        practiceSessionData["endingTime"] = endingTimeStr


        // Aggiunta dei dati al documento nel database
        newDocumentRef.set(practiceSessionData)
            .addOnSuccessListener {
                // Aggiunta avvenuta con successo
            }
            .addOnFailureListener { e ->
                // Errore durante l'aggiunta della sessione di pratica a Firestore
            }
    }

}


