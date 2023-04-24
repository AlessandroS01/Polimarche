package com.example.polimarche.Users.Managers.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataDamper

class DampersAdapter(
    private var elementList: MutableList<DataDamper>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderDamper(damperView : View) : RecyclerView.ViewHolder(damperView){
        val damperCode: TextView = damperView.findViewById(R.id.damperCode)
        val damperHSR: TextView = damperView.findViewById(R.id.hsrDamper)
        val damperHSC: TextView = damperView.findViewById(R.id.hscDamper)
        val damperLSR: TextView = damperView.findViewById(R.id.lsrDamper)
        val damperLSC: TextView = damperView.findViewById(R.id.lscDamper)

        val linearLayout = damperView.findViewById<LinearLayout>(R.id.linearLayoutExpandableDamper)
        val costraintLayout: androidx.constraintlayout.widget.ConstraintLayout = damperView.findViewById(R.id.costraintLayoutDamper)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_dampers, parent, false)
        return ViewHolderDamper(view)
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderDamper ->{
                holder.apply {
                    damperCode.text = elementList[position].code.toString()
                    damperHSR.text = elementList[position].hsr.toString()
                    damperHSC.text = elementList[position].hsc.toString()
                    damperLSR.text = elementList[position].lsr.toString()
                    damperLSC.text = elementList[position].lsc.toString()

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
    fun setFilteredList(filteredList: MutableList<DataDamper>){
        this.elementList = filteredList
        notifyDataSetChanged()
    }



}