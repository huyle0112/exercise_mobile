package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt


class NumberActivity : AppCompatActivity() {

    private lateinit var edtNumber: EditText
    private lateinit var listView: ListView
    private lateinit var txtMessage: TextView

    private lateinit var group1: RadioGroup
    private lateinit var group2: RadioGroup

    private lateinit var group1Listener: RadioGroup.OnCheckedChangeListener
    private lateinit var group2Listener: RadioGroup.OnCheckedChangeListener

    private val numbers = ArrayList<Int>()
    private lateinit var adapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.number)

        edtNumber = findViewById(R.id.edtNumber)
        listView = findViewById(R.id.listView)
        txtMessage = findViewById(R.id.txtMessage)

        group1 = findViewById(R.id.radioGroup1)
        group2 = findViewById(R.id.radioGroup2)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers)
        listView.adapter = adapter



        edtNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = updateList()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        group1Listener = RadioGroup.OnCheckedChangeListener { _, _ ->
            if (group1.checkedRadioButtonId != -1 && group2.checkedRadioButtonId != -1) {
                group2.setOnCheckedChangeListener(null)
                group2.clearCheck()
                group2.setOnCheckedChangeListener(group2Listener)
            }
            updateList()
        }

        group2Listener = RadioGroup.OnCheckedChangeListener { _, _ ->
            if (group2.checkedRadioButtonId != -1 && group1.checkedRadioButtonId != -1) {
                group1.setOnCheckedChangeListener(null)
                group1.clearCheck()
                group1.setOnCheckedChangeListener(group1Listener)
            }
            updateList()
        }

        group1.setOnCheckedChangeListener(group1Listener)
        group2.setOnCheckedChangeListener(group2Listener)
    }

    private fun updateList() {

        numbers.clear()
        val input = edtNumber.text.toString()
        if (input.isEmpty()) {
            adapter.notifyDataSetChanged()
            txtMessage.visibility = TextView.GONE
            return
        }


        val n = input.toInt()
        for (i in 1 until n) {
            if (isSelected(i)) numbers.add(i)
        }

        adapter.notifyDataSetChanged()
        txtMessage.visibility = if (numbers.isEmpty()) TextView.VISIBLE else TextView.GONE
    }


    private fun getCheckedId(): Int {
        return if (group1.checkedRadioButtonId != -1)
            group1.checkedRadioButtonId
        else
            group2.checkedRadioButtonId
    }

    private fun isSelected(x: Int): Boolean {
        return when (getCheckedId()) {
            R.id.rOdd -> x % 2 != 0
            R.id.rEven -> x % 2 == 0
            R.id.rPrime -> isPrime(x)
            R.id.rPerfect -> isPerfect(x)
            R.id.rSquare -> sqrt(x.toDouble()).toInt().toDouble() == sqrt(x.toDouble())
            R.id.rFibo -> isFibo(x)
            else -> false
        }
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..sqrt(n.toDouble()).toInt()) if (n % i == 0) return false
        return true
    }

    private fun isPerfect(n: Int): Boolean {
        var sum = 1
        for (i in 2..n / 2) if (n % i == 0) sum += i
        return n > 1 && sum == n
    }

    private fun isFibo(n: Int): Boolean {
        var a = 0
        var b = 1
        while (b < n) {
            val temp = a + b
            a = b
            b = temp
        }
        return b == n
    }
}
