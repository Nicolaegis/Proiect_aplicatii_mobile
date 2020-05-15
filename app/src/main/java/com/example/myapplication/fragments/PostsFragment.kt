package com.example.myapplication.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.adapters.PostRecyclerViewAdapter
import com.example.myapplication.adapters.UserRecyclerViewAdapter
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsFragment : Fragment() {

    private val newPostRequestCode = 1
    private lateinit var viewModelActivity: ViewModelActivity
    private lateinit var adapter: PostRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vew = inflater.inflate(R.layout.posts_fragment, container, false)

        val recyclerView = vew?.findViewById<RecyclerView>(R.id.postRecyclerView)
        adapter = context?.let { PostRecyclerViewAdapter(it) }!!
        if (recyclerView != null) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        viewModelActivity = ViewModelProvider(this).get(ViewModelActivity::class.java)
        viewModelActivity.allPosts.observe(viewLifecycleOwner, Observer { posts ->
            posts?.let { adapter.setPosts(it) }
        })

        vew?.findViewById<FloatingActionButton>(R.id.addNewPost)?.setOnClickListener {
            val intent = Intent(activity, AddPostActivity::class.java)
            startActivityForResult(intent, newPostRequestCode)
        }

        return vew
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newPostRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringArrayExtra(AddPostActivity.EXTRA_REPLY)?.let {
                val post = Post(
                    it[0].toInt(),
                    it[1],
                    it[2]
                )
                viewModelActivity.insertPost(post)
                MainActivity.service.postPost(post).enqueue( object : Callback<Post> {
                    override fun onFailure(call: Call<Post>, t: Throwable) {
                        Log.d("Aici2", "Failed to insert to server")
                    }

                    override fun onResponse(call: Call<Post>, response: Response<Post>) {
                        if(response.isSuccessful)
                            Log.d("Aici2", "Successfully inserted to server")
                        else
                            Log.d("Aici2", "Successfully failed inserted to server")
                    }
                })
            }
        } else {
            Toast.makeText(
                context,
                "Fill all",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}