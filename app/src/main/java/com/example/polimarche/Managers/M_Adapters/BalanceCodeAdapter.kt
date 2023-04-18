package com.example.polimarche.Managers.M_Adapters

import android.annotation.SuppressLint
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataBalance
import com.example.polimarche.Data.DataBalanceCode
import com.example.polimarche.Data.DataDampersCodification

class BalanceCodeAdapter(
    private var elementList: MutableList<DataBalanceCode>,
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
                listener.onCodificationClick(elementList[position].code)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_balance_code, parent, false)
        return ViewHolderBalanceCode(view)
    }

    override fun getItemCount(): Int {
        return elementList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderBalanceCode ->{
                holder.apply {
                    code.text = "Cod: ${elementList[position].code}"
                }
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<DataBalanceCode>){
        this.elementList = filteredList
        notifyDataSetChanged()
    }
}