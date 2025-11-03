package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var edtFirstName: EditText
    private lateinit var edtLastName: EditText
    private lateinit var edtBirthday: EditText
    private lateinit var edtAddress: EditText
    private lateinit var edtEmail: EditText
    private lateinit var calendarView: CalendarView
    private lateinit var btnSelect: Button
    private lateinit var btnRegister: Button
    private lateinit var checkBoxAgree: CheckBox
    private lateinit var radioGroupGender: RadioGroup

    private var isCalendarVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        edtFirstName = findViewById(R.id.edit_first_name)
        edtLastName = findViewById(R.id.edit_last_name)
        edtBirthday = findViewById(R.id.edit_birthday)
        edtAddress = findViewById(R.id.edit_address)
        edtEmail = findViewById(R.id.edit_email)
        calendarView = findViewById(R.id.calendarView)
        btnSelect = findViewById(R.id.button_selected)
        btnRegister = findViewById(R.id.button_register)
        checkBoxAgree = findViewById(R.id.check_box_agree)
        radioGroupGender = findViewById(R.id.radio_group_gender)

        calendarView.visibility = View.GONE

        btnSelect.setOnClickListener {
            isCalendarVisible = !isCalendarVisible
            calendarView.visibility = if (isCalendarVisible) View.VISIBLE else View.GONE
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            edtBirthday.setText(date)
            calendarView.visibility = View.GONE
            isCalendarVisible = false
        }

        btnRegister.setOnClickListener { validateInputs() }
    }

    private fun validateInputs() {
        var allValid = true

        val fields = listOf(edtFirstName, edtLastName, edtBirthday, edtAddress, edtEmail)

        for (field in fields) {
            if (field.text.toString().trim().isEmpty()) {
                field.setBackgroundColor(Color.parseColor("#FFCDD2")) // Đỏ nhạt
                allValid = false
            } else {
                field.setBackgroundColor(Color.parseColor("#EEEEEE")) // Màu nền gốc
            }
        }

        if (radioGroupGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            allValid = false
        }

        if (!checkBoxAgree.isChecked) {
            Toast.makeText(this, "Please agree to terms", Toast.LENGTH_SHORT).show()
            allValid = false
        }

        if (allValid) {
            Toast.makeText(this, "Register Successful!", Toast.LENGTH_LONG).show()
        }
    }
}
