package com.project.basiccalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var editTextNumber1: EditText
    lateinit var editTextNumber2: EditText
    lateinit var textViewResult: TextView
    lateinit var btnAdd: Button
    lateinit var btnSubtract: Button
    lateinit var btnMultiply: Button
    lateinit var btnDivide: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNumber1 = findViewById(R.id.firstNumber)
        editTextNumber2 = findViewById(R.id.secondNumber)
        textViewResult = findViewById(R.id.result)
        btnAdd = findViewById(R.id.btnAdd)
        btnSubtract = findViewById(R.id.btnSubtract)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)

        btnAdd.setOnClickListener {
            calculate('+')
        }

        btnSubtract.setOnClickListener {
            calculate('-')
        }

        btnMultiply.setOnClickListener {
            calculate('*')
        }

        btnDivide.setOnClickListener {
            calculate('/')
        }
    }

    private fun calculate(operation: Char) {
        val num1 = editTextNumber1.text.toString().toDoubleOrNull()
        val num2 = editTextNumber2.text.toString().toDoubleOrNull()

        if (num1 != null && num2 != null) {
            val result = when (operation) {
                '+' -> num1 + num2
                '-' -> num1 - num2
                '*' -> num1 * num2
                '/' -> if (num2 != 0.0) num1 / num2 else null
                else -> null
            }
            if (result != null) {
                textViewResult.text = if (result % 1 == 0.0) {
                    result.toInt().toString()
                } else {
                    String.format("%.2f", result)
                }
            } else {
                textViewResult.text = "Cannot divide by zero"
            }
        } else {
            textViewResult.text = "Invalid input"
        }
    }
}
