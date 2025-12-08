package com.example.myapplication.studentlistapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class StudentListActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    companion object {
        const val REQUEST_ADD_STUDENT = 1
        const val REQUEST_EDIT_STUDENT = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_list)

        listView = findViewById(R.id.listView)

        // Khởi tạo dữ liệu mẫu
        initSampleData()

        // Khởi tạo adapter
        adapter = StudentAdapter(
            this,
            studentList,
            onItemClick = { student ->
                openDetailActivity(student)
            },
            onDeleteClick = { position ->
                showDeleteConfirmDialog(position)
            }
        )

        listView.adapter = adapter
    }

    private fun initSampleData() {
        studentList.add(Student("20210001", "Nguyễn Văn A", "0901234567", "Hà Nội"))
        studentList.add(Student("20210002", "Trần Thị B", "0912345678", "TP HCM"))
        studentList.add(Student("20210003", "Lê Văn C", "0923456789", "Đà Nẵng"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_student -> {
                openAddActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openAddActivity() {
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, REQUEST_ADD_STUDENT)
    }

    private fun openDetailActivity(student: Student) {
        val intent = Intent(this, StudentDetailActivity::class.java)
        intent.putExtra("student", student)
        intent.putExtra("position", studentList.indexOf(student))
        startActivityForResult(intent, REQUEST_EDIT_STUDENT)
    }

    private fun showDeleteConfirmDialog(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${studentList[position].name}?")
            .setPositiveButton("Xóa") { _, _ ->
                studentList.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_ADD_STUDENT -> {
                    val newStudent = data.getParcelableExtra<Student>("student")
                    newStudent?.let {
                        studentList.add(it)
                        adapter.notifyDataSetChanged()
                    }
                }
                REQUEST_EDIT_STUDENT -> {
                    val updatedStudent = data.getParcelableExtra<Student>("student")
                    val position = data.getIntExtra("position", -1)
                    if (updatedStudent != null && position != -1) {
                        studentList[position] = updatedStudent
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}