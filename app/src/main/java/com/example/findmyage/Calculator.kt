package com.example.findmyage

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Calculator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator)
    }

    fun onNumberButtonPress(view: View) {
        isFirstNumber = false
        val editText = findViewById<EditText>(R.id.etNumberText)
        val selectedBtn = view as Button
        var btnValue = ""
        when (selectedBtn.id) {
            R.id.btn0 -> {
                btnValue = "0"
            }
            R.id.btn1 -> {
                btnValue = "1"
            }
            R.id.btn2 -> {
                btnValue = "2"
            }
            R.id.btn3 -> {
                btnValue = "3"
            }
            R.id.btn4 -> {
                btnValue = "4"
            }
            R.id.btn5 -> {
                btnValue = "5"
            }
            R.id.btn6 -> {
                btnValue = "6"
            }
            R.id.btn7 -> {
                btnValue = "7"
            }
            R.id.btn8 -> {
                btnValue = "8"
            }
            R.id.btn9 -> {
                btnValue = "9"
            }
            R.id.btnDot -> {
                btnValue = "."
            }
            R.id.btnNegate -> {
                editText.setText("-"+editText.text.toString())
            }
        }
        editText.setText(editText.text.toString() + btnValue)
    }

    var op = ""
    var currVal: String = "0"
    var isFirstNumber = true


    fun OnOperationButtonPress(view: View) {
        if (isFirstNumber) {
            Toast.makeText(this, "Enter a number before operation", Toast.LENGTH_LONG).show()
        } else {
            val editText = findViewById<EditText>(R.id.etNumberText)
            val selectedBtn = view as Button
            when (selectedBtn.id) {
                R.id.btnAdd -> {
                    op = "+"
                }
                R.id.btnSub -> {
                    op = "-"
                }
                R.id.btnMult -> {
                    op = "*"
                }
                R.id.btnDiv -> {
                    op = "/"
                }
            }
            currVal = editText.text.toString()
            editText.setText("0")
        }
    }

    fun OnEqualsPress(view: View) {
        val editText = findViewById<EditText>(R.id.etNumberText)
        val secondVal: String = editText.text.toString()
        var res: Double = "0".toDouble()
        if (op != "") {
            when (op) {
                "+" -> res = currVal.toDouble() + secondVal.toDouble()
                "-" -> res = currVal.toDouble() - secondVal.toDouble()
                "*" -> res = currVal.toDouble() * secondVal.toDouble()
                "/" -> res = currVal.toDouble() / secondVal.toDouble()
            }
            editText.setText(res.toString())
        } else {
            editText.setText(secondVal)
        }
        currVal = res.toString()
    }

    fun onClearPress(view: View) {
        val editText = findViewById<EditText>(R.id.etNumberText)
        editText.setText("0")
        isFirstNumber = true
    }

    fun onPercentPress(view: View) {
        val editText = findViewById<EditText>(R.id.etNumberText)
        editText.setText((editText.text.toString().toDouble() / 100).toString())

    }
}