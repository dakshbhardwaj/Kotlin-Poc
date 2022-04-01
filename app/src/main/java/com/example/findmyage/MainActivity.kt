package com.example.findmyage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       val calculateAgeButton = findViewById<Button>(R.id.btnCalculateAge)
        val playTicTacToeButton = findViewById<Button>(R.id.btnPlayTicTacToe)
        var toDobutton = findViewById<Button>(R.id.btnToDo);


        calculateAgeButton.setOnClickListener{
            val intent = Intent(this, CalculateAge::class.java)
            startActivity(intent)
        }

        playTicTacToeButton.setOnClickListener{
            val intent = Intent(this, TicTacToe::class.java)
            startActivity(intent)
        }

        toDobutton.setOnClickListener{
            val intent = Intent(this, ToDo::class.java)
            startActivity(intent)
        }



    }

}