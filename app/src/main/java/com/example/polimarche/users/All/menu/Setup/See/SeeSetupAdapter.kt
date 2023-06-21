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
import com.example.polimarche.data_container.setup.SetupViewModel

class SeeSetupAdapter (
    private var setupViewModel : SetupViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listSetupCodes = setupViewModel.getSetupCodes()

    inner class ViewHolderSetup(setupView : View) : RecyclerView.ViewHolder(setupView) {
        val setupCode: TextView = setupView.findViewById(R.id.seeSetupSetupCode)
        val detailSetup: ImageView = setupView.findViewById(R.id.seeSetupSetupDetails)
    }

    /*
    This method lets change the item on the recyclerView
    at the change of the text inserted inside the SearchView
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(filteredList: MutableList<Int>){
        listSetupCodes = filteredList
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
        return listSetupCodes.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is ViewHolderSetup -> {
                holder.apply {
                    setupCode.text = "Setup " + listSetupCodes[position].toString()
                    detailSetup.setImageResource(R.drawable.visibility_icon)
                    detailSetup.setOnClickListener {
                        Intent(holder.itemView.context, DetailsSetupActivity::class.java).apply {
                            /*
                            To the new activity it passes directly the value
                            of the setup code clicked.
                            Doing this we can find the parameters of the entire
                            setup inside the activity.
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