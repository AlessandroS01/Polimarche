package com.example.polimarche.Users.Managers.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R

class SpringsCodificationAdapter(
    private var elementList: MutableList<String>,
    private val listener: OnSpringsCodificationClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnSpringsCodificationClickListener{
        fun onCodificationClick(codification: String)
    }

    inner class ViewHolderSpringsCodification(
        springCodificationView : View
    ) : RecyclerView.ViewHolder(springCodificationView), View.OnClickListener {
        val codification: TextView = springCodificationView.findViewById(R.id.springsCodification)
        init {
            springCodificationView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onCodificationClick(elementList[position])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_springs_codification, parent, false)
        return ViewHolderSpringsCodification(view)
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderSpringsCodification ->{
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
    fun setFilteredList(filteredList: MutableList<String>){
        this.elementList = filteredList
        notifyDataSetChanged()
    }
}