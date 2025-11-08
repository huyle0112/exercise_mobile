package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CurrencyActivity: AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editFrom: EditText
    private lateinit var editTo: EditText
    private lateinit var keypad: GridLayout
    private lateinit var btnClear: Button

    private var activeEdit: EditText? = null
    private val rates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.86,
        "GBP" to 0.76,
        "JPY" to 153.54,
        "AUD" to 1.54,
        "CAD" to 1.41,
        "CHF" to 0.81,
        "CNY" to 7.12,
        "KRW" to 1455.99,
        "VND" to 26309.97
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        editFrom = findViewById(R.id.editFrom)
        editTo = findViewById(R.id.editTo)
        keypad = findViewById(R.id.keypad)
        btnClear = findViewById(R.id.btnClear)

        val currencies = rates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter
        spinnerFrom.setSelection(0)
        spinnerTo.setSelection(1)

        // Mặc định nhập bên editFrom
        activeEdit = editFrom

        editFrom.setOnClickListener { activeEdit = editFrom }
        editTo.setOnClickListener { activeEdit = editTo }

        // Xử lý bàn phím số
        for (i in 0 until keypad.childCount) {
            val btn = keypad.getChildAt(i) as Button
            btn.setOnClickListener { onKeyPressed(btn.text.toString()) }
        }

        btnClear.setOnClickListener {
            editFrom.setText("")
            editTo.setText("")
        }
    }

    private fun onKeyPressed(value: String) {
        val target = activeEdit ?: return
        var current = target.text.toString()

        when (value) {
            "⌫" -> if (current.isNotEmpty()) current = current.dropLast(1)
            else -> current += value
        }

        target.setText(current)

        if (target == editFrom)
            convert(editFrom, editTo, spinnerFrom, spinnerTo)
        else
            convert(editTo, editFrom, spinnerTo, spinnerFrom)
    }

    private fun convert(sourceEdit: EditText, targetEdit: EditText, sourceSpinner: Spinner, targetSpinner: Spinner) {
        val from = sourceSpinner.selectedItem.toString()
        val to = targetSpinner.selectedItem.toString()
        val input = sourceEdit.text.toString().toDoubleOrNull()

        if (input != null) {
            val usdValue = input / (rates[from] ?: 1.0)
            val result = usdValue * (rates[to] ?: 1.0)
            targetEdit.setText(String.format("%.4f", result))
        } else {
            targetEdit.setText("")
        }
    }
}
