package com.example.myapplication.playstore

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.view.View
import com.example.myapplication.R

class ScreenshotAdapter(private val ctx: Context, private val items: List<String>) :
    RecyclerView.Adapter<ScreenshotAdapter.H>() {

    inner class H(v: View) : RecyclerView.ViewHolder(v) {
        val iv: ImageView = v as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val v = LayoutInflater.from(ctx).inflate(R.layout.item_screenshot, parent, false)
        return H(v)
    }

    override fun onBindViewHolder(holder: H, position: Int) {
        Glide.with(ctx).load(items[position]).into(holder.iv)
    }

    override fun getItemCount(): Int = items.size
}
