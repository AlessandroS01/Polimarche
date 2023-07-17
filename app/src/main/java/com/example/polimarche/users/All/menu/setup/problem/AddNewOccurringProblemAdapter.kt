package com.example.polimarche.users.all.menu.setup.problem

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.users.all.menu.setup.see.DetailsSetupActivity

/*
Utilizzato per visualizzare tutte i setup in cui si è verificato il problema
non attualmente in corso.
Utilizzato per recyclerView creato dopo che l'utente fa clic su New
Setup Facing The Problem all'interno di ManageProblemsFragment.
 */
class AddNewOccurringProblemAdapter(
    private val listSetups: MutableList<DataSetup>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*
    Questi 2 attributi vengono utilizzati per passare direttamente a OccurringProblemFragment
    i setup che vengono scelti dall'intero elenco grazie al metodo isChecked della Checkbox.
     */
    private val listCheckedElements = initializeMappingCheckedElements()
    private val listDescriptionElements = initializeMappingDescriptionElements()

    inner class ViewHolderAddOccurringProblem(addOccurringProblemView : View) : RecyclerView.ViewHolder(addOccurringProblemView){
        val setupCode: CheckBox = addOccurringProblemView.findViewById(R.id.checkBoxAddOccurringProblem)
        val visualizeSetup: ImageView = addOccurringProblemView.findViewById(R.id.imageViewVisualizeSetupOccurringProblem)
        val description: EditText = addOccurringProblemView.findViewById(R.id.editTextAddDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_general_add_occurring_problem, parent, false
        )
        return ViewHolderAddOccurringProblem(view)
    }

    override fun getItemCount(): Int {
        return listSetups.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderAddOccurringProblem ->{
                holder.apply {
                    setupCode.text = "Setup code: ${listSetups[position].code}"
                    visualizeSetup.setImageResource(R.drawable.visibility_icon)

                    visualizeSetup.setOnClickListener {
                        Intent(holder.itemView.context, DetailsSetupActivity::class.java).apply {
                            this.putExtra("SETUP_CODE", listSetups[position].code)
                            it.context.startActivity(this)
                        }
                    }

                    /*
                    Modifica i valori all'interno delle 2 liste create come attributi
                    dell'adapter.
                     */
                    description.visibility = View.GONE
                    // Quando il testo della descrizione cambia il valore viene memorizzato in listDescriptionElements
                    description.addTextChangedListener {
                        listDescriptionElements[listSetups[position]] = description.text.toString()
                    }

                    // Quando lo stato di selezione di setupCode cambia, la visibilità di description viene regolata
                    // e il valore di selezione viene memorizzato in listCheckedElements
                    setupCode.setOnCheckedChangeListener { _, isChecked ->
                        listCheckedElements[listSetups[position]] = isChecked
                        description.visibility = if (isChecked) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

    // Questa funzioni inizializzano le mappe listCheckedElements e listDescriptionElements
    // associando ciascun elemento DataSetup a un valore di default (false per listCheckedElements e
    // "" per listDescriptionElements)
    private fun initializeMappingCheckedElements(): MutableMap<DataSetup, Boolean> {
        val map = mutableMapOf<DataSetup, Boolean>()
        listSetups.forEach {
            map[it] = false
        }
        return map
    }

    private fun initializeMappingDescriptionElements(): MutableMap<DataSetup, String>{
        val map = mutableMapOf<DataSetup, String>()
        listSetups.forEach {
            map[it] = ""
        }
        return map
    }

    fun getListCheckedElements(): MutableMap<DataSetup, Boolean>{
        return listCheckedElements
    }
    fun getListDescriptionElements(): MutableMap<DataSetup, String>{
        return listDescriptionElements
    }

}