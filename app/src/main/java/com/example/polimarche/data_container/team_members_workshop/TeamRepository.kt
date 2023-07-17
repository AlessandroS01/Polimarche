package com.example.polimarche.data_container.team_members_workshop

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.spring.SpringRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TeamRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            fetchDataFromFirestore()
        }
    }

    private val db = FirebaseFirestore.getInstance()

    //_listMembers è una variabile privata di tipo MutableLiveData che contiene una lista mutabile
    // di oggetti DataTeamMember.
    private val _listMembers: MutableLiveData<MutableList<DataTeamMember>> = MutableLiveData()
    val listMembers get() = _listMembers
    // L'uso del modificatore get() indica che questa proprietà ha solo un'implementazione
    // per la lettura e non per la scrittura.

    private val _listDepartments: MutableLiveData<MutableList<DataWorkshopArea>> = MutableLiveData()
    val listDepartments get() = _listDepartments


    suspend fun fetchDataFromFirestore() {

        val teamMembersCollection = db.collection("team_members")

        val teamMembersSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            //get() per ottenere i dati della collezione dei membri del team
            teamMembersCollection.get()
                //addOnSuccessListener per registrare un ascoltatore di successo che viene eseguito
                // quando il recupero dei dati della collezione dei membri della squadra ha successo
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot) //per riprendere la sospensione e
                                            // restituire querySnapshot come risultato della funzione.
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception) // per riprendere la sospensione
                                        // e restituire un'eccezione come risultato della funzione.
                }
        }

        val membersList = mutableListOf<DataTeamMember>() // Initialize with an empty list

        for (document in teamMembersSnapshot) {

            val matriculation = document.getString("matriculation").toString().toLong()
            val firstName = document.getString("firstName")!!
            val lastName = document.getString("lastName")!!
            val dateOfBirth = document.getString("dateOfBirth")!!
            val email = document.getString("email")!!
            val cellNumber = document.getString("cellNumber")!!
            val workshopArea = document.getString("workshopArea")!!
            //Viene creato un oggetto DataTeamMember utilizzando i valori estratti dalle variabili precedenti.
            val member = DataTeamMember(
                matriculation,
                firstName,
                lastName,
                dateOfBirth,
                email,
                cellNumber,
                workshopArea
            )
            //L'oggetto member viene aggiunto alla lista membersList.
            membersList.add(member)
        }
        withContext(Dispatchers.Main) {
            _listMembers.value = membersList //Assegna il valore della variabile memberList a _listMembers
        }

        val departmentsCollection = db.collection("departments")

        val departmentsSnapshot = suspendCoroutine<QuerySnapshot> { continuation ->
            departmentsCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    continuation.resume(querySnapshot)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }

        val departmentList = mutableListOf<DataWorkshopArea>() // Initialize with an empty list

        for (document in departmentsSnapshot) {
            val departmentName = document.getString("department")!!
            val departmentHead = document.getString("departmentHead").toString().toLong()
            val department = DataWorkshopArea(departmentName, departmentHead)

            departmentList.add(department)


        }
        withContext(Dispatchers.Main) {
            _listDepartments.value = departmentList // Use postValue to update MutableLiveData on the main thread
        }


    }
}
