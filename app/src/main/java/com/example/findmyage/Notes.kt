package com.example.findmyage

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class Notes : AppCompatActivity() {
    var listNotes = ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notes)
        loadDataFromDatabase("%")

    }

    override fun onResume() {
        super.onResume()
        loadDataFromDatabase("%")
    }
    @SuppressLint("Range")
    fun loadDataFromDatabase(title:String){
        val databaseManager = DatabaseManager(this)
        val projection = arrayOf("ID", "Title", "Description")
        val selectionArgs = arrayOf(title)
        listNotes.clear()
        val cursor = databaseManager.Query(projection, "Title like ?",selectionArgs,"Title DESC")

        if(cursor.moveToFirst()){
            do{
                val Id = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("Title"))
                val Description = cursor.getString(cursor.getColumnIndex("Description"))
                listNotes.add(Note(Id,Title,Description))
            }while (cursor.moveToNext())
        }
        val myNoteListAdapter = notesAdapter(this, listNotes)
        findViewById<ListView>(R.id.lvNotesList).adapter = myNoteListAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_app_menu, menu)

        val searchView = menu!!.findItem(R.id.btnSearchNote).actionView as SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                loadDataFromDatabase("%"+ p0 + "%")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnNavToAddNote -> {
                val intent = Intent(this, AddNote::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    inner class notesAdapter(context: Context, listNotes: ArrayList<Note>) : BaseAdapter() {

        var adapterListNotes = listNotes
        var context :Context? = context
        override fun getCount(): Int {
            return this.adapterListNotes.size
        }

        override fun getItem(p0: Int): Any {
            return adapterListNotes[p0]
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.note_card, null)
            val myNote = adapterListNotes[p0]
            myView.findViewById<TextView>(R.id.tvTitle).text = myNote.noteTitle
            myView.findViewById<TextView>(R.id.tvDesc).text = myNote.noteDes
            myView.findViewById<ImageView>(R.id.ivDelete).setOnClickListener(
                View.OnClickListener {
                    val databaseManager = DatabaseManager(this.context!!)
                    val selectionArgs = arrayOf(myNote.noteId.toString())
                    databaseManager.deleteNote("ID =?",selectionArgs)
                    loadDataFromDatabase("%")
                }
            )
            myView.findViewById<ImageView>(R.id.ivEdit).setOnClickListener(
                View.OnClickListener {
                    navToUpdateNote(myNote)
                }
            )
            return myView
        }
    }
    fun navToUpdateNote(note:Note){
        val intent = Intent(this, AddNote::class.java)
        intent.putExtra("Id", note.noteId)
        intent.putExtra("Title", note.noteTitle)
        intent.putExtra("Des", note.noteDes)
        startActivity(intent)
    }
}
