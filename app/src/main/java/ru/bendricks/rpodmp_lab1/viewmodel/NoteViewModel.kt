package ru.bendricks.rpodmp_lab1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.bendricks.rpodmp_lab1.db.NoteDBO
import ru.bendricks.rpodmp_lab1.entity.Note

class NoteViewModel : ViewModel() {

    var notesList : MutableLiveData<List<Note>> = MutableLiveData();
    lateinit var dbo: NoteDBO

    fun getListNotes() = notesList

    fun updateListNotes() {
            notesList.value = dbo.getNotes()
    }


}