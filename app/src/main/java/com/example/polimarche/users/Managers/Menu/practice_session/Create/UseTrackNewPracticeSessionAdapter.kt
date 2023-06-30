package com.example.polimarche.users.managers.menu.practice_session.Create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.practice_session.DataPracticeSession
import com.example.polimarche.data_container.track.DataTrack
import com.example.polimarche.data_container.track.TracksViewModel

class UseTrackNewPracticeSessionAdapter(
    private val tracksViewModel: TracksViewModel,
    private val listener: OnTrackClickListener
):  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listTracks: MutableList<DataTrack> = mutableListOf()

    interface OnTrackClickListener{
        fun onTrackClickListener(trackClicked: DataTrack)
    }

    inner class ViewHolderTracksCreatePracticeSession(
        tracksPracticeSessionView : View
    ) : RecyclerView.ViewHolder(tracksPracticeSessionView), View.OnClickListener{

        val trackName: TextView = tracksPracticeSessionView.findViewById(R.id.trackNameAddPS)
        val trackLength: TextView = tracksPracticeSessionView.findViewById(R.id.trackLengthAddPS)

        init {
            tracksPracticeSessionView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onTrackClickListener(
                    listTracks.get(position)!!
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_managers_add_practice_session_new_track, parent, false
        )
        return ViewHolderTracksCreatePracticeSession(view)
    }

    override fun getItemCount(): Int {
        return listTracks.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderTracksCreatePracticeSession ->{
                holder.apply {
                    trackName.text = "${listTracks?.get(position)?.name}"
                    trackLength.text = "${listTracks?.get(position)?.length} km"
                }
            }
        }
    }

    /*
    Sets the new list to be shown inside the recyclerView.
     */
    fun changeListTracksOnClick(newList: MutableLiveData<MutableList<DataTrack>>) {
        listTracks.clear()
        newList.value?.let { listTracks.addAll(it) }
        notifyDataSetChanged()
    }

}