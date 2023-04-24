package com.example.polimarche.Users.Managers.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R

class WheelsCodificationAdapter(
    private var codificationList: MutableList<String>,
    private var quantityCodification: MutableList<Int>,
    private val listener: OnWheelsCodificationClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnWheelsCodificationClickListener{
        fun onCodificationClick(codification: String)
    }

    inner class ViewHolderWheelCodification(wheelCodificationView : View) : RecyclerView.ViewHolder(wheelCodificationView), View.OnClickListener {
        val codification: TextView = wheelCodificationView.findViewById(R.id.wheelsCodification)
        val quantity: TextView = wheelCodificationView.findViewById(R.id.wheelsQuantity)
        init {
            wheelCodificationView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onCodificationClick(codificationList[position])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_wheels_codification, parent, false)
        return ViewHolderWheelCodification(view)
    }

    override fun getItemCount(): Int {
        return codificationList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderWheelCodification ->{
                holder.apply {
                    codification.text = "Cod: ${codificationList[position]}"
                    quantity.text = "Q: ${quantityCodification[position]}"
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
        this.codificationList = filteredList
        notifyDataSetChanged()
    }
}