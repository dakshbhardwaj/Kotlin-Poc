package com.example.findmyage

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class AddNote: AppCompatActivity() {
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_note)
        try {
            val bundle: Bundle = intent.extras!!
            id = bundle.getInt("Id")
            if (id != 0) {
                findViewById<EditText>(R.id.etTitle).setText(bundle.getString("Title").toString())
                findViewById<EditText>(R.id.etDescription).setText(
                    bundle.getString("Des").toString()
                )
            }
        }catch (ex : Exception){}
    }

    fun onAddButtonPress(view: View){
        var databaseManager = DatabaseManager(this)
        var values = ContentValues()
        values.put("Title", findViewById<EditText>(R.id.etTitle).text.toString())
        values.put("Description", findViewById<EditText>(R.id.etDescription).text.toString())
        if(id == 0) {
            val Id = databaseManager.insertValues(values)
            if (Id > 0) {
                Toast.makeText(this, "Note Added", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Note not Added", Toast.LENGTH_LONG).show()
            }
        }else {
            val selectionArgs = arrayOf(id.toString())
            val a:Boolean = databaseManager.updateNote(values, "ID?=",selectionArgs)

            if (a) {
                Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Note not Updated", Toast.LENGTH_LONG).show()
            }

        }
    }
}