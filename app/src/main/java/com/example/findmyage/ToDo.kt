package com.example.findmyage

import android.os.Bundle
import android.util.SparseBooleanArray
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ToDo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        var itemlist = arrayListOf<String>()
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, itemlist);

        var addButton = findViewById<Button>(R.id.btnAdd);
        var inputValue =  findViewById<EditText>(R.id.edtAddItem);
        var listView = findViewById<ListView>(R.id.listView)
        var delete = findViewById<Button>(R.id.btnDelete)
        var clear = findViewById<Button>(R.id.btnClear)

        addButton.setOnClickListener {
            itemlist.add(inputValue.text.toString())
            listView.adapter =  adapter
            adapter.notifyDataSetChanged()

            inputValue.text.clear()
        }

        delete.setOnClickListener {
            val position: SparseBooleanArray = listView.checkedItemPositions
            val count = listView.count
            var item = count - 1
            while (item >= 0) {
                if (position.get(item))
                {
                    adapter.remove(itemlist.get(item))
                }
                item--
            }
            position.clear()
            adapter.notifyDataSetChanged()
        }

        clear.setOnClickListener {

            itemlist.clear()
            adapter.notifyDataSetChanged()

        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            android.widget.Toast.makeText(this, "You Selected the item --> "+itemlist.get(i), android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}