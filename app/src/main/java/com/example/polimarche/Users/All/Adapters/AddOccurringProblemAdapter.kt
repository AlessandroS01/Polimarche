package com.example.polimarche.Users.All.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataSetup

class AddOccurringProblemAdapter(
    private val listSetups: MutableList<DataSetup>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*
    These 2 attributes are used to pass directly to the OccurringProblemFragment
    the setups that are chosen from the entire list thanks to the isChecked method of
    CheckBoxes.
     */
    private val listCheckedElements: MutableList<Boolean> = MutableList(listSetups.size){false}
    private val listDescriptionElements: MutableList<String> = MutableList(listSetups.size){""}

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
                    setupCode.text = "Setup code: ${listSetups[position].code.toString()}"
                    visualizeSetup.setImageResource(R.drawable.visibility_icon)

                    /*
                    Changes the values inside the 2 lists created as attributes
                    of the adapter.
                     */
                    description.visibility = View.GONE
                    description.addTextChangedListener {
                        listDescriptionElements[position] = description.text.toString()
                    }
                    setupCode.setOnCheckedChangeListener { _, isChecked ->
                        listCheckedElements[position] = isChecked
                        description.visibility = if (isChecked) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

    fun getListCheckedElements(): MutableList<Boolean>{
        return listCheckedElements
    }
    fun getListDescriptionElements(): MutableList<String>{
        return listDescriptionElements
    }

}