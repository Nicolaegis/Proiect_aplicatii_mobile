package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.StringBuilder

class PostRecyclerViewAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<PostRecyclerViewAdapter.PostViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var posts = emptyList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_postitem, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val current = posts[position]
        holder.postUserIdTextView.text = current.userId.toString()
        holder.postTitleTextView.text = current.title
        holder.postBodyTextView.text = current.body

        holder.deletePostButton.setOnClickListener {
            current.id?.let { it1 -> MainActivity.deletePost(it1) }
        }
    }

    override fun getItemCount() = posts.size

    internal fun setPosts(posts: List<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postUserIdTextView : TextView = itemView.findViewById(R.id.postUserId)
        val postTitleTextView : TextView = itemView.findViewById(R.id.postTitle)
        val postBodyTextView : TextView = itemView.findViewById(R.id.postBody)
        val deletePostButton : ImageView = itemView.findViewById(R.id.deletePostButton)
    }
}