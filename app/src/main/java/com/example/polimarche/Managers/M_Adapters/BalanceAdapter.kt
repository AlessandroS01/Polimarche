package com.example.polimarche.Managers.M_Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataBalance
import com.example.polimarche.Data.DataDampers

class BalanceAdapter(
    private var elementList: MutableList<DataBalance>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderBalance(balanceView : View) : RecyclerView.ViewHolder(balanceView){
        val balanceCode: TextView = balanceView.findViewById(R.id.balanceCode)
        val balanceBrake: TextView = balanceView.findViewById(R.id.brakeBalance)
        val balanceWeight: TextView = balanceView.findViewById(R.id.weightBalance)

        val linearLayout: LinearLayout = balanceView.findViewById(R.id.linearLayoutExpandableBalance)
        val costraintLayout: androidx.constraintlayout.widget.ConstraintLayout = balanceView.findViewById(R.id.costraintLayoutBalance)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_balance, parent, false)
        return ViewHolderBalance(view)
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderBalance ->{
                holder.apply {
                    balanceCode.text = elementList[position].code.toString()
                    balanceBrake.text = elementList[position].brake.toString()
                    balanceWeight.text = elementList[position].weight.toString()

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
    fun setFilteredList(filteredList: MutableList<DataBalance>){
        this.elementList = filteredList
        notifyDataSetChanged()
    }



}