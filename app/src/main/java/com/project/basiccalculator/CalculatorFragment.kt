package com.project.basiccalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment


class CalculatorFragment : Fragment() {

    private lateinit var editTextNumber1: EditText
    private lateinit var editTextNumber2: EditText
    private lateinit var textViewResult: TextView
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        // Initialize UI components
        initializeUIComponents(view)
        setButtonListeners()

        return view
    }

    private fun initializeUIComponents(view: View) {
        editTextNumber1 = view.findViewById(R.id.firstNumber)
        editTextNumber2 = view.findViewById(R.id.secondNumber)
        textViewResult = view.findViewById(R.id.result)
        btnAdd = view.findViewById(R.id.btnAdd)
        btnSubtract = view.findViewById(R.id.btnSubtract)
        btnMultiply = view.findViewById(R.id.btnMultiply)
        btnDivide = view.findViewById(R.id.btnDivide)
    }

    private fun setButtonListeners() {
        btnAdd.setOnClickListener {
            displayResult(calculate(editTextNumber1.text.toString(), editTextNumber2.text.toString(), '+'))
        }

        btnSubtract.setOnClickListener {
            displayResult(calculate(editTextNumber1.text.toString(), editTextNumber2.text.toString(), '-'))
        }

        btnMultiply.setOnClickListener {
            displayResult(calculate(editTextNumber1.text.toString(), editTextNumber2.text.toString(), '*'))
        }

        btnDivide.setOnClickListener {
            displayResult(calculate(editTextNumber1.text.toString(), editTextNumber2.text.toString(), '/'))
        }
    }

    private fun calculate(num1Str: String, num2Str: String, operation: Char): Double? {
        val num1 = num1Str.toDoubleOrNull()
        val num2 = num2Str.toDoubleOrNull()

        return if (num1 != null && num2 != null) {
            when (operation) {
                '+' -> num1 + num2
                '-' -> num1 - num2
                '*' -> num1 * num2
                '/' -> if (num2 != 0.0) num1 / num2 else null
                else -> null
            }
        } else {
            null
        }
    }

    private fun displayResult(result: Double?) {
        textViewResult.text = when {
            result == null -> "Invalid input"
            result % 1 == 0.0 -> result.toInt().toString()
            else -> String.format("%.2f", result)
        }
    }
}