package com.example.myapplication.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity
import com.example.myapplication.MainActivity.Companion.deleteUser
import com.example.myapplication.R
import com.example.myapplication.model.User
import kotlinx.android.synthetic.main.activity_add_user.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

class UserRecyclerViewAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<UserRecyclerViewAdapter.UserViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_useritem, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = users[position]
        holder.userNameTextView.text = current.name
        holder.userUsernameTextView.text = current.username
        holder.userEmailTextView.text = current.email
        holder.userPhoneTextView.text = current.phone.toString()
        holder.userWebsiteTextView.text = current.website

        if (!current.image.isNullOrEmpty()) {
            LoadImageBitmap(current.image!!, holder.userImageView)
        }

        holder.deleteUserButton.setOnClickListener{
            current.id?.let { it1 -> deleteUser(it1) }
        }
    }

    private fun LoadImageBitmap(path : String, imageView: ImageView) {
        try {
            val file = File(path)
            val bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            if (bitmap != null)
                imageView.setImageBitmap(bitmap)
        } catch (e : FileNotFoundException) {
            Log.d("aici", "Loading image failed")
            e.printStackTrace()
        }
    }

    override fun getItemCount() = users.size

    internal fun setUsers(users: List<User>){
        this.users = users
        notifyDataSetChanged()
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userNameTextView : TextView = itemView.findViewById(R.id.userName)
        val userUsernameTextView : TextView = itemView.findViewById(R.id.userUsername)
        val userEmailTextView : TextView = itemView.findViewById(R.id.userEmail)
        val userPhoneTextView : TextView = itemView.findViewById(R.id.userPhoneNumber)
        val userWebsiteTextView : TextView = itemView.findViewById(R.id.userName)
        val userImageView : ImageView = itemView.findViewById(R.id.userImageView)
        val deleteUserButton : ImageView = itemView.findViewById(R.id.deleteUserButton)
    }
}