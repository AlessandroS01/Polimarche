package com.example.polimarche.Managers.M_Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R

class SpringsAdapter(
    private var elementList: MutableList<DataSprings>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderSpring(springView : View) : RecyclerView.ViewHolder(springView){
        val springCode: TextView = springView.findViewById(R.id.springCode)
        val springCodification: TextView = springView.findViewById(R.id.codificationSpring)
        val springHeight: TextView = springView.findViewById(R.id.heightSprings)
        val springArbStiffness: TextView = springView.findViewById(R.id.arbStiffnessSprings)
        val springArbPosition: TextView = springView.findViewById(R.id.arbPositionSprings)

        val linearLayout = springView.findViewById<LinearLayout>(R.id.linearLayoutExpandableSpring)
        val costraintLayout: androidx.constraintlayout.widget.ConstraintLayout = springView.findViewById(R.id.costraintLayoutSpring)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_springs, parent, false)
        return ViewHolderSpring(view)
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderSpring ->{
                holder.apply {
                    springCode.text = elementList[position].code.toString()
                    springCodification.text = elementList[position].codification
                    springHeight.text = elementList[position].height.toString()
                    springArbStiffness.text = elementList[position].arb_stiffness
                    springArbPosition.text = elementList[position].arb_position

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
    fun setFilteredList(filteredList: MutableList<DataSprings>){
        this.elementList = filteredList
        notifyDataSetChanged()
    }



}