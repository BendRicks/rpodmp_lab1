package ru.bendricks.rpodmp_lab1.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.bendricks.rpodmp_lab1.R
import ru.bendricks.rpodmp_lab1.activity.MainActivity
import ru.bendricks.rpodmp_lab1.activity.SecondActivity
import ru.bendricks.rpodmp_lab1.entity.Note
import java.util.*
import kotlin.streams.toList


class NoteAdapter(private var notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteHolder>(), Filterable {

    private var filteredNotes: List<Note>

    init {
        filteredNotes = notes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(filteredNotes[position])
    }

    override fun getItemCount() = filteredNotes.size

    @SuppressLint("NotifyDataSetChanged")
    fun refreshNotes(notes: List<Note>) {
        this.notes = notes
        this.filteredNotes = ArrayList(notes)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if (charSearch.isEmpty()){
                    filteredNotes = notes
                } else {
                    filteredNotes = notes.stream().filter({it.title.lowercase().contains(charSearch.lowercase())}).toList()
                }
                val filterResults = FilterResults()
                filterResults.values = filteredNotes
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredNotes = p1?.values as ArrayList<Note>
                println(filteredNotes)
                notifyDataSetChanged()
            }
        }
    }

    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val titleTextView: TextView

        init {
            itemView.setOnClickListener(this)
            titleTextView = itemView.findViewById(R.id.note_name)
        }

        fun bind(note : Note)  {
            titleTextView?.text = note.title
            titleTextView?.id = note._id
        }

        override fun onClick(p0: View?)  {
            val mainActivity = MainActivity._mainActivity
            val intent = Intent(mainActivity, SecondActivity::class.java)
            intent.putExtra(SecondActivity.IS_UPDATE, true)
            intent.putExtra(SecondActivity.ID, titleTextView.id)
            mainActivity.startActivity(intent)
        }
    }

}