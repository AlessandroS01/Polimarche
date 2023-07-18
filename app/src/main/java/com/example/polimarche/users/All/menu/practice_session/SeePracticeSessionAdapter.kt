package com.example.polimarche.users.all.menu.practice_session

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.practice_session.DataPracticeSession
import com.example.polimarche.data_container.practice_session.PracticeSessionViewModel
import java.time.format.DateTimeFormatter


class SeePracticeSessionAdapter(
    private val practiceSessionViewModel: PracticeSessionViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Lista mutabile di oggetti DataPracticeSession vuota.
    private var listPracticeSession: MutableList<DataPracticeSession> = mutableListOf()

    init { // Blocco di inizializzazione che viene eseguito al momento della creazione di un'istanza della classe
        // Observer per l'oggetto LiveData listPracticeSession che viene eseguito ogni volta che il valore di listPracticeSession cambia
        practiceSessionViewModel.listPracticeSession.observeForever { session ->
            listPracticeSession.clear() // Svuota la lista
            listPracticeSession.addAll(session) // Aggiunge tutti gli elementi presenti nell'oggetto session
            notifyDataSetChanged() // Notifica all'adapter che i dati sono cambiati
        }
    }

    // ViewHolder personalizzato utilizzato per visualizzare gli elementi di ogni PracticeSession
    // della lista in un RecyclerView
    inner class ViewHolderPracticeSession(
        practiceSessionView : View
    ) : RecyclerView.ViewHolder(practiceSessionView){
        val eventType: TextView = practiceSessionView.findViewById(R.id.pSEventType)
        val date: TextView = practiceSessionView.findViewById(R.id.pSDate)
        val startingTime: TextView = practiceSessionView.findViewById(R.id.pSStartingTime)
        val endingTime: TextView = practiceSessionView.findViewById(R.id.pSEndingTime)
        val trackName: TextView = practiceSessionView.findViewById(R.id.pSTrack)
        val weather: TextView = practiceSessionView.findViewById(R.id.pSWeather)
        val trackCondition: TextView = practiceSessionView.findViewById(R.id.pSTrackCondition)
        val trackTemperature: TextView = practiceSessionView.findViewById(R.id.pSTrackTemperature)
        val ambientPressure: TextView = practiceSessionView.findViewById(R.id.pSAmbientPressure)
        val airTemperature: TextView = practiceSessionView.findViewById(R.id.pSAirTemperature)

        // ConstraintLayout che rappresenta la vista cliccabile per interagire con
        // l'elemento della sessione di pratica.
        val constraintTouchable: ConstraintLayout = practiceSessionView.findViewById(
            R.id.constraintLayoutSeePS
        )
        // LinearLayout che rappresenta la vista espandibile che mostra ulteriori dettagli della sessione di pratica quando viene cliccata
        val linearLayoutExpandable: LinearLayout = practiceSessionView.findViewById(
            R.id.linearLayoutExpandablePS
        )

    }

    // Rappresenta come devono essere visualizzati i dati delle PracticeSessions quando la si espande
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_general_see_practice_session, parent, false
        )
        return ViewHolderPracticeSession(view)
    }

    override fun getItemCount(): Int {
        return listPracticeSession.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Controllo per verificare il tipo di ViewHolder
        when(holder){
            /*All'interno del blocco when, vengono popolate le diverse viste all'interno del ViewHolder
            con i dati corrispondenti all'elemento nella posizione specificata */
            is ViewHolderPracticeSession ->{
                holder.apply {
                    eventType.text = "Event: ${listPracticeSession?.get(position)?.eventType}"
                    date.text = "Date: ${listPracticeSession?.get(position)?.date?.format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd"))}"

                    startingTime.text = "Starting time: ${listPracticeSession?.get(position)?.startingTime.toString()}"
                    endingTime.text = "Ending time: ${listPracticeSession?.get(position)?.endingTime.toString()}"
                    trackName.text = "Track: ${listPracticeSession?.get(position)?.trackName}"
                    weather.text = "Weather: ${listPracticeSession?.get(position)?.weather}"
                    trackCondition.text = "Track condition: ${listPracticeSession?.get(position)?.trackCondition}"
                    trackTemperature.text = "Track temperature: ${listPracticeSession?.get(position)?.trackTemperature.toString()}"
                    ambientPressure.text = "Ambient pressure: ${listPracticeSession?.get(position)?.ambientPressure.toString()}"
                    airTemperature.text = "Air temperature: ${listPracticeSession?.get(position)?.airTemperature.toString()}"

                    val expansion = listPracticeSession.get(position)?.expansion!!
                    /* Imposta la visibilità di linearLayoutExpandable in base allo stato di espansione ottenuto
                       dalla lista listPracticeSession nella posizione corrente.
                       Se l'elemento è espanso, la visibilità viene impostata su View.VISIBLE,
                       altrimenti viene impostata su View.GONE
                   * */
                    linearLayoutExpandable.visibility = if (expansion) View.VISIBLE else View.GONE

                    // Quando viene cliccato, inverte lo stato di espansione dell'elemento in listPracticeSession
                    // nella posizione corrente e notifica l'adapter
                    constraintTouchable.setOnClickListener{
                        listPracticeSession.get(position)?.expansion =
                            ! listPracticeSession.get(position)?.expansion!!
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }

    /*
    Metodo che ripristina l'elenco predefinito della recyclerView.
     */
    fun restoreListToDefault(){
        setNewList(practiceSessionViewModel.listPracticeSession.value?.toMutableList()!!)
    }

    /*
    Filtra la lista delle sessioni di pratica in base alla data e al tipo di evento specificati e
    imposta la nuova lista filtrata come elenco visualizzato nell'interfaccia utente.
     */
    fun filterListByEventChecked(query: String, eventType: String) {
        listPracticeSession = practiceSessionViewModel.listPracticeSession.value?.toMutableList()!!
        val listFiltered = listPracticeSession.filter {
            it.date.toString() == query && it.eventType.lowercase() == eventType.lowercase()
        }.toMutableList()
        setNewList(listFiltered)
    }


    /*
    Filtra la lista delle sessioni di pratica in base alla data specificata,
    senza considerare il tipo di evento, e imposta la nuova lista filtrata come elenco visualizzato nell'interfaccia utente.
     */
    fun filterListWithoutEvents(query: String){
        listPracticeSession = practiceSessionViewModel.listPracticeSession.value?.toMutableList()!!
        val listFiltered = listPracticeSession.filter {
            it.date.toString() == query
        }.toMutableList()
        setNewList(listFiltered)
    }

    /*
    Aggiorna l'elenco degli elementi all'interno di recyclerView.
     */
    fun setNewList(newList: MutableList<DataPracticeSession>){
        listPracticeSession.clear()
        listPracticeSession.addAll(newList)
        notifyDataSetChanged()
    }
}