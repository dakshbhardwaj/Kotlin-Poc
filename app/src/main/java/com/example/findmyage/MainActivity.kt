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

        calculateAgeButton.setOnClickListener{
            val intent = Intent(this, CalculateAge::class.java)
            startActivity(intent)
        }

        val playTicTacToeButton = findViewById<Button>(R.id.btnPlayTicTacToe)

        playTicTacToeButton.setOnClickListener{
            val intent = Intent(this, TicTacToe::class.java)
            startActivity(intent)
        }

    }

}