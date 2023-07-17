package com.example.polimarche.users.all.menu.setup.see

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.data_container.setup.SetupViewModel


class SeeSetupAdapter (
    private val setupViewModel : SetupViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listSetupCodes: MutableList<Int> = mutableListOf()

    init {
        setupViewModel.setupList.observeForever { setup ->
            listSetupCodes.clear()
            setupViewModel.setupList.value?.forEach{
                listSetupCodes.add(it.code)
            }
            notifyDataSetChanged()
        }
    }
    inner class ViewHolderSetup(setupView : View) : RecyclerView.ViewHolder(setupView) {
        val setupCode: TextView = setupView.findViewById(R.id.seeSetupSetupCode)
        val detailSetup: ImageView = setupView.findViewById(R.id.seeSetupSetupDetails)
    }

    /*
    Questo metodo consente di modificare l'elemento su recyclerView
    al cambio del testo inserito all'interno della SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<Int>){
        listSetupCodes.clear()
        listSetupCodes.addAll(filteredList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_general_see_setup,
            parent,
            false
        )
        return ViewHolderSetup(view)
    }

    override fun getItemCount(): Int {
        return listSetupCodes.size!!
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolderSetup -> {
                holder.apply {
                    setupCode.text = "Setup " + listSetupCodes.get(position)?.toString()
                    detailSetup.setImageResource(R.drawable.visibility_icon)
                    detailSetup.setOnClickListener {
                        Intent(holder.itemView.context, DetailsSetupActivity::class.java).apply {
                            /*
                            Alla nuova attività passa direttamente il valore
                            del codice di configurazione cliccato.
                            In questo modo possiamo trovare i parametri dell'intero
                            setup all'interno dell'activity.
                             */
                            this.putExtra("SETUP_CODE", listSetupCodes[position])
                            it.context.startActivity(this)
                        }
                    }
                }
            }
        }
    }


}