package com.example.myapplication.studentlistapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    var mssv: String,
    var name: String,
    var phone: String,
    var address: String
) : Parcelable