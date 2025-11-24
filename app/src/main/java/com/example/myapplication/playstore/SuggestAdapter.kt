package com.example.myapplication.playstore

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class SuggestAdapter(
    private val ctx: Context,
    private var items: List<AppItem>
) : RecyclerView.Adapter<SuggestAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val ivIcon: ImageView = v.findViewById(R.id.ivIcon)
        val tvName: TextView = v.findViewById(R.id.tvName)
        val tvDev: TextView = v.findViewById(R.id.tvDev)
        val rb: RatingBar = v.findViewById(R.id.rbRating)
        val tvDownloads: TextView = v.findViewById(R.id.tvDownloads)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(ctx).inflate(R.layout.suggest_app_item, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.tvName.text = item.name
        holder.tvDev.text = item.developer
        holder.rb.rating = item.rating
        holder.tvDownloads.text = item.downloads

        Glide.with(ctx).load(item.iconUrl).into(holder.ivIcon)

        // Simple appear + scale animation
        holder.itemView.alpha = 0f
        holder.itemView.scaleX = 0.95f
        holder.itemView.scaleY = 0.95f
        holder.itemView.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(250).start()

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

    fun update(newList: List<AppItem>) {
        items = newList
        notifyDataSetChanged()
    }
}