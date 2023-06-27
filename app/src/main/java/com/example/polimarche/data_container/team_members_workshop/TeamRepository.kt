package com.example.polimarche.data_container.team_members_workshop

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object TeamRepository {

    private val db = FirebaseFirestore.getInstance()

    private val _listMembers: MutableLiveData<MutableList<DataTeamMember>> = MutableLiveData()
    val listMembers get() = _listMembers

    private val _listDepartments: MutableLiveData<MutableList<DataWorkshopArea>> = MutableLiveData()
    val listDepartments get() = _listDepartments

    suspend fun fetchDataFromFirestore() {

        withContext(Dispatchers.IO) {
            Log.d("TeamRepository", "Fetching data from Firestore")
            try {
                // Esegui la query per ottenere i dati dei membri del team
                val membersQuerySnapshot = db.collection("Users").get().await()
                val membersList = mutableListOf<DataTeamMember>()
                for (document in membersQuerySnapshot.documents) {
                    val matriculation = document.getString("matriculation")
                    val firstName = document.getString("firstName")
                    val lastName = document.getString("lastName")
                    val dateOfBirth = document.getString("dateOfBirth")
                    val email = document.getString("email")
                    val cellNumber = document.getString("cellNumber")
                    val workshopArea = document.getString("workshopArea")
                    val member = DataTeamMember(matriculation, firstName, lastName, dateOfBirth, email, cellNumber, workshopArea)
                    membersList.add(member)
                    // Stampa il log dei dati del membro del team
                    Log.d("TeamRepository", "Membro del team: $matriculation, $firstName, $lastName, $dateOfBirth, $email, $cellNumber, $workshopArea")
                }
                _listMembers.postValue(membersList)

                // Esegui la query per ottenere i dati dei dipartimenti
                val departmentsQuerySnapshot = db.collection("departments").get().await()
                val departmentsList = mutableListOf<DataWorkshopArea>()
                for (document in departmentsQuerySnapshot.documents) {
                    val departmentName = document.getString("department")
                    val departmentHead = document.getString("departmentHead")
                    val department = DataWorkshopArea(departmentName, departmentHead)
                    departmentsList.add(department)
                }
                _listDepartments.postValue(departmentsList)
            } catch (e: Exception) {
                // Gestisci l'errore nella query
            }
        }
    }
}
