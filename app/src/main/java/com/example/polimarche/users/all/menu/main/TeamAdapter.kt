package com.example.polimarche.users.all.menu.main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.polimarche.R
import com.example.polimarche.data_container.team_members_workshop.DataTeamMember
import com.example.polimarche.data_container.team_members_workshop.DataWorkshopArea
import com.example.polimarche.data_container.team_members_workshop.TeamViewModel


class TeamAdapter (
    teamViewModel: TeamViewModel,
    private val listener: OnWorkshopAreaClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    /*
    Used to keep an eye on the list of members
     */
    private val listMembers: MutableList<DataTeamMember> =
        teamViewModel.listMembers.value?.toMutableList()!!

    /*
    Used to keep an eye on the list of departments
     */
    private val listDepartments: MutableList<DataWorkshopArea> =
        teamViewModel.listDepartments.value?.toMutableList()!!

    /*
    This is the list the recyclerView will show.
    For this reason everytime the size of listMembers changes, it will be redefined
    trough the method changeSortedList()
     */
    private var sortedList = getSortedItems(listDepartments, listMembers)

    /*
    This list contains all the team members that are currently "invisible"
    after the click on a department.
     */
    private var membersToggled : MutableList<DataTeamMember> = mutableListOf()

    /*
    Orders the items inside the recycler view.
    Indeed it automatically creates a sorted list in which every team member
    have a spot right below the department they're part of.
     */
    private fun getSortedItems(
        listDepartments: MutableList<DataWorkshopArea>,
        listMembers: MutableList<DataTeamMember>
    ): MutableList<Any> {
        val sortedList: MutableList<Any> = mutableListOf()

        listDepartments.forEach {
            // Here we add every department area to the sorted list
            sortedList.add(it)
            val department = it.department
            // Here we add all the team members that are part of the department added
            sortedList.addAll(
                listMembers.filter {
                    it.workshopArea == department
                }
            )
        }
        return sortedList
    }

    /*
    These 2 variables are used to understand if an element of the sorted list
    is an instance of "DataTeamMember" or "DataWorkshopArea".
     */
    private val ITEM_TYPE_MEMBER = 1
    private val ITEM_TYPE_WORKSHOP = 2

    /*
    This interface is implemented inside "TeamFragment" class and
    is used to understand which particular element of "DataWorkshopArea",
    which is stored inside the sortedList(), was clicked.
     */
    interface OnWorkshopAreaClickListener{
        fun onWorkshopAreaClick(workshopAreaClicked: String)
    }

    inner class ViewHolderMembersList(memberView : View) : ViewHolder(memberView) {
        val identifyMember: TextView = memberView.findViewById(R.id.identifyMember)
        val detailsView: ImageView = memberView.findViewById(R.id.viewDetailsMember)

        @SuppressLint("SetTextI18n")
        /*
        This method is called inside the onBindViewHolder and set the 2 attributes
        by searching inside the sortedList() the element that is shown inside the recyclerView.
         */
        fun bind(item: Any){
            val teamMember = listMembers.filter { it == item }[0]
            identifyMember.text = "${teamMember.matriculationNumber}:  ${teamMember.firstName}"
            detailsView.setImageResource(R.drawable.visibility_icon)

            detailsView.setOnClickListener {
                Intent(it.context, DetailsMemberActivity::class.java).apply {
                    it.context.startActivity(this)
                }
            }
        }
    }

    inner class ViewHolderWorkshopAreas(
        workshopAreaView : View
    ) : ViewHolder(workshopAreaView), View.OnClickListener{
        private val identifyArea: TextView = workshopAreaView.findViewById(R.id.identifyWorkshopArea)

        /*
        This method is called inside the onBindViewHolder and set the 2 attributes
        by searching inside the sortedList() the element that is shown inside the recyclerView.
         */
        fun bind(item: Any){
            val workshopArea = listDepartments.filter { item == it }[0]
            identifyArea.text = workshopArea.department
        }
        /*
        The init{} and the onClick{} method return the department that was clicked.
        In particular, inside the onClick{} method, we find which element of the sorted
        list was clicked.
         */
        init {
            workshopAreaView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val workshopArea: DataWorkshopArea = listDepartments.filter {
                    sortedList[position] == it }[0]
                listener.onWorkshopAreaClick(workshopArea.department)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType){
            ITEM_TYPE_WORKSHOP ->{
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_general_workshop_area, parent, false
                )
                ViewHolderWorkshopAreas(view)
            }
            ITEM_TYPE_MEMBER ->{
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_general_team_member, parent, false
                )
                ViewHolderMembersList(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    /*
    This method is used to understand if the different elements of the sorted list
    are instance of "DataTeamMember" or "DataWorkshopArea".
     */
    override fun getItemViewType(position: Int): Int {
        return when (sortedList[position]) {
            is DataTeamMember -> ITEM_TYPE_MEMBER
            is DataWorkshopArea -> ITEM_TYPE_WORKSHOP
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun getItemCount(): Int {
        return sortedList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderWorkshopAreas -> {
                val item = sortedList[position]
                holder.bind(item)
            }
            is ViewHolderMembersList -> {
                val item = sortedList[position]
                holder.bind(item)
            }
        }
    }


    /*
    Method used for toggle visibility of the team members after the click on
    the department.
    It's directly recalled from TeamFragment fragment.
     */
    fun switchingVisibility(department : String){
        if (membersToggled.none{ it.workshopArea == department }) {
            membersToggled += listMembers.filter {
                it.workshopArea == department
            }.toMutableList()
            listMembers -= membersToggled.toSet()

            changeSortedList()
        }
        else {
            listMembers.addAll(membersToggled.filter {
                it.workshopArea == department
            })
            membersToggled -= listMembers.toSet()

            changeSortedList()
        }
    }

    /*
    Used to redefine the new list of elements after the size of
    listMembers changes.
     */
    private fun changeSortedList(){
        sortedList = getSortedItems(listDepartments, listMembers)
        println(sortedList.size)
        notifyDataSetChanged()
    }

}




