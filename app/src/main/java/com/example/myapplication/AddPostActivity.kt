package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_add_user.*

class AddPostActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY = "addPost"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        val addBtn = findViewById<FloatingActionButton>(R.id.addNewPost)
        addBtn.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(postUserId.text) ||
                TextUtils.isEmpty(postTitle.text) ||
                TextUtils.isEmpty(postBody.text)
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else {
                val str = arrayOfNulls<String>(6)
                str[0] = postUserId.text.toString()
                str[1] = postTitle.text.toString()
                str[2] = postBody.text.toString()

                replyIntent.putExtra(EXTRA_REPLY, str)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.logoutButton){
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, 0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        intent = Intent(this, MainActivity::class.java)
        startActivityForResult(intent, 0)
    }
}
