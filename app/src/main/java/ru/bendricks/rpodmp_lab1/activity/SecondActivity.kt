package ru.bendricks.rpodmp_lab1.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.bendricks.rpodmp_lab1.R
import ru.bendricks.rpodmp_lab1.databinding.ActivitySecondBinding
import ru.bendricks.rpodmp_lab1.db.DBHelper
import ru.bendricks.rpodmp_lab1.db.NoteDBO

class SecondActivity : AppCompatActivity() {

    private lateinit var deleteButton: Button
    private lateinit var saveUpdateButton: Button
    private lateinit var titleTextEdit: TextView
    private lateinit var descriptionTextEdit: TextView

    private lateinit var binding: ActivitySecondBinding
    private lateinit var dbo: NoteDBO

    companion object {
        const val IS_UPDATE = "is_update"
        const val ID = "id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)

        deleteButton = findViewById(R.id.delete_button)
        saveUpdateButton = findViewById(R.id.save_update_button)
        titleTextEdit = findViewById(R.id.edit_title)
        descriptionTextEdit = findViewById(R.id.edit_description)

        dbo = NoteDBO(DBHelper(this, null))
        val isUpd = intent.getBooleanExtra(IS_UPDATE, false)
        binding.isUpdate = isUpd
        if (isUpd) {
            saveUpdateButton.setOnClickListener(this::updateButtonPressed)
            deleteButton.setOnClickListener(this::deleteButtonPressed)
            val id = intent.getIntExtra(ID, 0)
            binding.id = id
            val note = dbo.getNoteById(id)
            binding.title = note.title
            binding.description = note.description
        } else {
            saveUpdateButton.setOnClickListener(this::saveButtonPressed)
            deleteButton.visibility = View.GONE
        }
    }

    fun saveButtonPressed(view: View){
        val title = findViewById<EditText>(R.id.edit_title).text.toString().trim() ?: "error"
        val description = findViewById<EditText>(R.id.edit_description).text.toString().trim() ?: "error"

        if (title.isNotEmpty() && description.isNotEmpty()) {
            dbo.addNote(title, description)
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT)
                .show()
            finish()
        } else {
            Toast.makeText(this, "Title and description must not be empty", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateButtonPressed(view: View){
        val id = binding.id ?: 0
        if (id != 0) {
            val title = findViewById<EditText>(R.id.edit_title).text.toString().trim() ?: "error"
            val description =
                findViewById<EditText>(R.id.edit_description).text.toString().trim() ?: "error"
            if (title.isNotEmpty() && description.isNotEmpty()) {
                dbo.updateNote(id, title, description)
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "Title and description must not be empty", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            Toast.makeText(this, "Error updating note", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun deleteButtonPressed(view: View){
        val id = binding.id ?: 0
        if (id != 0){
            dbo.deleteNote(id)
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT)
                .show()
            finish()
        } else {
            Toast.makeText(this, "Error deleting note", Toast.LENGTH_SHORT)
                .show()
        }
    }

}