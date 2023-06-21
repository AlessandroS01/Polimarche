package com.example.polimarche.users.managers.menu.setup.create.choosing_balance

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.balance.BalanceViewModel
import com.example.polimarche.data_container.balance.DataBalance

class BalanceAdapter(
    private val position: String,
    private val balanceViewModel: BalanceViewModel
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var balanceParametersList = when(position){
        "Front" -> {
            balanceViewModel.getFrontBalanceList()
        }
        "Back" -> {
            balanceViewModel.getBackBalanceList()
        }
        else -> {
            if (
                balanceViewModel.getFrontBalanceParametersStocked() != null
                &&
                balanceViewModel.getBackBalanceParametersStocked() != null
            ){
                 balanceViewModel.getStockedParameters()
            }
            else
                null
        }
    }


    inner class ViewHolderBalance(balanceView : View) : RecyclerView.ViewHolder(balanceView){
        val balanceCode: TextView = balanceView.findViewById(R.id.balanceCode)
        val balanceBrake: TextView = balanceView.findViewById(R.id.brakeBalance)
        val balanceWeight: TextView = balanceView.findViewById(R.id.weightBalance)
        val balanceEnd: TextView = balanceView.findViewById(R.id.balanceEnd)

        val linearLayout: LinearLayout = balanceView.findViewById(R.id.linearLayoutExpandableBalance)
        val constraintLayout: androidx.constraintlayout.widget.ConstraintLayout = balanceView.findViewById(R.id.costraintLayoutBalance)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_balance, parent, false)
        return ViewHolderBalance(view)
    }

    override fun getItemCount(): Int {
        return if (balanceParametersList != null)
            balanceParametersList?.size!!
        else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if( balanceParametersList != null){
            when(holder){
                is ViewHolderBalance ->{
                    holder.apply {
                        balanceCode.text = balanceParametersList!![position].code.toString()
                        balanceBrake.text = balanceParametersList!![position].brake.toString()
                        balanceWeight.text = balanceParametersList!![position].weight.toString()
                        balanceEnd.text = balanceParametersList!![position].end

                        val expansion = balanceParametersList!![position].expansion


                        linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                        constraintLayout.setOnClickListener {
                            balanceParametersList!![position].expansion = !balanceParametersList!![position].expansion
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
    fun setNewList(newList: MutableList<DataBalance>){
        if (newList.isNotEmpty()){
            this.balanceParametersList = newList
            notifyDataSetChanged()
        }
        else{
            this.balanceParametersList = null
            notifyDataSetChanged()
        }
    }



}