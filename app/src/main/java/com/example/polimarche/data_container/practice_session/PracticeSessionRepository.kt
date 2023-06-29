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

object PracticeSessionRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchSessionFromFirestore()
        }
    }

    private val db= FirebaseFirestore.getInstance()

    private val _listPracticeSessions: MutableLiveData<MutableList<DataPracticeSession>> =
        MutableLiveData()
    val listPracticeSession get() = _listPracticeSessions




    suspend fun fetchSessionFromFirestore() {
        val practiceSessionsCollection = db.collection("practiceSessions")

        val sessionSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            practiceSessionsCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val practiceSessionsList = mutableListOf<DataPracticeSession>()
        val practiceSessionsIdList = mutableListOf<String>()

                for (document in sessionSnapshot) {
                    val documentId = document.id // Get the document ID
                    practiceSessionsIdList.add(documentId)
                    Log.d("ID","$documentId")
                    val eventType = document.getString("eventType") ?: ""
                    val dateStr = document.getString("date") ?: ""
                    val date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                    val startingTimeStr = document.getString("startingTime") ?: ""
                    val startingTime = LocalTime.parse(startingTimeStr, DateTimeFormatter.ofPattern("HH:mm"))

                    val endingTimeStr = document.getString("endingTime") ?: ""
                    val endingTime = LocalTime.parse(endingTimeStr, DateTimeFormatter.ofPattern("HH:mm"))

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
        val newDocumentRef = db.collection("practice_sessions").document()

        // Creazione di un oggetto mappa contenente i dati della nuova sessione di pratica
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
                // Puoi eseguire eventuali azioni aggiuntive qui, se necessario
            }
            .addOnFailureListener { e ->
                // Errore durante l'aggiunta della sessione di pratica a Firestore
                // Puoi gestire l'errore qui o visualizzare un messaggio all'utente
            }
    }

}


