package com.example.polimarche.users.managers.menu.setup.create.choosing_dampers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.data_container.damper.DamperViewModel

class DampersCodeAdapter(
    private val damperViewModel: DamperViewModel,
    private val listener: OnDampersCodeClickListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listDamperCode = damperViewModel.getDampersCode()
    
    interface OnDampersCodeClickListener{
        fun onCodeClick(code: Int)
    }

    inner class ViewHolderDamperCodification(damperCodificationView : View) : RecyclerView.ViewHolder(damperCodificationView), View.OnClickListener {
        val codification: TextView = damperCodificationView.findViewById(R.id.dampersCodification)
        init {
            damperCodificationView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onCodeClick(listDamperCode[position])
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_managers_dampers_codification,
            parent,
            false
        )
        return ViewHolderDamperCodification(view)
    }

    override fun getItemCount(): Int {
        return listDamperCode.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderDamperCodification ->{
                holder.apply {
                    codification.text = "Code: ${listDamperCode[position]}"
                }
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setNewList(newCodeList: MutableList<Int>){
        this.listDamperCode = newCodeList
        notifyDataSetChanged()
    }
}