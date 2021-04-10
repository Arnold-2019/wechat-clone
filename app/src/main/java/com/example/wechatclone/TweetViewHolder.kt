package com.example.wechatclone

import android.view.View
import android.widget.ArrayAdapter
import android.widget.GridView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wechatclone.data.Tweet
import kotlinx.android.synthetic.main.fragment_tweet.view.*

class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var playerImages = intArrayOf(R.drawable.ronaldo, R.drawable.felix, R.drawable.bernado, R.drawable.andre)

    fun bind(tweet: Tweet) {


        if (tweet.error.isNullOrEmpty() && tweet.unknownError.isNullOrEmpty()) {

            Glide.with(itemView.context)
                .load(tweet.sender.avatar)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(itemView.avatar)

            itemView.user_name.text = tweet.sender.userName
            itemView.content_text_view.text = tweet.content

            val gridView = itemView.findViewById<GridView>(R.id.grid_view)
            val mainAdapter = itemView.context?.let { ImageAdapter(it, playerImages) }
            if (gridView != null) {
                gridView.adapter = mainAdapter
            }

            val comments = getComments(tweet)


            val commentRecyclerView = itemView.findViewById<RecyclerView>(R.id.comment_recycler_view)
            commentRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = CommentAdapter(comments)
            }
        }
    }

    private fun getComments(tweet: Tweet): MutableList<String> {
        val comments = mutableListOf<String>()
        if (!tweet.comments.isNullOrEmpty()) {
            tweet.comments.forEach {
                comments.add("${it.sender.userName}: ${it.content}")
            }
        }
        return comments
    }
}