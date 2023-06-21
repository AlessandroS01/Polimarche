package com.example.polimarche.users.managers.menu.setup.create.choosing_balance

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.balance.BalanceViewModel

class BalanceCodeAdapter(
    private val balanceViewModel: BalanceViewModel,
    private val listener: OnBalanceCodeClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var balanceListCodes = balanceViewModel.getBalanceCodes()

    interface OnBalanceCodeClickListener{
        fun onCodeClick(code: Int)
    }

    inner class ViewHolderBalanceCode(balanceCodeView : View) : RecyclerView.ViewHolder(balanceCodeView), View.OnClickListener {
        val code: TextView = balanceCodeView.findViewById(R.id.balanceCodification)
        init {
            balanceCodeView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onCodeClick(balanceListCodes[position])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_balance_code, parent, false)
        return ViewHolderBalanceCode(view)
    }

    override fun getItemCount(): Int {
        return balanceListCodes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderBalanceCode ->{
                holder.apply {
                    code.text = "Code: ${balanceListCodes[position]}"
                }
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setNewList(newList: MutableList<Int>){
        this.balanceListCodes = newList
        notifyDataSetChanged()
    }
}