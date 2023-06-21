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
    Used to keep an eye on the value of the query created by the searchView when
    a user writes on it.
     */
    private var inputQuery: MutableLiveData<String> = MutableLiveData("")
    fun setQuery(query: String){
        inputQuery.value = query
    }

    /*
    Contains the complete list of tracks of the viewModel
     */
    private var listTracks : MutableLiveData<MutableList<DataTrack>> = tracksViewModel.listTracks

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
        return listTracks.value?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolderTracks -> {
                holder.apply {
                    trackName.text = listTracks.value?.get(position)?.name
                    trackLength.text = "${listTracks.value?.get(position)?.length.toString()} km"
                    removeTrack.setImageResource(R.drawable.remove_general_small_icon)
                    textView.text = "Remove track"

                    val expansion = listTracks.value?.get(position)?.expansion!!

                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    constraintLayout.setOnClickListener {
                        listTracks.value?.get(position)?.expansion =
                            ! listTracks.value?.get(position)?.expansion!!
                        notifyItemChanged(position)
                    }

                    removeTrack.setOnClickListener {
                        /*
                        Recalls a method in which the user can confirm or cancel the
                        track length modification
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
        Confirm to modify the length of the track
         */
        confirmFrame.setOnClickListener {
            tracksViewModel.removeTrack(
                tracksViewModel.filterTracksByName(
                    inputQuery.value.toString()
                ).value?.get(position)!!
            )
            setNewList(tracksViewModel.listTracks)
            dialog.dismiss()

        }
        cancelFrame.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    /*
    Calls a method which allows the dynamic change of the list of the recyclerView.
     */
    fun filterNameByQuery(){
        setNewList(tracksViewModel.filterTracksByName(inputQuery.value.toString()))
    }

    /*
    Inflate inside listTracks a newList in which all the elements' name
    contain inputQuery.
     */
    private fun setNewList(newList: MutableLiveData<MutableList<DataTrack>>){
        listTracks = newList
        notifyDataSetChanged()
    }



}