package com.example.polimarche.Managers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mobileprogramming.R


class TeamMembersAdapterManager (var teamMembersList: List<TeamMember>,
                                 var workshopAreasList: List<WorkshopArea>) : RecyclerView.Adapter<ViewHolder>() {

    private val elementsList = teamMembersList + workshopAreasList

    class MembersList(memberView : View) : ViewHolder(memberView) {
        var identifyMember: TextView = memberView.findViewById(R.id.identifyMember)
    }
    class WorkshopAreas(workshopAreaView : View) : ViewHolder(workshopAreaView) {
        var identifyArea: TextView = workshopAreaView.findViewById(R.id.identifyWorkshopArea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_workshop_area, parent, false)
            WorkshopAreas(view)
        } else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_team_member, parent, false)
            MembersList(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                holder.apply {
                    identifyArea.text = workshopAreasList[position].area
                }
            }
            1 -> {
                holder.apply {
                    identifyMember.text = teamMembersList[position].identification
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (elementsList[position]) {
            is WorkshopArea -> 0
            is TeamMember -> 1
            else -> -1
        }
    }

    override fun getItemCount(): Int {
        return elementsList.size
    }


}