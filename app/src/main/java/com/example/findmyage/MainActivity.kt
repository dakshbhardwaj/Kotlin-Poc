package com.example.findmyage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       var button = findViewById<Button>(R.id.btnCalculateAge);

        button.setOnClickListener{
            val intent = Intent(this, CalculateAge::class.java)
            startActivity(intent)
        }



    }

}