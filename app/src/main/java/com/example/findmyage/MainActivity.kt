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
        val toDoButton = findViewById<Button>(R.id.btnToDo);
        val weatherButton = findViewById<Button>(R.id.btnWeather);


        calculateAgeButton.setOnClickListener {
            val intent = Intent(this, CalculateAge::class.java)
            startActivity(intent)
        }

        playTicTacToeButton.setOnClickListener {
            val intent = Intent(this, TicTacToe::class.java)
            startActivity(intent)
        }

        toDoButton.setOnClickListener {
            val intent = Intent(this, ToDo::class.java)
            startActivity(intent)
        }

        weatherButton.setOnClickListener {
            val intent = Intent(this, Weather::class.java)
            startActivity(intent)
        }


    }

}