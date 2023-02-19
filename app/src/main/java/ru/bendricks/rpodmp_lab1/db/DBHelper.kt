package ru.bendricks.rpodmp_lab1.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

val DATABASE_NAME = "ru.bendricks.notes_app_db"
val DATABASE_VERSION = 1
val TABLE_NOTES = "notes"

val KEY_ID = "_id"
val KEY_TITLE = "title"
val KEY_DESCRIPRION = "description"

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table ".plus(TABLE_NOTES).plus(" (")
            .plus(KEY_ID).plus(" integer primary key,")
            .plus(KEY_TITLE).plus(" text,")
            .plus(KEY_DESCRIPRION).plus(" text)"))
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists ".plus(TABLE_NOTES))
        onCreate(p0)
    }
}