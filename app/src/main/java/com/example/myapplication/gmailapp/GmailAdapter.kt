package com.example.myapplication.gmailapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myapplication.R

class GmailAdapter(
    private val context: Context,
    private var items: List<Gmail>
) : ArrayAdapter<Gmail>(context, R.layout.gmail_item, items) {

    fun updateList(newList: List<Gmail>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Gmail = items[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.gmail_item, parent, false)

        val item = items[position]

        view.findViewById<TextView>(R.id.tvSender).text = item.sender
        view.findViewById<TextView>(R.id.tvSubject).text = item.subject
        view.findViewById<TextView>(R.id.tvPreview).text = item.content.take(40) + "..."
        view.findViewById<TextView>(R.id.tvTime).text = item.time

        return view
    }
}