package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.fragments.FragmentPagerAdapter
import com.example.myapplication.fragments.PostsFragment
import com.example.myapplication.fragments.UsersFragment
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.retrofit.GetDataService
import com.example.myapplication.retrofit.RetrofitClientInstance
import com.nshmura.recyclertablayout.RecyclerTabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    companion object {
        private lateinit var viewModelActivity: ViewModelActivity
        val service: GetDataService =
            RetrofitClientInstance.getRetrofitInstance()!!
                .create(GetDataService::class.java)

        fun deleteUser(position: Int) {
            viewModelActivity.deleteUser(position)
            service.deleteUser(position).enqueue( object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.d("Aici2", "Failed to delete from server")
                    //Toast.makeText(this@MainActivity, "Failed to delete from server", Toast.LENGTH_LONG)
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful)
                        Log.d("Aici2", "Successfully deleted from server")
                    else
                        Log.d("Aici2", "Successfully failed deleted from server")
                        //Toast.makeText(this, "Successfully deleted from server", Toast.LENGTH_LONG)
                }
            })
        }

        fun deletePost(position: Int) {
            viewModelActivity.deletePost(position)
            service.deletePost(position).enqueue( object : Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Log.d("Aici3", "Failed to delete from server")
                    //Toast.makeText(this@MainActivity, "Failed to delete from server", Toast.LENGTH_LONG)
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.isSuccessful)
                        Log.d("Aici3", "Successfully deleted from server")
                    else
                        Log.d("Aici3", "Successfully failed deleted from server")
                    //Toast.makeText(this, "Successfully deleted from server", Toast.LENGTH_LONG)
                }
            })
        }
    }

    private val fragmentPagerAdapter = FragmentPagerAdapter(supportFragmentManager)
    private lateinit var tabsLayout: RecyclerTabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Setting up the tabs
        val viewPager = findViewById<ViewPager>(R.id.container)
        fragmentPagerAdapter.addFragment(UsersFragment(), "Users")
        fragmentPagerAdapter.addFragment(PostsFragment(), "Posts")
        viewPager.adapter = fragmentPagerAdapter

        tabsLayout = findViewById(R.id.tabs)
        tabsLayout.setUpWithViewPager(viewPager)

        viewModelActivity = ViewModelProvider(this).get(ViewModelActivity::class.java)

        Thread(Runnable {
            while (true) {
                val call: Call<List<User?>?>? = service.getUsers()
                call!!.enqueue(object : Callback<List<User?>?> {
                    override fun onResponse(
                        call: Call<List<User?>?>?,
                        response: Response<List<User?>?>
                    ) {
                        response.body()?.forEach {
                            if (it != null) {
                                viewModelActivity.insertUser(it)
                            }
                        }
                    }

                    override fun onFailure(
                        call: Call<List<User?>?>?,
                        t: Throwable?
                    ) {
                        Toast.makeText(
                            applicationContext,
                            "Users refresh failed...\"" + t.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

                Thread.sleep(120000)
            }
        }).start()

        Thread(Runnable {
            while (true) {
                val service =
                    RetrofitClientInstance.getRetrofitInstance()!!
                        .create(GetDataService::class.java)
                val call: Call<List<Post>> = service.getPosts()
                call.enqueue(object : Callback<List<Post>> {
                    override fun onResponse(
                        call: Call<List<Post>>,
                        response: Response<List<Post>>
                    ) {
                        response.body()?.forEach {
                            viewModelActivity.insertPost(it)
                        }
                    }

                    override fun onFailure(
                        call: Call<List<Post>>,
                        t: Throwable?
                    ) {
                        Toast.makeText(
                            applicationContext,
                            "Posts refresh failed..." + t.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

                Thread.sleep(120000)
            }
        }).start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.logoutButton -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivityForResult(intent, 0)
            }
            R.id.deleteAllUsersDataButton -> {
                viewModelActivity.deleteAllUsers()
            }
            R.id.deleteAllPostsDataButton -> {
                viewModelActivity.deleteAllPosts()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, 0)
    }
}
