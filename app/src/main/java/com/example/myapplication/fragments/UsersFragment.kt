package com.example.myapplication.fragments

import android.app.Activity
import android.content.Context
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
import com.example.myapplication.AddUserActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.ViewModelActivity
import com.example.myapplication.adapters.UserRecyclerViewAdapter
import com.example.myapplication.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileNotFoundException

class UsersFragment : Fragment() {

    private val newUserRequestCode = 1
    private lateinit var viewModelActivity: ViewModelActivity
    private lateinit var adapter: UserRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vew = inflater.inflate(R.layout.users_fragment, container, false)

        val recyclerView = vew?.findViewById<RecyclerView>(R.id.usersRecyclerView)
        adapter = context?.let { UserRecyclerViewAdapter(it) }!!
        if (recyclerView != null) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

        viewModelActivity = ViewModelProvider(this).get(ViewModelActivity::class.java)
        viewModelActivity.allUsers.observe(viewLifecycleOwner, Observer { users ->
            users?.let { adapter.setUsers(it) }
        })

        vew?.findViewById<FloatingActionButton>(R.id.addNewUser)?.setOnClickListener {
            val intent = Intent(activity, AddUserActivity::class.java)
            startActivityForResult(intent, newUserRequestCode)
        }

        return vew
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newUserRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringArrayExtra(AddUserActivity.EXTRA_REPLY)?.let {
                val user = User(it[0],
                    it[1],
                    it[2],
                    it[3],
                    it[4],
                    it[5])
                viewModelActivity.insertUser(user)
                MainActivity.service.postUser(user).enqueue( object : Callback<User> {
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.d("Aici2", "Failed to insert to server")
                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if(response.isSuccessful)
                            Log.d("Aici2", "Successfully inserted to server")
                        else
                            Log.d("Aici2", "Successfully failed inserted to server")
                    }
                })
            }
        }else {
            Toast.makeText(context,
                "Fill all",
                Toast.LENGTH_LONG).show()
        }
    }
}