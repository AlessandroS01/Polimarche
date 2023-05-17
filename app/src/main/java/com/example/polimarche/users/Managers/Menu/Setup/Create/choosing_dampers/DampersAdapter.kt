package com.example.polimarche.users.managers.menu.setup.create.choosing_dampers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.data_container.damper.DamperViewModel
import com.example.polimarche.data_container.damper.DataDamper

class DampersAdapter(
    private val position: String, //used to get on what it should filter the entire list of dampers
    private var damperViewModel: DamperViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var damperList = when(position){
        "Front" -> {
            damperViewModel.getFrontDampers()
        }
        "End" -> {
            damperViewModel.getEndDampers()
        }
        else -> {
            if (
                damperViewModel.getFrontDamperParametersStocked() != null
                &&
                damperViewModel.getBackDamperParametersStocked() != null
            ){
                 damperViewModel.getStockedParameters()
            }
            else
                null
        }
    }

    inner class ViewHolderDamper(damperView : View) : RecyclerView.ViewHolder(damperView){
        val damperCode: TextView = damperView.findViewById(R.id.damperCode)
        val damperHSR: TextView = damperView.findViewById(R.id.hsrDamper)
        val damperHSC: TextView = damperView.findViewById(R.id.hscDamper)
        val damperLSR: TextView = damperView.findViewById(R.id.lsrDamper)
        val damperLSC: TextView = damperView.findViewById(R.id.lscDamper)
        val damperPosition: TextView = damperView.findViewById(R.id.positionDamper)

        val linearLayout = damperView.findViewById<LinearLayout>(R.id.linearLayoutExpandableDamper)
        val constraintLayout: androidx.constraintlayout.widget.ConstraintLayout =
            damperView.findViewById(R.id.costraintLayoutDamper)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_managers_dampers,
            parent,
            false
        )
        return ViewHolderDamper(view)
    }

    override fun getItemCount(): Int {
        return if (damperList != null)
            damperList?.size!!
        else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if( damperList != null) {
            when (holder) {
                is ViewHolderDamper -> {
                    holder.apply {
                        damperCode.text = damperList!![position].code.toString()
                        damperHSR.text = damperList!![position].hsr.toString()
                        damperHSC.text = damperList!![position].hsc.toString()
                        damperLSR.text = damperList!![position].lsr.toString()
                        damperLSC.text = damperList!![position].lsc.toString()
                        damperPosition.text = damperList!![position].end

                        val expansion = damperList!![position].expansion


                        linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                        constraintLayout.setOnClickListener {
                            damperList!![position].expansion = !damperList!![position].expansion
                            notifyItemChanged(position)
                        }
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
    fun setNewList(newList: MutableList<DataDamper>){
        if (newList.isNotEmpty()){
            this.damperList = newList
            notifyDataSetChanged()
        }
        else{
            this.damperList = null
            notifyDataSetChanged()
        }
    }



}