package com.example.polimarche.data_container.team_members_workshop.setup.problem

import android.content.Intent
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
import com.example.polimarche.data_container.team_members_workshop.setup.see.DetailsSetupActivity
/*
Passes directly the list of problems that matches with the problem clicked
on ProblemsSetupFragment and then the viewModel to
 */
class OccurringProblemAdapter(
    private val problemClicked: DataProblem,
    private val occurringProblemViewModel: OccurringProblemViewModel,
    private val listener: OnProblemSolvedClick
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var listOccurringProblem = occurringProblemViewModel.filterListByProblemCode(
                                            problemClicked.code
                                        )

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
        return listOccurringProblem.value?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listOfItems = listOccurringProblem.value?.toMutableList()

        when(holder){
            is ViewHolderOccurringProblem ->{
                holder.apply {
                    setupCode.text = "Setup ${ listOfItems?.get(position)?.setupCode.toString() }"
                    description.setText(listOfItems?.get(position)?.description)
                    removeProblem.setImageResource(R.drawable.remove_general_small_icon)
                    visualizeProblem.setImageResource(R.drawable.visibility_icon)

                    visualizeProblem.setOnClickListener {
                        Intent(it.context, DetailsSetupActivity::class.java).apply {
                            this.putExtra("SETUP_CODE", listOfItems?.get(position)?.setupCode)
                            itemView.context.startActivity(this)
                        }
                    }

                    val expansion = listOfItems?.get(position)?.expansion
                    linearLayout.visibility = if (expansion!!) View.VISIBLE else View.GONE

                    linearLayoutTouchable.setOnClickListener{
                        listOfItems[position].expansion =
                            !listOfItems[position].expansion
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
            occurringProblemViewModel.filterListByProblemCode(problemClicked.code)
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
    fun removeItemFromList(item: DataOccurringProblem, descriptionSolvedProblem: String){
        occurringProblemViewModel.removeItemFromList(item, descriptionSolvedProblem)
        setNewList(
            occurringProblemViewModel.filterListByProblemCode(problemClicked.code)
        )
    }


    private fun setNewList(newList: MutableLiveData<MutableList<DataOccurringProblem>>){
        listOccurringProblem = newList
        notifyDataSetChanged()
    }
}