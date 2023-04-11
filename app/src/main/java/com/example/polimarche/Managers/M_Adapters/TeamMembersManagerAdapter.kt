package com.example.polimarche.Managers.M_Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mobileprogramming.R
import com.example.polimarche.DetailsMember


/*
class TeamMembersAdapterManager(
 */
class TeamMembersManagerAdapter (
    private var elementList : MutableList<DataModelTeamMembers>,
    private val listener: OnWorkshopAreaClickListener) : RecyclerView.Adapter<ViewHolder>() {


    interface OnWorkshopAreaClickListener{
        fun onWorkshopAreaClick(position: Int)
    }

    class ViewHolderMembersList(memberView : View) : ViewHolder(memberView) {
        val identifyMember: TextView = memberView.findViewById(R.id.identifyMember)
        val detailsView: ImageView = memberView.findViewById(R.id.viewDetailsMember)
    }
    inner class ViewHolderWorkshopAreas(
        workshopAreaView : View
    ) : ViewHolder(workshopAreaView), View.OnClickListener{
        val identifyArea: TextView = workshopAreaView.findViewById(R.id.identifyWorkshopArea)
        init {
            workshopAreaView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onWorkshopAreaClick(position)
            }
        }
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
                    detailsView.setImageResource(R.drawable.visibility_icon)
                    holder.detailsView.setOnClickListener {
                        Intent(it.context, DetailsMember::class.java).apply {
                            it.context.startActivity(this)
                        }
                    }
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




