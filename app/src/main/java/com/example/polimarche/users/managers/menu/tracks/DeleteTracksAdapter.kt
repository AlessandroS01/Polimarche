package com.example.polimarche.users.managers.menu.tracks

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.data_container.track.TracksViewModel

class DeleteTracksAdapter(
    private val tracksViewModel: TracksViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*
    Utilizzata per tenere d'occhio il valore della query creata dalla searchView quando un utente
    scrive su essa.
     */
    private var inputQuery: MutableLiveData<String> = MutableLiveData("")
    fun setQuery(query: String){
        inputQuery.value = query
    }

    /*
    Contiene la lista completa delle tracks del viewModel
     */
    private var listTracks: MutableList<DataTrack> = mutableListOf()
    init {
        tracksViewModel.listTracks.observeForever { tracks ->
            listTracks.clear()
            listTracks.addAll(tracks)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolderTracks(
        tracksView: View
    ): RecyclerView.ViewHolder(tracksView){

        val trackName = tracksView.findViewById(R.id.trackName) as TextView
        val trackLength = tracksView.findViewById(R.id.trackLength) as TextView
        val removeTrack = tracksView.findViewById(R.id.imageViewModifyAllUsesTracks) as ImageView
        val textView = tracksView.findViewById(R.id.textViewChangeableTracks) as TextView

        val constraintLayout: ConstraintLayout = tracksView.findViewById(
            R.id.constraintLayoutAllUsesTracks
        )
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
        return listTracks.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolderTracks -> {
                holder.apply {
                    trackName.text = listTracks.get(position)?.name
                    trackLength.text = "${listTracks.get(position)?.length.toString()} km"
                    removeTrack.setImageResource(R.drawable.remove_general_small_icon)
                    textView.text = "Remove track"

                    val expansion = listTracks.get(position)?.expansion!!

                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    constraintLayout.setOnClickListener {
                        listTracks.get(position)?.expansion =
                            ! listTracks.get(position)?.expansion!!
                        notifyItemChanged(position)
                    }

                    removeTrack.setOnClickListener {
                        /*
                        Richiama un metodo dove l'utente può confermare o cancellare
                        la modifica sulla lunghezza della track.
                         */
                        showDialogRemoveTrack(holder.itemView, position)
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun showDialogRemoveTrack(item: View, position: Int) {
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

        val editTextToNonVisible = dialog.findViewById(R.id.editTextAllUses) as EditText
        editTextToNonVisible.visibility = View.GONE

        val textViewChangeableToNonVisible = dialog.findViewById(R.id.textViewChangeableAllUses) as TextView
        textViewChangeableToNonVisible.visibility = View.GONE
        
        val titleDialog = dialog.findViewById(R.id.textViewTitleAllUses) as TextView
        val confirmFrame = dialog.findViewById(R.id.confirmFrameAllUses) as FrameLayout
        val cancelFrame = dialog.findViewById(R.id.cancelFrameAllUses) as FrameLayout

        titleDialog.text = "Remove track"


        /*
        Conferma di rimuovere la track selezionata
         */
        confirmFrame.setOnClickListener {
            val track = tracksViewModel.filterList(inputQuery.value.toString()).value?.get(position)
            if (track != null) {
                tracksViewModel.removeTrack(track)
            }
            setNewList(tracksViewModel.listTracks.value?.toMutableList()!!)
            dialog.dismiss()
        }
        cancelFrame.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    /*
    Metodo che permette il cambiamento dinamico della list della recyclerView
     */
    fun filterNameByQuery(){
        setNewList(tracksViewModel.filterList(inputQuery.value.toString()).value?.toMutableList()!!)
    }

    /*
    Inflate all'interno di newList da parte di listTracks che contiene tutti i nomi degli
    elementi che contengono inputQuery.
     */
    fun setNewList(newList: MutableList<DataTrack>){
        listTracks.clear()
        listTracks.addAll(newList)
        notifyDataSetChanged()
    }



}