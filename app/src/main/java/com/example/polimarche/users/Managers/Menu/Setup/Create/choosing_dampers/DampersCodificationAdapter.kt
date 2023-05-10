package com.example.polimarche.users.managers.menu.setup.create.choosing_dampers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R

class DampersCodificationAdapter(
    private var elementList: MutableList<Int>,
    private val listener: OnDampersCodificationClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnDampersCodificationClickListener{
        fun onCodificationClick(codification: Int)
    }

    inner class ViewHolderDamperCodification(damperCodificationView : View) : RecyclerView.ViewHolder(damperCodificationView), View.OnClickListener {
        val codification: TextView = damperCodificationView.findViewById(R.id.dampersCodification)
        init {
            damperCodificationView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onCodificationClick(elementList[position])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_dampers_codification, parent, false)
        return ViewHolderDamperCodification(view)
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderDamperCodification ->{
                holder.apply {
                    codification.text = "Cod: ${elementList[position]}"
                }
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<Int>){
        this.elementList = filteredList
        notifyDataSetChanged()
    }
}