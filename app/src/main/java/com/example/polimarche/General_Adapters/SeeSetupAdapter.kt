package com.example.polimarche.General_Adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataSeeSetupCode
import com.example.polimarche.DetailsSetup

class SeeSetupAdapter (private var elementList : MutableList<DataSeeSetupCode>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderSetup(setupView : View) : RecyclerView.ViewHolder(setupView) {
        val setupCode: TextView = setupView.findViewById(R.id.seeSetupSetupCode)
        val detailSetup: ImageView = setupView.findViewById(R.id.seeSetupSetupDetails)
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<DataSeeSetupCode>){
        this.elementList = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_see_setup, parent, false)
        return ViewHolderSetup(view)
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolderSetup -> {
                holder.apply {
                    setupCode.text = "Setup " + elementList[position].code.toString()
                    detailSetup.setImageResource(R.drawable.visibility_icon)
                    detailSetup.setOnClickListener {
                        Intent(holder.itemView.context, DetailsSetup::class.java).apply {
                            it.context.startActivity(this)
                        }
                    }
                }
            }
        }
    }


}