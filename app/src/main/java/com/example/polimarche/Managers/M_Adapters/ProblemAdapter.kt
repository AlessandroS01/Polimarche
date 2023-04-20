package com.example.polimarche.Managers.M_Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramming.R
import com.example.polimarche.Data.DataDampers
import com.example.polimarche.Data.DataProblem

class ProblemAdapter(
    private var problemList: MutableList<DataProblem>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolderProblem(problemView : View) : RecyclerView.ViewHolder(problemView){
        val problemCode: TextView = problemView.findViewById(R.id.problemCode)
        val problemDescription: TextView = problemView.findViewById(R.id.problemDescription)

        val linearLayout: LinearLayout = problemView.findViewById(R.id.linearLayoutExpandableProblem)
        val costraintLayout: androidx.constraintlayout.widget.ConstraintLayout = problemView.findViewById(R.id.costraintLayoutProblem)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setup_problem, parent, false)
        return ViewHolderProblem(view)
    }

    override fun getItemCount(): Int {
        return problemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderProblem ->{
                holder.apply {
                    problemCode.text = problemList[position].code.toString()
                    problemDescription.text = problemList[position].description

                    val expansion = problemList[position].expansion


                    linearLayout.visibility = if (expansion) View.VISIBLE else View.GONE

                    costraintLayout.setOnClickListener {
                        problemList[position].expansion = !problemList[position].expansion
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<DataProblem>){
        this.problemList = filteredList
        notifyDataSetChanged()
    }



}