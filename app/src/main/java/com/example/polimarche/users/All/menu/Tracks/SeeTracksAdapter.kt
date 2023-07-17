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
        // Observer per l'oggetto LiveData listTracks che viene eseguito ogni volta che il valore di listTracks cambia
        tracksViewModel.listTracks.observeForever { tracks ->
            listTracks.clear()  // Svuota la lista
            listTracks.addAll(tracks) // Aggiunge tutti gli elementi presenti nell'oggetto session
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

        // Layout principale dell'elemento della traccia
        val constraintLayout: ConstraintLayout = tracksView.findViewById(
            R.id.constraintLayoutAllUsesTracks
        )
        // Utilizzato per il layout delle tracce espandibili
        val linearLayout: LinearLayout = tracksView.findViewById(R.id.linearLayoutExpandableTracks)

    }

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
        when (holder){
            is ViewHolderTracks -> {
                holder.apply {
                    trackName.text = listTracks.get(position)?.name
                    trackLength.text = "${listTracks.get(position)?.length.toString()} km"
                    modifyTrackLength.setImageResource(R.drawable.modify_icon)

                    val expansion = listTracks.get(position)?.expansion!!

                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    constraintLayout.setOnClickListener {
                        listTracks.get(position)?.expansion =
                            ! listTracks.get(position)?.expansion!!
                        notifyItemChanged(position)
                    }

                    modifyTrackLength.setOnClickListener {
                        /*
                        Recalls a method in which the user can confirm or cancel the
                        track length modification
                         */
                        showDialogModifyTrackLength(holder.itemView, position)
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun showDialogModifyTrackLength(item: View, position: Int) {
        val dialog = Dialog(item.context)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_all_uses)

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
        Confirm to modify the length of the track
         */
        confirmFrame.setOnClickListener {
            /*
            Whenever the user inserts a value that cannot be converted to a Double value,
            the dialog won't be dismissed and changes also the color and the text of a textView
            in which there's written what the error is.
             */
            if(modifyLengthEditText.text.toString().toDoubleOrNull() == null){
                changeableText.setTextColor(ContextCompat.getColor(item.context, R.color.red_700))
                changeableText.text = "The value inside the edit text must be changed to confirm the modification. \nWrite a decimal number like 6.092 if the track length is 6.092km"
            }
            /*
            When the user sets a proper value inside the edit text, the adapter recalls directly
            modifyTrackLength method of the viewModel giving it a DataTrack and the new length.
            The DataTrack given is found thanks to the use of an other method of the viewModel
            called filterTracksByName that requires the string of the query.
            So if filterTracksByName returns a list of DataTracks, it can find the exact
            DataTrack that was touched getting the element at the position clicked,
            even though the list can be different.
             */

            else{
                tracksViewModel.modifyTrackLength(
                    tracksViewModel.filterList(
                        inputQuery.value.toString()
                    ).value?.get(position)!!,
                    modifyLengthEditText.text.toString().toDouble()
                )
                notifyItemChanged(position)
                dialog.dismiss()
            }

        }
        cancelFrame.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    /*
    Calls a method which allows the dynamic change of the list of the recyclerView.
     */
    /*
    fun filterNameByQuery(){
        setNewList(tracksViewModel.filterList(inputQuery.value.toString()))
    } */

    /*
    This function is used for filtering the list of tracks based on a search query.
     */
    fun setNewList(newList: MutableList<DataTrack>) {
        listTracks.clear()
        listTracks.addAll(newList)
        notifyDataSetChanged()
    }


     @SuppressLint("SuspiciousIndentation")
     fun addNewTrack(newTrack: DataTrack) {
         tracksViewModel.addNewTrack(newTrack)
         val filteredList = tracksViewModel.filterList(inputQuery.value.toString()).value
         if (filteredList != null) {
             setNewList(filteredList)
         }
     }





}