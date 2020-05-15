package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.dao.PostDAO
import com.example.myapplication.dao.UserDAO
import com.example.myapplication.model.Post
import com.example.myapplication.model.User

class Repository(
    private val userDAO: UserDAO,
    private val postDAO: PostDAO
) {
    val allUsers: LiveData<List<User>> = userDAO.getUsers()
    val allPosts: LiveData<List<Post>> = postDAO.getPosts()

    suspend fun insertPost(post: Post) {
        postDAO.insert(post)
    }

    suspend fun deletePost(deletePostId: Int) {
        postDAO.delete(deletePostId)
    }

    suspend fun deleteAllPosts() {
        postDAO.deleteAll()
    }

    suspend fun insertUser(user: User) {
        userDAO.insert(user)
    }

    suspend fun deleteUser(deleteUserId: Int) {
        userDAO.delete(deleteUserId)
    }

    suspend fun deleteAllUsers() {
        userDAO.deleteAll()
    }
}