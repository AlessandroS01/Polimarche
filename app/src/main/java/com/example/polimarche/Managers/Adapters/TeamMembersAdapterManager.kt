package com.example.polimarche.Managers.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mobileprogramming.R

/*
class TeamMembersAdapterManager(
 */
class TeamMembersAdapterManager (var elementList : MutableList<DataModelTeamMembers>) : RecyclerView.Adapter<ViewHolder>() {


    class ViewHolderMembersList(memberView : View) : ViewHolder(memberView) {
        val identifyMember: TextView = memberView.findViewById(R.id.identifyMember)
        val detailsView: ImageView = memberView.findViewById(R.id.viewDetailsMember)
    }
    class ViewHolderWorkshopAreas(workshopAreaView : View) : ViewHolder(workshopAreaView) {
        val identifyArea: TextView = workshopAreaView.findViewById(R.id.identifyWorkshopArea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_workshop_area, parent, false)
            ViewHolderWorkshopAreas(view)
        } else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_team_member, parent, false)
            ViewHolderMembersList(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderWorkshopAreas -> {
                holder.apply {
                    identifyArea.text = elementList[position].identifyItem
                }
            }
            is ViewHolderMembersList -> {
                holder.apply {
                    identifyMember.text = elementList[position].identifyItem
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return elementList[position].viewType
    }

    override fun getItemCount(): Int {
        return elementList.size
    }


}