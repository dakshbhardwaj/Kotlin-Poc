package com.example.findmyage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CalculateAge : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculate_age)
        var button = findViewById<Button>(R.id.btnCalculate);

        var text = findViewById<EditText>(R.id.etAge);

        var textView = findViewById<TextView>(R.id.tvAge);

        button.setOnClickListener{
            var userDob = Integer.parseInt(text.text.toString())
            val currentYear = Calendar.getInstance().get(Calendar.YEAR)
            val userAge = currentYear - userDob;
            textView.text = userAge.toString();
        }
    }
}