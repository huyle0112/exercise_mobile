package com.example.myapplication.playstore

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_detail)

        val ivIcon = findViewById<ImageView>(R.id.ivIcon)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvDev = findViewById<TextView>(R.id.tvDev)
        val rb = findViewById<RatingBar>(R.id.rbRating)
        val tvRating = findViewById<TextView>(R.id.tvRating)
        val tvDownloads = findViewById<TextView>(R.id.tvDownloads)
        val rvScreens = findViewById<RecyclerView>(R.id.rvScreenshots)
        val btnInstall = findViewById<Button>(R.id.btnInstall)

        val name = intent.getStringExtra("name") ?: ""
        val dev = intent.getStringExtra("developer") ?: ""
        val iconUrl = intent.getStringExtra("iconUrl") ?: ""
        val rating = intent.getFloatExtra("rating", 0f)
        val downloads = intent.getStringExtra("downloads") ?: ""
        val screenshots = intent.getStringArrayListExtra("screenshots") ?: arrayListOf()

        tvName.text = name
        tvDev.text = dev
        rb.rating = rating
        tvRating.text = String.format("%.1f", rating)
        tvDownloads.text = downloads

        Glide.with(this).load(iconUrl).into(ivIcon)

        rvScreens.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvScreens.adapter = ScreenshotAdapter(this, screenshots)

        btnInstall.setOnClickListener {
            // demo: thay bằng hành động thật khi cần
            btnInstall.text = "Installed"
            btnInstall.isEnabled = false
        }
    }
}