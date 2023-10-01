package com.example.newsapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects

class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
   // val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //news function call
        getNews()
    }


    public fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in", 1)
        news.enqueue(object : Callback<News> {

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Error", "Error in Processing", t)
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    Log.d("Pass", news.toString())
                    
                    val newsList: RecyclerView = findViewById(R.id.newsList)
                    adapter = NewsAdapter(this@MainActivity, news.articles)
                    newsList.adapter = adapter
                    newsList.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        })
    }

}

