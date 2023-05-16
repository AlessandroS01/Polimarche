package com.example.polimarche.users.managers.menu.setup.create.choosing_wheels

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.data_container.wheel.WheelViewModel

class WheelsCodificationAdapter(
    private val wheelViewModel: WheelViewModel,
    private val listener: OnWheelsCodificationClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mappingCodificationQuantityWheel = wheelViewModel.mapQuantityCodification()

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
                listener.onCodificationClick(mappingCodificationQuantityWheel.keys.elementAt(position))
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_wheels_codification, parent, false)
        return ViewHolderWheelCodification(view)
    }

    override fun getItemCount(): Int {
        return mappingCodificationQuantityWheel.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderWheelCodification ->{
                holder.apply {
                    val codificationMapped = mappingCodificationQuantityWheel.keys.elementAt(position)
                    val quantityMapped = mappingCodificationQuantityWheel[codificationMapped]
                    codification.text = "Cod: ${codificationMapped}"
                    quantity.text = "Q: ${quantityMapped}"
                }
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setNewMap(newMap: MutableMap<String, Int>){
        this.mappingCodificationQuantityWheel = newMap
        notifyDataSetChanged()
    }
}