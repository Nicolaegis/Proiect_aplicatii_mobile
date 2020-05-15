package com.example.myapplication.retrofit

import com.example.myapplication.model.Account
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface GetDataService {
    @GET("/comments")
    fun getAccounts(): Call<List<Account?>?>?

    @POST("/comments")
    fun postAccount(@Body loginInfo : Account) : Call<Account>?

    @DELETE("/comments/{id}")
    fun deleteAccount(@Path("id") id: Int) : Call<Unit>

    @GET("/users")
    fun getUsers(): Call<List<User?>?>?

    @POST("/users")
    fun postUser(@Body user : User) : Call<User>

    @DELETE("/users/{id}")
    fun deleteUser(@Path("id") id: Int) : Call<Unit>

    @GET("/posts")
    fun getPosts(): Call<List<Post>>

    @POST("/posts")
    fun postPost(@Body post : Post) : Call<Post>

    @DELETE("/posts/{id}")
    fun deletePost(@Path("id") id: Int) : Call<Unit>
}