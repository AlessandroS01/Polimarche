package com.example.polimarche.users.managers.menu.setup.create.choosing_springs

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.data_container.spring.DataSpring
import com.example.polimarche.data_container.spring.SpringViewModel

class SpringsAdapter(
    private val position: String,
    private val springViewModel: SpringViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var listSpring = when(position){
        "Front" -> {
            springViewModel.getFrontSpringList()
        }
        "End" -> {
            springViewModel.getBackSpringList()
        }
        else -> {
            if (
                springViewModel.getFrontSpringParametersStocked() != null
                &&
                springViewModel.getBackSpringParametersStocked() != null
            ){
                 springViewModel.getStockedParameters()
            }
            else
                null
        }
    }

    inner class ViewHolderSpring(springView : View) : RecyclerView.ViewHolder(springView){
        val springCode: TextView = springView.findViewById(R.id.springCode)
        val springCodification: TextView = springView.findViewById(R.id.codificationSpring)
        val springHeight: TextView = springView.findViewById(R.id.heightSprings)
        val springArbStiffness: TextView = springView.findViewById(R.id.arbStiffnessSprings)
        val springArbPosition: TextView = springView.findViewById(R.id.arbPositionSprings)
        val springPosition: TextView = springView.findViewById(R.id.positionSprings)

        val linearLayout = springView.findViewById<LinearLayout>(R.id.linearLayoutExpandableSpring)
        val constraintLayout: androidx.constraintlayout.widget.ConstraintLayout = springView.findViewById(R.id.costraintLayoutSpring)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_springs, parent, false)
        return ViewHolderSpring(view)
    }

    override fun getItemCount(): Int {
        return if (listSpring != null)
            listSpring?.size!!
        else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if( listSpring != null) {
            when (holder) {
                is ViewHolderSpring -> {
                    holder.apply {
                        springCode.text = listSpring!![position].code.toString()
                        springCodification.text = listSpring!![position].codification
                        springHeight.text = listSpring!![position].height.toString()
                        springArbStiffness.text = listSpring!![position].arb_stiffness
                        springArbPosition.text = listSpring!![position].arb_position
                        springPosition.text = listSpring!![position].end

                        val expansion = listSpring!![position].expansion


                        linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                        constraintLayout.setOnClickListener {
                            listSpring!![position].expansion = !listSpring!![position].expansion
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
    fun setNewList(newList: MutableList<DataSpring>){
        if (newList.isNotEmpty()){
            this.listSpring = newList
            notifyDataSetChanged()
        }
        else{
            this.listSpring = null
            notifyDataSetChanged()
        }
    }



}