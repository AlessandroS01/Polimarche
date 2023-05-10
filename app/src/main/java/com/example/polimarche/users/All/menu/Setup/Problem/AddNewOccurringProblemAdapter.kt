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
import com.example.mobileprogramming.R
import com.example.polimarche.data_container.setup.DataSetup
import com.example.polimarche.users.all.menu.setup.see.DetailsSetupActivity

/*
Used to display all the setups in which the problem clicked
is not currently occurring.
Used for the recyclerView created after the user clicks on New
Setup Facing The Problem inside ManageProblemsFragment.
 */
class AddNewOccurringProblemAdapter(
    private val listSetups: MutableList<DataSetup>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*
    These 2 attributes are used to pass directly to the OccurringProblemFragment
    the setups that are chosen from the entire list thanks to the isChecked method of
    CheckBoxes.
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
        return listSetups.size
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
                    Changes the values inside the 2 lists created as attributes
                    of the adapter.
                     */
                    description.visibility = View.GONE
                    description.addTextChangedListener {
                        listDescriptionElements[listSetups[position]] = description.text.toString()
                    }
                    setupCode.setOnCheckedChangeListener { _, isChecked ->
                        listCheckedElements[listSetups[position]] = isChecked
                        description.visibility = if (isChecked) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

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