package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private var currentInput = ""
    private var operand1 = 0
    private var operator: Char? = null
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator)

        textView = findViewById(R.id.textView)

        val numberButtons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
        )
        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener {
                onNumberClick((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.button_plus).setOnClickListener { onOperatorClick('+') }
        findViewById<Button>(R.id.button_minus).setOnClickListener { onOperatorClick('-') }
        findViewById<Button>(R.id.button_mul).setOnClickListener { onOperatorClick('x') }
        findViewById<Button>(R.id.button_div).setOnClickListener { onOperatorClick('/') }

        findViewById<Button>(R.id.button_equal).setOnClickListener { calculate() }

        findViewById<Button>(R.id.button_c).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.button_ce).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.button_bs).setOnClickListener { backspace() }

        findViewById<Button>(R.id.button_negate).setOnClickListener { toggleSign() }
    }

    private fun onNumberClick(number: String) {
        if (isNewOperation) {
            currentInput = ""
            isNewOperation = false
        }
        currentInput += number
        textView.text = currentInput
    }

    private fun onOperatorClick(op: Char) {
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toInt()
            operator = op
            textView.text = op.toString()
            currentInput = ""
        }
    }

    private fun calculate() {
        if (operator == null || currentInput.isEmpty()) return

        val operand2 = currentInput.toInt()
        val result = when (operator) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            'x' -> operand1 * operand2
            '/' -> if (operand2 != 0) operand1 / operand2 else {
                textView.text = "Error"
                return
            }
            else -> return
        }

        textView.text = result.toString()
        currentInput = result.toString()
        operator = null
        isNewOperation = true
    }

    private fun clearAll() {
        currentInput = ""
        operand1 = 0
        operator = null
        textView.text = "0"
    }

    private fun clearEntry() {
        currentInput = ""
        textView.text = "0"
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            textView.text = if (currentInput.isEmpty()) "0" else currentInput
        }
    }

    private fun toggleSign() {
        if (currentInput.isEmpty()) return
        val value = currentInput.toInt() * -1
        currentInput = value.toString()
        textView.text = currentInput
    }
}
