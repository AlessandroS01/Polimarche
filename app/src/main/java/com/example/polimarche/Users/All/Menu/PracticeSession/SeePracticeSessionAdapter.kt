package com.example.polimarche.Users.All.Menu.PracticeSession

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R

class SeePracticeSessionAdapter(
    private val practiceSessionViewModel: PracticeSessionViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var listPracticeSession = practiceSessionViewModel.listPracticeSession


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
        return listPracticeSession.value?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listOfItems = listPracticeSession.value?.toMutableList()

        when(holder){
            is ViewHolderPracticeSession ->{
                holder.apply {
                    eventType.text = "Event: ${listOfItems?.get(position)?.eventType}"
                    date.text = "Date: ${listOfItems?.get(position)?.date.toString()}"
                    startingTime.text = "Starting time: ${listOfItems?.get(position)?.startingTime.toString()}"
                    endingTime.text = "Ending time: ${listOfItems?.get(position)?.endingTime.toString()}"
                    trackName.text = "Track: ${listOfItems?.get(position)?.trackName}"
                    weather.text = "Weather: ${listOfItems?.get(position)?.weather}"
                    trackCondition.text = "Track condition: ${listOfItems?.get(position)?.trackCondition}"
                    trackTemperature.text = "Track temperature: ${listOfItems?.get(position)?.trackTemperature.toString()}"
                    ambientPressure.text = "Ambient pressure: ${listOfItems?.get(position)?.ambientPressure.toString()}"
                    airTemperature.text = "Air temperature: ${listOfItems?.get(position)?.airTemperature.toString()}"

                    val expansion = listOfItems?.get(position)?.expansion
                    linearLayoutExpandable.visibility = if (expansion!!) View.VISIBLE else View.GONE

                    constraintTouchable.setOnClickListener{
                        listOfItems[position].expansion =
                            !listOfItems[position].expansion
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }

    /*
    fun setNewList(newList: MutableLiveData<MutableList<DataOccurringProblem>>){
        listPracticeSession = newList
        notifyDataSetChanged()
    }
     */
}