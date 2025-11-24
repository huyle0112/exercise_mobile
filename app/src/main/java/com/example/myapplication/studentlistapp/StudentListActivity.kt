package com.example.myapplication.studentlistapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.studentlistapp.StudentAdapter

class StudentListActivity  : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtMssv: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var listView: ListView

    private val studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter

    private var selectedIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_list)

        edtName = findViewById(R.id.edtName)
        edtMssv = findViewById(R.id.edtMssv)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        listView = findViewById(R.id.listView)

        studentList.add(Student("Nguyen Van A","2022001"))
        studentList.add(Student("Nguyen Van B","2022002"))
        studentList.add(Student("Nguyen Van C","2022003"))
        studentList.add(Student("Nguyen Van D","2022004"))
        studentList.add(Student("Nguyen Van E","2022005"))
        studentList.add(Student("Nguyen Van F","2022006"))
        studentList.add(Student("Nguyen Van G","2022007"))
        studentList.add(Student("Nguyen Van H","2022008"))
        studentList.add(Student("Nguyen Van J","2022009"))
        studentList.add(Student("Nguyen Van K","2022010"))
        studentList.add(Student("Nguyen Van L","2022011"))

        adapter = StudentAdapter(
            this,
            studentList,
            onItemClick = { student ->
                loadStudent(student)
            },
            onDeleteClick = { position ->
                studentList.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        )

        listView.adapter = adapter

        btnAdd.setOnClickListener { addStudent() }
        btnUpdate.setOnClickListener { updateStudent() }
    }

    private fun addStudent() {
        val name = edtName.text.toString()
        val mssv = edtMssv.text.toString()

        if (name.isEmpty() || mssv.isEmpty()) return

        studentList.add(Student(name, mssv))
        adapter.notifyDataSetChanged()

        edtName.text.clear()
        edtMssv.text.clear()
    }

    private fun loadStudent(student: Student) {
        edtName.setText(student.name)
        edtMssv.setText(student.mssv)
        selectedIndex = studentList.indexOf(student)
    }

    private fun updateStudent() {
        if (selectedIndex == -1) return

        studentList[selectedIndex].name = edtName.text.toString()
        studentList[selectedIndex].mssv = edtMssv.text.toString()

        adapter.notifyDataSetChanged()

        edtName.text.clear()
        edtMssv.text.clear()
        selectedIndex = -1
    }
}

data class Student(
    var name: String,
    var mssv: String
)
