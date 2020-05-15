package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.model.Account
import com.example.myapplication.retrofit.GetDataService
import com.example.myapplication.retrofit.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess


class LoginActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = findViewById<Button>(R.id.loginButton)
        val username = findViewById<EditText>(R.id.username)
        val str : String = StringBuilder().append("").toString()
        username.setText(str)
        val password = findViewById<EditText>(R.id.password)
        password.setText(str)
        //Debug
        val autofill = findViewById<Button>(R.id.autofillDebug)
        autofill.setOnClickListener {
            username.setText("Eliseo@gardner.biz")
            password.setText("1")
        }

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        loginBtn.setOnClickListener {
            if ( username.text.isEmpty() ||
                password.text.isEmpty() ) {
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            val service =
                RetrofitClientInstance.getRetrofitInstance()!!.create(GetDataService::class.java)
            val call: Call<List<Account?>?>? = service.getAccounts()
            call!!.enqueue(object : Callback<List<Account?>?> {
                override fun onResponse(
                    call: Call<List<Account?>?>?,
                    response: Response<List<Account?>?>
                ) {
                    response.body()?.forEach {
                        if (it?.getUsername()?.equals(username.text.toString())!! &&
                            it.getPassword() == password.text.toString()
                        ) {
                            login()
                            return
                        }
                    }
                }

                override fun onFailure(
                    call: Call<List<Account?>?>?,
                    t: Throwable?
                ) {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        this@LoginActivity,
                        "Something went wrong...Please try later!" + t.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    override fun onBackPressed() {
        finish()
        exitProcess(0)
    }

    fun login() {
        val intent = Intent(this, MainActivity::class.java)
        //progressBar.visibility = View.INVISIBLE
        startActivityForResult(intent, 0)
    }
}
