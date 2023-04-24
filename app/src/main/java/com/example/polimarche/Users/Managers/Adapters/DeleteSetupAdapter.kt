package com.example.polimarche.Users.Managers.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataSetup

class DeleteSetupAdapter (
    private var setupList : MutableList<DataSetup>,
    private val listener: OnSetupCodeClickListener
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    interface OnSetupCodeClickListener{
        fun onSetupCodeClickListener(position: Int)
    }

    inner class ViewHolderDeleteSetup(setupView : View) : RecyclerView.ViewHolder(setupView), View.OnClickListener{
        val setupCode: TextView = setupView.findViewById(R.id.itemDeleteSetupCode)
        init{
            setupView.setOnClickListener(this)
        }
        
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onSetupCodeClickListener(position)
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<DataSetup>){
        this.setupList = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_delete_setup, parent, false)
        return ViewHolderDeleteSetup(view)
    }

    override fun getItemCount(): Int {
        return setupList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolderDeleteSetup -> {
                holder.apply {
                    setupCode.text = "Setup " + setupList[position].code.toString()
                }
            }
        }
    }


}