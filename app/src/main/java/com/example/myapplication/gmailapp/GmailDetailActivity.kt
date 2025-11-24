package com.example.myapplication.gmailapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class GmailDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gmail_detail)

        val sender = intent.getStringExtra("sender")
        val subject = intent.getStringExtra("subject")
        val content = intent.getStringExtra("content")
        val time = intent.getStringExtra("time")

        findViewById<TextView>(R.id.tvSender).text = sender
        findViewById<TextView>(R.id.tvSubject).text = subject
        findViewById<TextView>(R.id.tvContent).text = content
        findViewById<TextView>(R.id.tvTime).text = time
    }
}