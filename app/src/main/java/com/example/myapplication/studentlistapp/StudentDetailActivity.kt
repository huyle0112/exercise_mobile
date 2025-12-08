package com.example.myapplication.studentlistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var edtMssv: EditText
    private lateinit var edtName: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnCancel: Button

    private var student: Student? = null
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        // Thiết lập ActionBar
        supportActionBar?.title = "Chi tiết sinh viên"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Ánh xạ views
        edtMssv = findViewById(R.id.edtMssv)
        edtName = findViewById(R.id.edtName)
        edtPhone = findViewById(R.id.edtPhone)
        edtAddress = findViewById(R.id.edtAddress)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnCancel = findViewById(R.id.btnCancel)

        // Nhận dữ liệu từ Intent
        student = intent.getParcelableExtra("student")
        position = intent.getIntExtra("position", -1)

        // Hiển thị thông tin sinh viên
        student?.let {
            edtMssv.setText(it.mssv)
            edtName.setText(it.name)
            edtPhone.setText(it.phone)
            edtAddress.setText(it.address)
        }

        // Xử lý sự kiện
        btnUpdate.setOnClickListener {
            updateStudent()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun updateStudent() {
        val mssv = edtMssv.text.toString().trim()
        val name = edtName.text.toString().trim()
        val phone = edtPhone.text.toString().trim()
        val address = edtAddress.text.toString().trim()

        // Kiểm tra dữ liệu
        if (mssv.isEmpty() || name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        // Cập nhật sinh viên
        val updatedStudent = Student(mssv, name, phone, address)

        // Trả kết quả về MainActivity
        val resultIntent = Intent()
        resultIntent.putExtra("student", updatedStudent)
        resultIntent.putExtra("position", position)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}