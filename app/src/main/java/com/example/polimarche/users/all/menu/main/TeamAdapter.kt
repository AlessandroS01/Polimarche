package com.example.polimarche.users.all.menu.main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.polimarche.R
import com.example.polimarche.data_container.team_members_workshop.DataTeamMember
import com.example.polimarche.data_container.team_members_workshop.DataWorkshopArea
import com.example.polimarche.data_container.team_members_workshop.TeamViewModel
import java.io.Serializable


class TeamAdapter (
    teamViewModel: TeamViewModel,
    private val listener: OnWorkshopAreaClickListener
) : RecyclerView.Adapter<ViewHolder>() {
    // Estende RecyclerView.Adapter e prende come argomenti il teamViewModel
    // e un'implementazione di OnWorkshopAreaClickListener

    /*
    listMembers è lista mutabile di tipo DataTeamMember, inizializzata con il valore di teamViewModel.listMembers.value
     */
    private val listMembers: MutableList<DataTeamMember> =
        teamViewModel.listMembers.value?.toMutableList()!!

    /*
     listDepartments è lista mutabile di tipo DataWorkshopArea, inizializzata con il valore di teamViewModel.listDepartments.value
     */
    private val listDepartments: MutableList<DataWorkshopArea> =
        teamViewModel.listDepartments.value?.toMutableList()!!

    /*
    sortedList è l'elenco che verrà mostrato da recyclerView.
    Per questo motivo ogni volta che la dimensione di listMembers cambia, verrà ridefinita
    tramite il metodo changeSortedList()
     */
    // inizializzata con una lista ordinata in base alle aree di lavoro e ai membri del team
    private var sortedList = getSortedItems(listDepartments, listMembers)

    /*
    Lista mutabile di tipo DataTeamMember contenente tutti i membri del team che sono attualmente "invisibili"
    dopo il clic su un dipartimento.
     */
    private var membersToggled : MutableList<DataTeamMember> = mutableListOf()

    /*
    Prende in ingresso la lista dei membri e dei dipartimenti e ordina gli articoli all'interno della recyclerView.
    Infatti crea automaticamente un elenco ordinato in cui ogni membro del team
    hanno un posto proprio sotto il dipartimento di cui fanno parte.
     */
    private fun getSortedItems(
        listDepartments: MutableList<DataWorkshopArea>,
        listMembers: MutableList<DataTeamMember>
    ): MutableList<Any> {
        val sortedList: MutableList<Any> = mutableListOf()

        listDepartments.forEach {
            // Qui aggiungiamo ogni area del dipartimento all'elenco ordinato
            sortedList.add(it)
            val department = it.department
            // Qui aggiungiamo tutti i membri del team che fanno parte del dipartimento aggiunto
            sortedList.addAll(
                listMembers.filter {
                    it.workshopArea == department
                }
            )
        }
        return sortedList
    }


    // Dichiarazione di due costanti per identificare gli elementi DataTeamMember e DataWorkshopArea nella RecyclerView
    private val ITEM_TYPE_MEMBER = 1
    private val ITEM_TYPE_WORKSHOP = 2

    /*
    Questa interfaccia è implementata all'interno della classe "TeamFragment" e
    serve per capire su quale particolare elemento di "DataWorkshopArea",
    memorizzato all'interno di sortedList(), è stato fatto clic.
     */
    interface OnWorkshopAreaClickListener{
        fun onWorkshopAreaClick(workshopAreaClicked: String)
    }

    // Classe interna che estende la classe ViewHolder e gestisce la visualizzazione dei membri del team nella RecyclerView
    inner class ViewHolderMembersList(memberView : View) : ViewHolder(memberView) {

        // identifyMember viene inizializzata con la vista corrispondente all'ID identifyMember presente nella vista memberView
        val identifyMember: TextView = memberView.findViewById(R.id.identifyMember)
        // detailsView viene inizializzata con la vista corrispondente all'ID viewDetailsMember presente nella vista memberView
        val detailsView: ImageView = memberView.findViewById(R.id.viewDetailsMember)

        @SuppressLint("SetTextI18n")
        /*
        Questo metodo viene chiamato all'interno di onBindViewHolder e imposta i 2 attributi
        cercando all'interno di sortedList() l'elemento che viene mostrato all'interno di recyclerView.
         */
        fun bind(item: Any){
            // Viene filtrata listMembers per trovare l'elemento corrispondente a item nell'elenco
            // e viene assegnato il primo elemento trovato a teamMember
            val teamMember = listMembers.filter { it == item }[0]
            // Imposta il testo della vista identifyMember concatenando la matricola e il firstName del membro del team
            identifyMember.text = "${teamMember.matriculationNumber}:  ${teamMember.firstName}"
            // Imposta l'immagine della vista detailsView con l'icona dell'occhiolino
            detailsView.setImageResource(R.drawable.visibility_icon)

            // Quando viene cliccata, crea un'Intent per avviare l'attività DetailsMemberActivity
            // e aggiunge i dati del membro del team come extra nell'intent.
            detailsView.setOnClickListener {
                Intent(it.context, DetailsMemberActivity::class.java).apply {
                    this.putExtra("MATRICULATION" , teamMember.matriculationNumber)
                    this.putExtra("WORKSHOP_AREA" , teamMember.workshopArea)
                    this.putExtra("FIRST_NAME" , teamMember.firstName)
                    this.putExtra("LAST_NAME" , teamMember.lastName)
                    this.putExtra("DOB" , teamMember.dateOfBirth)
                    this.putExtra("EMAIL" , teamMember.email)
                    this.putExtra("NUMBER" , teamMember.cellNumber)
                    it.context.startActivity(this)
                }
            }
        }
    }

    // Classe interna che estende la classe ViewHolder e gestisce la visualizzazione delle aree di lavoro del team nella RecyclerView
    inner class ViewHolderWorkshopAreas(
        workshopAreaView : View
    ) : ViewHolder(workshopAreaView), View.OnClickListener{

        // identifyArea viene inizializzata con la vista corrispondente all'ID identifyWorkshopArea presente nella vista workshopAreaView
        private val identifyArea: TextView = workshopAreaView.findViewById(R.id.identifyWorkshopArea)

        /*
        Questo metodo viene chiamato all'interno di onBindViewHolder e imposta i 2 attributi
        cercando all'interno di sortedList() l'elemento che viene mostrato all'interno di recyclerView.
         */
        fun bind(item: Any){
            // Viene filtrato listDepartments per trovare l'elemento corrispondente a item nell'elenco e si assegna
            // il primo elemento risultante a workshopArea
            val workshopArea = listDepartments.filter { item == it }[0]
            identifyArea.text = workshopArea.department
        }
        /*
       I metodi init{} e onClick{} restituiscono il dipartimento su cui è stato fatto clic.
        In particolare, all'interno del metodo onClick{}, troviamo quale elemento della sorted
        list è stato cliccato.
         */
        init {
            workshopAreaView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition // viene ottenuta la posizione dell'elemento cliccato nella RecyclerView
            if (position != RecyclerView.NO_POSITION) {
                val workshopArea: DataWorkshopArea = listDepartments.filter {
                    sortedList[position] == it }[0] // viene ricavato l'oggetto DataWorkshopArea corrispondente all'elemento cliccato
                // viene richiamato il metodo onWorkshopAreaClick dell'interfaccia OnWorkshopAreaClickListener tramite l'oggetto listener,
                // passando come argomento il nome del dipartimento dell'area di lavoro
                listener.onWorkshopAreaClick(workshopArea.department)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType){ // Restituisce un oggetto ViewHolder in base al tipo di visualizzazione (viewType) dell'elemento
            ITEM_TYPE_WORKSHOP ->{
                // Se il tipo di visualizzazione è ITEM_TYPE_WORKSHOP, crea una nuova istanza di ViewHolderWorkshopAreas
                // responsabile della visualizzazione delle aree di lavoro
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_general_workshop_area, parent, false
                )
                ViewHolderWorkshopAreas(view)
            }
            ITEM_TYPE_MEMBER ->{
                // Se il tipo di visualizzazione è ITEM_TYPE_MEMBER, crea una nuova istanza di ViewHolderMembersList
                // responsabile della visualizzazione dei membri del team
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_general_team_member, parent, false
                )
                ViewHolderMembersList(view)
            }
            // Altrimenti viene generata un'eccezione
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    /*
    Questo metodo viene utilizzato per capire se i diversi elementi dell'elenco ordinato
    sono un'istanza di "DataTeamMember" o "DataWorkshopArea".
     */
    override fun getItemViewType(position: Int): Int {
        return when (sortedList[position]) {
            is DataTeamMember -> ITEM_TYPE_MEMBER
            is DataWorkshopArea -> ITEM_TYPE_WORKSHOP
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun getItemCount(): Int {
        return sortedList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) { // si determina il tipo specifico del ViewHolder
            is ViewHolderWorkshopAreas -> { // se è di tipo ViewHolderWorkshopAreas
                val item = sortedList[position] // Ottiene l'elemento corrispondente alla posizione dalla sortedList
                holder.bind(item) // Imposta i dati dell'elemento corrente nella vista del ViewHolder
            }
            is ViewHolderMembersList -> { // se è di tipo ViewHolderMembersList
                val item = sortedList[position] // Ottiene l'elemento corrispondente alla posizione position dalla sortedList
                holder.bind(item) // Imposta i dati dell'elemento corrente nella vista del ViewHolder
            }
        }
    }


    /*
    Metodo utilizzato per attivare o disattivare la visibilità dei membri del team dopo il clic su
    il Dipartimento.
    Viene richiamato direttamente da TeamFragment.
     */
    fun switchingVisibility(department : String){
        // Controlla se nessun membro del team è stato precedentemente nascosto per il dipartimento specificato
        if (membersToggled.none{ it.workshopArea == department }) {
            // Si filtra listMembers e si selezionano solo i membri con workshopArea uguale al dipartimento.
            // I membri filtrati vengono convertiti in una MutableList e aggiunti a membersToggled
            membersToggled += listMembers.filter {
                it.workshopArea == department
            }.toMutableList()

            // Rimuove dalla lista listMembers tutti i membri che sono stati aggiunti a membersToggled
            listMembers -= membersToggled.toSet()

            // Metodo per aggiornare la lista dei membri e notificare l'adapter del cambiamento
            changeSortedList()
        }
        else { // Se alcuni membri del team sono stati nascosti per il dipartimento specificato
            // Aggiunge alla lista listMembers i membri del team che sono stati precedentemente nascosti per il dipartimento specificato
            listMembers.addAll(membersToggled.filter {
                it.workshopArea == department
            })
            // Rimuove dalla lista membersToggled tutti i membri che sono stati aggiunti a listMembers
            membersToggled -= listMembers.toSet()

            // Aggiornare la lista dei membri ordinata e notificare l'adapter del cambiamento
            changeSortedList()
        }
    }

    /*
    Aggiorna sortedList in base ai dipartimenti e ai membri del team correnti,
    e notifica al RecyclerView di aggiornare l'interfaccia utente per riflettere tali modifiche
     */
    private fun changeSortedList(){
        sortedList = getSortedItems(listDepartments, listMembers)
        println(sortedList.size)
        notifyDataSetChanged()
    }

}




