package ru.bendricks.rpodmp_lab1.db

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import ru.bendricks.rpodmp_lab1.entity.Note

class NoteDBO(val dbHelper: SQLiteOpenHelper) {

    fun addNote(title: String, description: String) {
        val db = dbHelper.writableDatabase

        var contentValues = ContentValues()
        contentValues.put(KEY_TITLE, title)
        contentValues.put(KEY_DESCRIPRION, description)

        db.insert(TABLE_NOTES, null, contentValues)
    }

    fun updateNote(id: Int, title: String, description: String) {
        val db = dbHelper.writableDatabase

        var contentValues = ContentValues()
        contentValues.put(KEY_TITLE, title)
        contentValues.put(KEY_DESCRIPRION, description)

        db.update(TABLE_NOTES, contentValues, KEY_ID.plus("=").plus(id), null)
    }

    fun getNoteById(id: Int): Note {
        val db = dbHelper.readableDatabase
        var note: Note

        var cursor = db.query(TABLE_NOTES, null, KEY_ID.plus("=").plus(id), null, null, null, null)

        val ID_INDEX = cursor.getColumnIndex(KEY_ID)
        val TITLE_INDEX = cursor.getColumnIndex(KEY_TITLE)
        val DESCRIPTION_INDEX = cursor.getColumnIndex(KEY_DESCRIPRION)

        if (cursor.moveToFirst())
            note = Note(
                cursor.getInt(ID_INDEX),
                cursor.getString(TITLE_INDEX),
                cursor.getString(DESCRIPTION_INDEX)
            )
        else
            note = Note(
                0,
                "error",
                "that was not supposed to be here"
            )
        cursor.close()
        return note
    }

    fun getNotes(): List<Note> {
        val db = dbHelper.readableDatabase
        var notesList = ArrayList<Note>()

        var cursor = db.query(TABLE_NOTES, arrayOf(KEY_ID, KEY_TITLE), null, null, null, null, null)

        val ID_INDEX = cursor.getColumnIndex(KEY_ID)
        val TITLE_INDEX = cursor.getColumnIndex(KEY_TITLE)

        while (cursor.moveToNext()) {
            notesList.add(
                Note(
                    cursor.getInt(ID_INDEX),
                    cursor.getString(TITLE_INDEX)
                )
            )
        }
        cursor.close()

        return notesList
    }

    fun deleteNote(id: Int) {
        val db = dbHelper.writableDatabase

        db.delete(TABLE_NOTES, KEY_ID.plus("=").plus(id), null)
    }

}