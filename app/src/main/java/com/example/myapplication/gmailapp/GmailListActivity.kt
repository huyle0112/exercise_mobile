package com.example.myapplication.gmailapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.R

class GmailListActivity : AppCompatActivity() {

    private lateinit var adapter: GmailAdapter
    private val emailList = mutableListOf<Gmail>()
    private var filteredList = listOf<Gmail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gmail_list)

        val listView = findViewById<ListView>(R.id.gmailListView)
        val edtSearch = findViewById<EditText>(R.id.edtSearch)
        val btnAdd = findViewById<ImageButton>(R.id.btnAdd) // Fix: ImageButton

        // Sample data
        emailList.add(Gmail("Google", "Welcome!", "Thanks for joining Gmail...", "10:20"))
        emailList.add(Gmail("Facebook", "Alert!", "Someone tried to login your account...", "09:10"))
        filteredList = emailList

        adapter = GmailAdapter(this, filteredList)
        listView.adapter = adapter

        // Search by sender
        edtSearch.addTextChangedListener {
            val text = it.toString().lowercase()
            filteredList = emailList.filter { mail ->
                mail.sender.lowercase().contains(text)
            }
            adapter.updateList(filteredList)
        }

        // Add new email
        btnAdd.setOnClickListener {
            // Tạo dialog
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_email, null)
            val edtSender = dialogView.findViewById<EditText>(R.id.edtSender)
            val edtSubject = dialogView.findViewById<EditText>(R.id.edtSubject)
            val edtContent = dialogView.findViewById<EditText>(R.id.edtContent)

            val dialog = AlertDialog.Builder(this)
                .setTitle("Compose Email")
                .setView(dialogView)
                .setPositiveButton("Send") { _, _ ->
                    val sender = edtSender.text.toString().ifEmpty { "Unknown Sender" }
                    val subject = edtSubject.text.toString().ifEmpty { "No Subject" }
                    val content = edtContent.text.toString().ifEmpty { "No Content" }
                    val newMail = Gmail(sender, subject, content, "Now")

                    // Thêm vào danh sách đầu tiên
                    emailList.add(0, newMail)
                    filteredList = emailList
                    adapter.updateList(filteredList)
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()
        }


        // Open detail
        listView.setOnItemClickListener { _, _, position, _ ->
            val email = filteredList[position]
            val intent = Intent(this, GmailDetailActivity::class.java)
            intent.putExtra("sender", email.sender)
            intent.putExtra("subject", email.subject)
            intent.putExtra("content", email.content)
            intent.putExtra("time", email.time)
            startActivity(intent)
        }
    }
}

data class Gmail(
    val sender: String,
    val subject: String,
    val content: String,
    val time: String
)