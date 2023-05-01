package com.example.polimarche.Users.All.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataOccurringProblem

class OccurringProblemAdapter(
    private val listOccurringProblem: MutableList<DataOccurringProblem>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderOccurringProblem(occurringProblemView : View) : RecyclerView.ViewHolder(occurringProblemView){
        val setupCode: TextView = occurringProblemView.findViewById(R.id.setupCodeOccurringProblem)
        val description: EditText = occurringProblemView.findViewById(R.id.descriptionOccurringProblem)
        val removeProblem: ImageView = occurringProblemView.findViewById(R.id.imageViewRemoveOccurringProblem)
        
        val linearLayoutTouchable: LinearLayout = occurringProblemView.findViewById(R.id.linearLayoutTouchableOccurringProblem)
        val linearLayout: LinearLayout = occurringProblemView.findViewById(R.id.linearLayoutExpandableOccurringProblem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_general_occurring_problem, parent, false
        )
        return ViewHolderOccurringProblem(view)
    }

    override fun getItemCount(): Int {
        return listOccurringProblem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderOccurringProblem ->{
                holder.apply {
                    setupCode.text = listOccurringProblem[position].setupCode.toString()
                    description.setText(listOccurringProblem[position].description)
                    removeProblem.setImageResource(R.drawable.remove_setup_note_icon)

                    val expansion = listOccurringProblem[position].expansion
                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    linearLayoutTouchable.setOnClickListener{
                        listOccurringProblem[position].expansion = !listOccurringProblem[position].expansion
                        notifyItemChanged(position)
                    }

                    removeProblem.setOnClickListener {
                        listOccurringProblem.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }


}