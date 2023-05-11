package com.example.polimarche.users.managers.menu.setup.create.choosing_balance

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.data_container.balance.BalanceViewModel
import com.example.polimarche.data_container.balance.DataBalance

class BalanceAdapter(
    private val position: String,
    private val balanceViewModel: BalanceViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var balanceParametersList = if(
        position == "Front"
    ) balanceViewModel.getFrontBalanceList()
    else balanceViewModel.getBackBalanceList()

    inner class ViewHolderBalance(balanceView : View) : RecyclerView.ViewHolder(balanceView){
        val balanceCode: TextView = balanceView.findViewById(R.id.balanceCode)
        val balanceBrake: TextView = balanceView.findViewById(R.id.brakeBalance)
        val balanceWeight: TextView = balanceView.findViewById(R.id.weightBalance)

        val linearLayout: LinearLayout = balanceView.findViewById(R.id.linearLayoutExpandableBalance)
        val constraintLayout: androidx.constraintlayout.widget.ConstraintLayout = balanceView.findViewById(R.id.costraintLayoutBalance)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_balance, parent, false)
        return ViewHolderBalance(view)
    }

    override fun getItemCount(): Int {
        return balanceParametersList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderBalance ->{
                holder.apply {
                    balanceCode.text = balanceParametersList[position].code.toString()
                    balanceBrake.text = balanceParametersList[position].brake.toString()
                    balanceWeight.text = balanceParametersList[position].weight.toString()

                    val expansion = balanceParametersList[position].expansion


                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    constraintLayout.setOnClickListener {
                        balanceParametersList[position].expansion = !balanceParametersList[position].expansion
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
    fun setNewList(newList: MutableList<DataBalance>){
        this.balanceParametersList = newList
        notifyDataSetChanged()
    }



}