package com.example.myapplication.studentlistapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R

class StudentAdapter(
    private val context: Context,
    private val list: MutableList<Student>,
    private val onItemClick: (Student) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.student_item, parent, false)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvMssv = view.findViewById<TextView>(R.id.tvMssv)
        val btnDelete = view.findViewById<ImageView>(R.id.btnDelete)

        val student = list[position]

        tvName.text = student.name
        tvMssv.text = student.mssv

        view.setOnClickListener {
            onItemClick(student)
        }

        btnDelete.setOnClickListener {
            onDeleteClick(position)
        }

        return view
    }
}