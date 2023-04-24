package com.example.polimarche.Users.Managers.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R

class BalanceCodeAdapter(
    private var balanceCodeList: MutableList<Int>,
    private val listener: OnBalanceCodeClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnBalanceCodeClickListener{
        fun onCodificationClick(codification: Int)
    }

    inner class ViewHolderBalanceCode(balanceCodeView : View) : RecyclerView.ViewHolder(balanceCodeView), View.OnClickListener {
        val code: TextView = balanceCodeView.findViewById(R.id.balanceCodification)
        init {
            balanceCodeView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onCodificationClick(balanceCodeList[position])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_balance_code, parent, false)
        return ViewHolderBalanceCode(view)
    }

    override fun getItemCount(): Int {
        return balanceCodeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderBalanceCode ->{
                holder.apply {
                    code.text = "Cod: ${balanceCodeList[position]}"
                }
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<Int>){
        this.balanceCodeList = filteredList
        notifyDataSetChanged()
    }
}