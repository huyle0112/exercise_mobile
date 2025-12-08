package com.example.myapplication.studentlistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class AddStudentActivity : AppCompatActivity() {

    private lateinit var edtMssv: EditText
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        // Thiết lập ActionBar
        supportActionBar?.title = "Thêm sinh viên mới"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Ánh xạ views
        edtMssv = findViewById(R.id.edtMssv)
        edtName = findViewById(R.id.edtName)
        edtPhone = findViewById(R.id.edtPhone)
        edtAddress = findViewById(R.id.edtAddress)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        // Xử lý sự kiện
        btnSave.setOnClickListener {
            saveStudent()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveStudent() {
        val mssv = edtMssv.text.toString().trim()
        val name = edtName.text.toString().trim()
        val phone = edtPhone.text.toString().trim()
        val address = edtAddress.text.toString().trim()

        // Kiểm tra dữ liệu
        if (mssv.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        // Tạo sinh viên mới
        val newStudent = Student(mssv, name, phone, address)

        // Trả kết quả về MainActivity
        val resultIntent = Intent()
        resultIntent.putExtra("student", newStudent)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}