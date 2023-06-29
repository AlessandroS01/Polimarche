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


    private var listPracticeSession: MutableList<DataPracticeSession> = mutableListOf()

    init {
        practiceSessionViewModel.listPracticeSession.observeForever { session ->
            listPracticeSession.clear()
            listPracticeSession.addAll(session)
            notifyDataSetChanged()
        }
    }

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

        val constraintTouchable: ConstraintLayout = practiceSessionView.findViewById(
            R.id.constraintLayoutSeePS
        )
        val linearLayoutExpandable: LinearLayout = practiceSessionView.findViewById(
            R.id.linearLayoutExpandablePS
        )

    }

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
        when(holder){
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
                    linearLayoutExpandable.visibility = if (expansion) View.VISIBLE else View.GONE

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
    Restores the default list of the recyclerView.
     */

    fun restoreListToDefault(){
        setNewList(practiceSessionViewModel.listPracticeSession.value?.toMutableList()!!)
    }



    /*
    Filter the list of practice session the one that occurred on the day
    given as input and in which the event was the one selected trough the
    radioButton.
     */

    fun filterListByEventChecked(query: String, eventType: String) {
        listPracticeSession = practiceSessionViewModel.listPracticeSession.value?.toMutableList()!!
        val listFiltered = listPracticeSession.filter {
            it.date.toString() == query && it.eventType.lowercase() == eventType.lowercase()
        }.toMutableList()
        setNewList(listFiltered)
    }


    /*
    Filter the list of practice session the one that occurred on the day
    given as input.
     */

    fun filterListWithoutEvents(query: String){
        listPracticeSession = practiceSessionViewModel.listPracticeSession.value?.toMutableList()!!
        val listFiltered = listPracticeSession.filter {
            it.date.toString() == query
        }.toMutableList()
        setNewList(listFiltered)
    }

    /*
    Refresh the list of items inside the recyclerView.
     */
    fun setNewList(newList: MutableList<DataPracticeSession>){
        listPracticeSession.clear()
        listPracticeSession.addAll(newList)
        notifyDataSetChanged()
    }
}