package com.example.polimarche.users.managers.menu.setup.create.choosing_wheels

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.wheel.DataWheel
import com.example.polimarche.data_container.wheel.WheelViewModel

class WheelsAdapter(
    private val position: String,
    private val wheelViewModel: WheelViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var wheelList =
        when(position){
            "Front right"-> wheelViewModel.getFrontRightWheels()
            "Front left" -> wheelViewModel.getFrontLeftWheels()
            "Rear left" -> wheelViewModel.getRearLeftWheels()
            "Rear right" -> wheelViewModel.getRearRightWheels()
            else ->{
                if (
                    wheelViewModel.getFrontRightParametersStocked() != null
                    &&
                    wheelViewModel.getFrontLeftParametersStocked() != null
                    &&
                    wheelViewModel.getRearRightParametersStocked() != null
                    &&
                    wheelViewModel.getRearLeftParametersStocked() != null
                ){
                     wheelViewModel.getStockedParameters()
                }
                else
                    null
            }
        }

    inner class ViewHolderWheel(wheelView : View) : RecyclerView.ViewHolder(wheelView){
        val wheelCode: TextView = wheelView.findViewById(R.id.wheelCode)
        val wheelCodification: TextView = wheelView.findViewById(R.id.wheelCodificationItem)
        val wheelPressure: TextView = wheelView.findViewById(R.id.wheelPressure)
        val wheelCamber: TextView = wheelView.findViewById(R.id.wheelCamber)
        val wheelToe: TextView = wheelView.findViewById(R.id.wheelToe)
        val wheelPosition: TextView = wheelView.findViewById(R.id.wheelPosition)

        val linearLayout = wheelView.findViewById<LinearLayout>(R.id.linearLayoutExpandable)
        val constraintLayout: androidx.constraintlayout.widget.ConstraintLayout = wheelView.findViewById(R.id.costraintLayout)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_wheels, parent, false)
        return ViewHolderWheel(view)
    }

    override fun getItemCount(): Int {
        return if (wheelList != null)
            wheelList?.size!!
        else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if( wheelList != null) {
            when (holder) {
                is ViewHolderWheel -> {
                    holder.apply {
                        wheelCode.text = wheelList!![position].code.toString()
                        wheelCodification.text = wheelList!![position].codification
                        wheelPressure.text = "${wheelList!![position].pressure} bar"
                        wheelCamber.text = wheelList!![position].camber
                        wheelToe.text = wheelList!![position].toe
                        wheelPosition.text = wheelList!![position].position

                        val expansion = wheelList!![position].expansion


                        linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                        constraintLayout.setOnClickListener {
                            wheelList!![position].expansion = !wheelList!![position].expansion
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
    fun setNewList(newList: MutableList<DataWheel>){
        if (newList.isNotEmpty()){
            this.wheelList = newList
            notifyDataSetChanged()
        }
        else{
            this.wheelList = null
            notifyDataSetChanged()
        }
    }



}