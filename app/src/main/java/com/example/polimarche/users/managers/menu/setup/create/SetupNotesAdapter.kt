package com.example.polimarche.users.managers.menu.setup.create

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.polimarche.R

class SetupNotesAdapter(
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /*
    Create the at least one element of the list
     */
    private var setupNoteList: MutableList<String> = mutableListOf("")

    inner class ViewHolderNotes(noteView : View) : RecyclerView.ViewHolder(noteView){
        var note: EditText = noteView.findViewById(R.id.editTextNotes)
        var add: ImageView = noteView.findViewById(R.id.imageViewAddNote)
        var remove: ImageView = noteView.findViewById(R.id.imageViewRemoveNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_managers_setup_note, parent, false)
        return ViewHolderNotes(view)
    }

    override fun getItemCount(): Int {
        return setupNoteList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        when(holder){
            is ViewHolderNotes ->{
                holder.apply {
                    note.setText(setupNoteList[position])
                    add.setImageResource(R.drawable.create_note_setup_icon)
                    remove.setImageResource(R.drawable.remove_general_small_icon)
                    /*
                    Creates a new note after the click on the imageView
                    that is linked to "add" attribute of the viewHolder.
                     */
                    add.setOnClickListener {
                        setupNoteList.add("")
                        notifyItemInserted(setupNoteList.size - 1)
                    }
                    /*
                    Removes the note after the click on the imageView
                    that is linked to "remove" attribute of the viewHolder.
                     */
                    remove.setOnClickListener {
                        if (itemCount != 1) {
                            setupNoteList.removeAt(position)
                            notifyDataSetChanged()
                        }
                    }

                    note.addTextChangedListener(object: TextWatcher{
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {
                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            setupNoteList[position] = s.toString().trim()
                        }

                        override fun afterTextChanged(s: Editable?) {
                        }

                    })
                }
            }
        }
    }

    fun retrieveNoteList(): MutableList<String>{
        return setupNoteList
    }

    fun clearNoteList(){
        setupNoteList = mutableListOf("")
        notifyDataSetChanged()
    }
}