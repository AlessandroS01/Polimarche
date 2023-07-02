package com.example.polimarche.users.managers.menu.setup.delete

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel

class DeleteSetupAdapter (
    private val setupViewModel: SetupViewModel,
    private val listener: OnSetupCodeClickListener
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var setupList: MutableList<DataSetup> = mutableListOf()

    init {
        setupViewModel.setupList.observeForever { setup ->
            setupList.clear()
            setupList.addAll(setup)
            notifyDataSetChanged()
        }
    }

    interface OnSetupCodeClickListener{
        fun onSetupCodeClickListener(setupClicked: DataSetup)
    }

    inner class ViewHolderDeleteSetup(setupView : View) : RecyclerView.ViewHolder(setupView), View.OnClickListener{
        val setupCode: TextView = setupView.findViewById(R.id.itemDeleteSetupCode)
        init{
            setupView.setOnClickListener(this)
        }
        
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onSetupCodeClickListener(setupList[position])
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setNewList(filteredList: MutableList<DataSetup>){
        setupList.clear()
        setupList.addAll(filteredList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_delete_setup, parent, false)
        return ViewHolderDeleteSetup(view)
    }

    override fun getItemCount(): Int {
        return setupList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolderDeleteSetup -> {
                holder.apply {
                    setupCode.text = "Setup " + setupList[position].code.toString()
                }
            }
        }
    }


}