package com.example.polimarche.Managers.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mobileprogramming.R




/*
class TeamMembersAdapterManager(
 */
class TeamMembersAdapterManager (var elementList : MutableList<DataModelTeamMembers>) : RecyclerView.Adapter<ViewHolder>() {

    private var viewList : MutableList<ViewHolder> = mutableListOf()

    inner class ViewHolderMembersList(memberView : View) : ViewHolder(memberView) {
        val identifyMember: TextView = memberView.findViewById(R.id.identifyMember)
        val detailsView: ImageView = memberView.findViewById(R.id.viewDetailsMember)
        init {
            println("Cliccato")
        }
    }
    inner class ViewHolderWorkshopAreas(workshopAreaView : View) : ViewHolder(workshopAreaView){
        val identifyArea: TextView = workshopAreaView.findViewById(R.id.identifyWorkshopArea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_workshop_area, parent, false)
            viewList.add(ViewHolderWorkshopAreas(view))
            ViewHolderWorkshopAreas(view)
        } else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_team_member, parent, false)
            viewList.add(ViewHolderMembersList(view))
            ViewHolderMembersList(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderWorkshopAreas -> {
                holder.apply {
                    identifyArea.text = elementList[position].identifyItem

                    holder.itemView.setOnClickListener {
                        when (findNextWorkshopAreaIndex(position)){
                            -1 -> {
                                for (index in position + 1  until viewList.size ){
                                    viewList[index].itemView.isVisible = false
                                    val params = viewList[index].itemView.layoutParams
                                    params.height = 0
                                    params.width = 0
                                    viewList[index].itemView.layoutParams = params
                                }
                            }
                            else -> {
                                for (index in position + 1  until findNextWorkshopAreaIndex(position) ){
                                    viewList[index].itemView.isVisible = false
                                    val params = viewList[index].itemView.layoutParams
                                    params.height = 0
                                    params.width = 0
                                    viewList[index].itemView.layoutParams = params
                                }
                            }
                        }
                    }
                    /*
                        for (index in position .. elementList.size){
                            if (elementList[position + 1].viewType != 0) {
                                println(position)
                                holder.itemView.visibility = View.VISIBLE
                            }
                        }

                    }
                    */
                }
            }
            is ViewHolderMembersList -> {
                holder.apply {
                    identifyMember.text = elementList[position].identifyItem
                    /*
                    holder.itemView.isVisible = false
                    val params = holder.itemView.layoutParams
                    params.height = 0
                    params.width = 0
                    holder.itemView.layoutParams = params

                    for (index in position .. 0){
                        if (elementList[index].viewType == 0)
                            elementList[index].

                    }
                    holder.itemView.setVisibility(View.VISIBLE);
                     */
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

    /*
    This method return the position in which is located the next
    workshop area tag inside the recycler view.
    In case there's not one more tag then the method return -1.
     */
    fun findNextWorkshopAreaIndex(position: Int): Int{
        if (position == 0) {
            for (index in 1 until elementList.size) {
                if (elementList[index].viewType == 0) {
                    return index
                }
            }
        }
        else {
            for (index in position + 1 until elementList.size ) {
                when (elementList[index].viewType){
                    0 -> return index
                }
                if (index == elementList.size) {return -1}
            }
        }
        return -1
    }


}




