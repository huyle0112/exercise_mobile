package com.example.myapplication.playstore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class RecommendAdapter(
    private val ctx: Context,
    private var items: List<AppItem>
) : RecyclerView.Adapter<RecommendAdapter.Holder>() {

    inner class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val iv: ImageView = v.findViewById(R.id.ivIcon)
        val tvName: TextView = v.findViewById(R.id.tvName)
        val tvDev: TextView = v.findViewById(R.id.tvDev)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(ctx).inflate(R.layout.recommend_app_item, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvDev.text = item.developer
        Glide.with(ctx).load(item.iconUrl).into(holder.iv)

        // subtle scale animation
        holder.itemView.scaleX = 0.97f
        holder.itemView.scaleY = 0.97f
        holder.itemView.alpha = 0f
        holder.itemView.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(220).start()

        holder.itemView.setOnClickListener {
            val i = Intent(ctx, DetailActivity::class.java)
            i.putExtra("name", item.name)
            i.putExtra("developer", item.developer)
            i.putExtra("iconUrl", item.iconUrl)
            i.putExtra("rating", item.rating)
            i.putExtra("downloads", item.downloads)
            i.putStringArrayListExtra("screenshots", ArrayList(item.screenshots))
            ctx.startActivity(i)
        }
    }

    override fun getItemCount(): Int = items.size
}