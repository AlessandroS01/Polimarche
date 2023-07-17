package com.example.polimarche.users.all.menu.tracks

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.data_container.track.TracksViewModel

class SeeTracksAdapter(
    private val tracksViewModel: TracksViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    /*
    Contiene l'elenco completo delle tracks del viewModel
     */
    private val listTracks: MutableList<DataTrack> = mutableListOf()
    init { // blocco di inizializzazione
        // Observer per l'oggetto LiveData listTracks che viene eseguito
        // ogni volta che il valore di listTracks cambia
        tracksViewModel.listTracks.observeForever { tracks ->
            listTracks.clear()  // Svuota la lista
            listTracks.addAll(tracks) // Aggiunge tutti gli elementi presenti nell'oggetto tracks
            notifyDataSetChanged() // Notifica all'adapter che i dati sono cambiati
        }
    }

    /*
    Utilizzato per tenere d'occhio il valore della query creata dalla searchView quando
    un utente scrive su di essa.
     */
    private var inputQuery: MutableLiveData<String> = MutableLiveData("")
    fun setQuery(query: String){
        inputQuery.value = query
    }

    inner class ViewHolderTracks(
        tracksView: View
    ): RecyclerView.ViewHolder(tracksView){

        // Vengono passati a queste variabili i riferimenti dei corrispondenti elementi di layout XML
        val trackName = tracksView.findViewById(R.id.trackName) as TextView
        val trackLength = tracksView.findViewById(R.id.trackLength) as TextView
        val modifyTrackLength = tracksView.findViewById(R.id.imageViewModifyAllUsesTracks) as ImageView

        // Layout principale dell'elemento della track
        val constraintLayout: ConstraintLayout = tracksView.findViewById(
            R.id.constraintLayoutAllUsesTracks
        )
        // Utilizzato per il layout delle tracce espandibili
        val linearLayout: LinearLayout = tracksView.findViewById(R.id.linearLayoutExpandableTracks)

    }

    // Viene effettuato l'inflate per ogni elemento della RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_general_all_uses_tracks,
            parent,
            false
        )
        return ViewHolderTracks(view)
    }

    override fun getItemCount(): Int {
        return listTracks.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){  // Controllo per verificare il tipo di ViewHolder
            is ViewHolderTracks -> {
                /*Vengono popolate le diverse viste all'interno del ViewHolder
           con i dati corrispondenti all'elemento nella posizione specificata */
                holder.apply {
                    trackName.text = listTracks.get(position)?.name
                    trackLength.text = "${listTracks.get(position)?.length.toString()} km"
                    modifyTrackLength.setImageResource(R.drawable.modify_icon)

                    val expansion = listTracks.get(position)?.expansion!!

                    /* Imposta la visibilità di linearLayout in base allo stato di espansione ottenuto
                       dalla lista listTracks nella posizione corrente.
                       Se l'elemento è espanso, la visibilità viene impostata su View.VISIBLE,
                       altrimenti viene impostata su View.GONE
                    */
                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    // Quando viene cliccato, inverte lo stato di espansione dell'elemento in listTracks
                    // nella posizione corrente e notifica l'adapter
                    constraintLayout.setOnClickListener {
                        listTracks.get(position)?.expansion =
                            ! listTracks.get(position)?.expansion!!
                        notifyItemChanged(position)
                    }

                    // Listener sul click del bottone modifyTrackLength, al click viene eseguito il metodo showDialogModifyTrackLength
                    // passandogli l'item e la rispettiva posizione
                    modifyTrackLength.setOnClickListener {
                        /*
                        Richiama un metodo in cui l'utente può confermare o annullare la
                        modifica della lunghezza della traccia
                         */
                        showDialogModifyTrackLength(holder.itemView, position)
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor") // indicare al compilatore di ignorare gli avvisi relativi all'uso di un colore come valore costante
    private fun showDialogModifyTrackLength(item: View, position: Int) {
        val dialog = Dialog(item.context) // Creazione oggetto Dialog
        // Proprietà del Dialog
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        // Proprietà del Dialog
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_all_uses)

        // Vengono ottenute le referenze alle diverse viste all'interno del layout del dialog
        val modifyLengthEditText = dialog.findViewById(R.id.editTextAllUses) as EditText
        val titleDialog = dialog.findViewById(R.id.textViewTitleAllUses) as TextView
        val confirmFrame = dialog.findViewById(R.id.confirmFrameAllUses) as FrameLayout
        val cancelFrame = dialog.findViewById(R.id.cancelFrameAllUses) as FrameLayout

        titleDialog.text = "Modify track length"

        modifyLengthEditText.hint = "Track length (in km)"
        modifyLengthEditText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL

        val changeableText = dialog.findViewById(R.id.textViewChangeableAllUses) as TextView
        changeableText.text = "Inside the edit text only write a decimal number like 6.092 if the track length is 6.092km"

        /*
        Bottone di conferma per modificare la lunghezza della traccia
         */
        confirmFrame.setOnClickListener {
            /*
            Ogni volta che l'utente inserisce un valore che non può essere convertito in un valore Double,
            la finestra di dialogo non verrà chiusa e cambia anche il colore e il testo di una textView
            in cui c'è scritto qual è l'errore.
             */
            if(modifyLengthEditText.text.toString().toDoubleOrNull() == null){
                changeableText.setTextColor(ContextCompat.getColor(item.context, R.color.red_700))
                changeableText.text = "The value inside the edit text must be changed to confirm the modification. \nWrite a decimal number like 6.092 if the track length is 6.092km"
            }
            /*
            Quando l'utente imposta un valore appropriato all'interno dell'editText, l'adattatore richiama direttamente
            modifyTrackLength del viewModel assegnandogli un DataTrack e la nuova lunghezza.
            Il DataTrack specifico viene trovato grazie all'utilizzo di un altro metodo del viewModel
            chiamato filterTracksByName che richiede la stringa della query.
            Quindi, se filterTracksByName restituisce un elenco di DataTracks, può trovare il
            DataTrack che è stato cliccato, ottenendo l'elemento nella posizione cliccata,
            anche se l'elenco può essere diverso.
             */

            else{
                tracksViewModel.modifyTrackLength(
                    tracksViewModel.filterList(
                        inputQuery.value.toString()
                    ).value?.get(position)!!,
                    modifyLengthEditText.text.toString().toDouble()
                )
                notifyItemChanged(position) // aggiornare la vista dell'elemento nella RecyclerView
                dialog.dismiss() // Dialog chiuso
            }

        }
        // Bottone di annullamento modifiche
        cancelFrame.setOnClickListener {
            dialog.dismiss()  // Dialog chiuso
        }
        dialog.show() // Mostra il Dialog
    }

    /*
    Calls a method which allows the dynamic change of the list of the recyclerView.
     */
    /*
    fun filterNameByQuery(){
        setNewList(tracksViewModel.filterList(inputQuery.value.toString()))
    } */

    /*
    Aggiorna l'elenco degli elementi all'interno di recyclerView.
     */
    fun setNewList(newList: MutableList<DataTrack>) {
        listTracks.clear() // Svuota la lista
        listTracks.addAll(newList) // Aggiunge tutti gli elementi presenti nell'oggetto tracks
        notifyDataSetChanged() // Notifica all'adapter che i dati sono cambiati
    }


    // Metodo che aggiunge una nuova track utilizzando il tracksViewModel
    // e quindi aggiorna la visualizzazione della RecyclerView con la lista filtrata aggiornata,
    // se non è nulla
     @SuppressLint("SuspiciousIndentation")
     fun addNewTrack(newTrack: DataTrack) {
         tracksViewModel.addNewTrack(newTrack)
         val filteredList = tracksViewModel.filterList(inputQuery.value.toString()).value
         if (filteredList != null) {
             setNewList(filteredList)
         }
     }





}