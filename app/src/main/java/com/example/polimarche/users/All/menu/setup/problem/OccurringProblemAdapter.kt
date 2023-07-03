package com.example.polimarche.users.all.menu.setup.problem

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R
import com.example.polimarche.data_container.problem.occurring_problem.DataOccurringProblem
import com.example.polimarche.data_container.problem.DataProblem
import com.example.polimarche.data_container.problem.occurring_problem.OccurringProblemViewModel
import com.example.polimarche.data_container.problem.solved_problem.DataSolvedProblem
import com.example.polimarche.users.all.menu.setup.see.DetailsSetupActivity
/*
Passes directly the list of problems that matches with the problem clicked
on ProblemsSetupFragment and then the viewModel to
 */
class OccurringProblemAdapter(
    private val problemClicked: DataProblem,
    private val occurringProblemViewModel: OccurringProblemViewModel,
    private val listener: OnProblemSolvedClick
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var listOccurringProblem: MutableList<DataOccurringProblem> =
        occurringProblemViewModel.filterListByProblemCode(problemClicked.code).value?.toMutableList()!!


    interface OnProblemSolvedClick{
        fun onProblemSolvedClick(element: DataOccurringProblem, itemView: View)
    }

    inner class ViewHolderOccurringProblem(
        occurringProblemView : View
    ) : RecyclerView.ViewHolder(occurringProblemView), View.OnClickListener{
        val setupCode: TextView = occurringProblemView.findViewById(R.id.setupCodeOccurringProblem)
        val description: EditText = occurringProblemView.findViewById(R.id.descriptionOccurringProblem)
        val removeProblem: ImageView = occurringProblemView.findViewById(R.id.imageViewRemoveOccurringProblem)
        val visualizeProblem: ImageView = occurringProblemView.findViewById(R.id.imageViewVisualizeSetupOccurringProblem)
        
        val linearLayoutTouchable: LinearLayout = occurringProblemView.findViewById(R.id.linearLayoutTouchableOccurringProblem)
        val linearLayout: LinearLayout = occurringProblemView.findViewById(R.id.linearLayoutExpandableOccurringProblem)

        init {
            removeProblem.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val id = v?.id
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                if (id == R.id.imageViewRemoveOccurringProblem) {
                    listener.onProblemSolvedClick(
                        /*
                        Find the instance of DataProblem that has the same problem code
                        as the one clicked at the position given by the adapter
                         */
                        occurringProblemViewModel.filterListByProblemCode(
                            problemClicked.code
                        ).value?.get(position)!!,
                        v
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_general_occurring_problem, parent, false
        )
        return ViewHolderOccurringProblem(view)
    }

    override fun getItemCount(): Int {
        return listOccurringProblem.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolderOccurringProblem ->{
                holder.apply {
                    setupCode.text = "Setup ${ listOccurringProblem?.get(position)?.setupCode.toString() }"
                    description.setText(listOccurringProblem?.get(position)?.description)
                    removeProblem.setImageResource(R.drawable.remove_general_small_icon)
                    visualizeProblem.setImageResource(R.drawable.visibility_icon)

                    visualizeProblem.setOnClickListener {
                        Intent(it.context, DetailsSetupActivity::class.java).apply {
                            this.putExtra("SETUP_CODE", listOccurringProblem?.get(position)?.setupCode)
                            itemView.context.startActivity(this)
                        }
                    }

                    val expansion = listOccurringProblem?.get(position)?.expansion
                    linearLayout.visibility = if (expansion!!) View.VISIBLE else View.GONE

                    linearLayoutTouchable.setOnClickListener{
                        listOccurringProblem[position].expansion =
                            !listOccurringProblem[position].expansion
                        notifyItemChanged(position)
                    }
                }
            }
        }
    }

    /*
    Adds a new item to the list inside the ViewModel.
    Then it calls directly a method of the adapter
    that replaces the list of the adapter.
     */
    fun addItemToItemView(item: DataOccurringProblem){
        occurringProblemViewModel.addNewOccurringProblem(item)
        setNewList(
            occurringProblemViewModel.filterListByProblemCode(problemClicked.code).value?.toMutableList()!!
        )
    }

    /*
    Firstly it adds to the list inside solvedProblemViewModel a new SolvedProblem
    in which the problem code is the one of the problem clicked, the
    setup code is the one of the item removed and the description is the one passed
    as a parameter from OccurringProblemFragment.
    Then it calls directly a method of the adapter
    that replaces the list of the adapter.
     */
    suspend fun removeItemFromList(item: DataOccurringProblem, descriptionSolvedProblem: String){
        val newList = occurringProblemViewModel.filterListByProblemCode(problemClicked.code).value?.toMutableList()!!

        newList.removeIf { it.setupCode == item.setupCode }

        setNewList(
            newList
        )

        occurringProblemViewModel.removeItemFromList(item, descriptionSolvedProblem)
    }


     fun setNewList(newList: MutableList<DataOccurringProblem>){
        listOccurringProblem.clear()
        listOccurringProblem.addAll(newList)
        notifyDataSetChanged()
    }
}