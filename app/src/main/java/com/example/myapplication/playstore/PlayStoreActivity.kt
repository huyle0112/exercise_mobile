package com.example.myapplication.playstore

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import com.example.myapplication.R
import com.example.myapplication.playstore.RecommendAdapter
import com.example.myapplication.playstore.SuggestAdapter

class PlayStoreActivity : AppCompatActivity() {

    private lateinit var rvSuggest: RecyclerView
    private lateinit var rvRecommend: RecyclerView
    private lateinit var etSearch: EditText

    private lateinit var suggestAdapter: SuggestAdapter
    private lateinit var recommendAdapter: RecommendAdapter

    private var suggestList = listOf<AppItem>()
    private var recommendList = listOf<AppItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_store)

        rvSuggest = findViewById(R.id.rvSuggest)
        rvRecommend = findViewById(R.id.rvRecommend)
        etSearch = findViewById(R.id.edtSearch)

        // Sample data (iconUrl can be remote or local "https://..." or use "file:///android_res/mipmap/ic_launcher")
        suggestList = listOf(
            AppItem("Facebook", "Meta", "https://dummyimage.com/200x200/3b5998/ffffff&text=Facebook", 4.2f, "1B+ downloads"),
            AppItem("Instagram", "Meta", "https://dummyimage.com/200x200/e1306c/ffffff&text=Instagram", 4.4f, "1B+ downloads"),
            AppItem("Gmail", "Google", "https://dummyimage.com/200x200/db4437/ffffff&text=Gmail", 4.3f, "1B+ downloads"),
            AppItem("Messenger", "Meta", "https://dummyimage.com/200x200/0084ff/ffffff&text=Messenger", 4.0f, "1B+ downloads"),
            AppItem("Google Maps", "Google", "https://dummyimage.com/200x200/4285F4/ffffff&text=Maps", 4.5f, "1B+ downloads"),
            AppItem("Zalo", "VNG", "https://dummyimage.com/200x200/0066cc/ffffff&text=Zalo", 4.1f, "10M+ downloads"),
            AppItem("Twitter", "X Corp.", "https://dummyimage.com/200x200/1DA1F2/ffffff&text=Twitter", 3.9f, "500M+ downloads"),
            AppItem("LinkedIn", "Microsoft", "https://dummyimage.com/200x200/0077B5/ffffff&text=LinkedIn", 4.0f, "500M+ downloads")
        )

        recommendList = listOf(
            AppItem("YouTube", "Google", "https://dummyimage.com/200x200/FF0000/ffffff&text=YouTube", 4.6f, "1B+ downloads", listOf(
                "https://dummyimage.com/600x300/FF0000/ffffff&text=YT1",
                "https://dummyimage.com/600x300/FF0000/ffffff&text=YT2"
            )),
            AppItem("Spotify", "Spotify Ltd.", "https://dummyimage.com/200x200/1DB954/ffffff&text=Spotify", 4.5f, "500M+ downloads"),
            AppItem("TikTok", "ByteDance", "https://dummyimage.com/200x200/000000/ffffff&text=TikTok", 4.2f, "1B+ downloads"),
            AppItem("Netflix", "Netflix Inc.", "https://dummyimage.com/200x200/E50914/ffffff&text=Netflix", 4.3f, "500M+ downloads"),
            AppItem("Google Drive", "Google", "https://dummyimage.com/200x200/4285F4/ffffff&text=Drive", 4.4f, "1B+ downloads"),
            AppItem("Snapchat", "Snap Inc.", "https://dummyimage.com/200x200/FFFC00/000000&text=Snapchat", 4.0f, "1B+ downloads"),
            AppItem("Shopee", "Shopee Pte. Ltd.", "https://dummyimage.com/200x200/EE4D2D/ffffff&text=Shopee", 4.1f, "100M+ downloads")
        )

        // Setup suggest RecyclerView (vertical)
        suggestAdapter = SuggestAdapter(this, suggestList)
        rvSuggest.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvSuggest.adapter = suggestAdapter

        // Setup recommend RecyclerView (horizontal)
        recommendAdapter = RecommendAdapter(this, recommendList)
        rvRecommend.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvRecommend.adapter = recommendAdapter

        // Search filter
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val q = s?.toString()?.lowercase()?.trim() ?: ""
                if (q.isEmpty()) {
                    suggestAdapter.update(suggestList)
                    recommendAdapter.notifyDataSetChanged()
                } else {
                    val fs = suggestList.filter { it.name.lowercase().contains(q) || it.developer.lowercase().contains(q) }
                    suggestAdapter.update(fs)
                    val fr = recommendList.filter { it.name.lowercase().contains(q) || it.developer.lowercase().contains(q) }
                    recommendAdapter.notifyDataSetChanged() // we could update list if adapter maintained mutable list
                    // Simple approach: recreate adapters with filtered data
                    recommendAdapter = RecommendAdapter(this@PlayStoreActivity, fr)
                    rvRecommend.adapter = recommendAdapter
                }
            }
        })
    }
}

data class AppItem(
    val name: String,
    val developer: String,
    val iconUrl: String,   // hỗ trợ load online
    val rating: Float,
    val downloads: String,
    val screenshots: List<String> = emptyList()
)
