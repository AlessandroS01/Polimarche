package com.example.polimarche.Users.All.Adapters

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
                        showDialog(holder.itemView, position)
                    }
                }
            }
        }
    }

    /*
    Create a dialog box that lets the user add a description
    if the he clicks on reappeared problem
     */
    private fun showDialog(view: View, position: Int) {
        val dialog = Dialog(view.context)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_general_problem_reappeared_or_solved)

        val newDescription = dialog.findViewById(R.id.editTextReappearedProblem) as EditText
        val changeableText = dialog.findViewById(R.id.textViewChangeable) as TextView

        changeableText.text = "If the problem is solved click on confirm otherwise click on cancel.\nDescription value is not compulsory."

        val confirmReappearedProblem = dialog.findViewById(R.id.confirmReappearedProblem) as FrameLayout
        val cancelReappearedProblem = dialog.findViewById(R.id.cancelReappearedProblem) as FrameLayout

        /*
        Confirm that the problem that was solved before reappeared on the same setup.
         */
        confirmReappearedProblem.setOnClickListener {
            listOccurringProblem.removeAt(position)
            notifyDataSetChanged()
            dialog.dismiss()
        }
        cancelReappearedProblem.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}