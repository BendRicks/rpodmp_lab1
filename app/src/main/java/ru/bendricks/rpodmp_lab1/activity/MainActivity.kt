package ru.bendricks.rpodmp_lab1.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.bendricks.rpodmp_lab1.R
import ru.bendricks.rpodmp_lab1.databinding.ActivityMainBinding
import ru.bendricks.rpodmp_lab1.db.DBHelper
import ru.bendricks.rpodmp_lab1.db.NoteDBO
import ru.bendricks.rpodmp_lab1.viewmodel.NoteAdapter
import ru.bendricks.rpodmp_lab1.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    private val noteViewModel by lazy { ViewModelProviders.of(this)[NoteViewModel::class.java] }

    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchEdit: SearchView

    companion object {
        lateinit var _mainActivity: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val adapter = NoteAdapter(ArrayList())

        addButton = findViewById(R.id.add_button)
        recyclerView = findViewById(R.id.notes_list)
        searchEdit = findViewById(R.id.search_query)
        addButton.setOnClickListener(this::createNote)

        searchEdit.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })

        val notesList = findViewById<RecyclerView>(R.id.notes_list)
        notesList.layoutManager = LinearLayoutManager(this)
        notesList.adapter = adapter

        noteViewModel.dbo = NoteDBO(DBHelper(this, null))
        noteViewModel.getListNotes().observe(this) {
            it?.let {
                adapter.refreshNotes(it)
            }
        }
        _mainActivity = this
    }

    override fun onPostResume() {
        noteViewModel.updateListNotes()
        searchEdit.setQuery("", false)
        searchEdit.clearFocus()
        super.onPostResume()
    }

    private fun createNote(view: View){
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(SecondActivity.IS_UPDATE, false)
        startActivity(intent)
    }

}