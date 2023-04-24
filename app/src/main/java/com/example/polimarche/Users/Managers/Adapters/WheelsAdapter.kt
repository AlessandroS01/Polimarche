package com.example.polimarche.Users.Managers.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataWheel

class WheelsAdapter(
    private var elementList: MutableList<DataWheel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderWheel(wheelView : View) : RecyclerView.ViewHolder(wheelView){
        val wheelCode: TextView = wheelView.findViewById(R.id.wheelCode)
        val wheelPressure: TextView = wheelView.findViewById(R.id.wheelPressure)
        val wheelCamber: TextView = wheelView.findViewById(R.id.wheelCamber)
        val wheelToe: TextView = wheelView.findViewById(R.id.wheelToe)

        val linearLayout = wheelView.findViewById<LinearLayout>(R.id.linearLayoutExpandable)
        val costraintLayout: androidx.constraintlayout.widget.ConstraintLayout = wheelView.findViewById(R.id.costraintLayout)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_wheels, parent, false)
        return ViewHolderWheel(view)
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderWheel ->{
                holder.apply {
                    wheelCode.text = elementList[position].code.toString()
                    wheelPressure.text = "${elementList[position].pressure} bar"
                    wheelCamber.text = elementList[position].camber
                    wheelToe.text = elementList[position].toe

                    val expansion = elementList[position].expansion


                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    costraintLayout.setOnClickListener {
                        elementList[position].expansion = !elementList[position].expansion
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<DataWheel>){
        this.elementList = filteredList
        notifyDataSetChanged()
    }



}