package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Post
import com.example.myapplication.model.User
import com.example.myapplication.repository.Repository
import com.example.myapplication.services.PostService
import com.example.myapplication.services.UserService
import kotlinx.coroutines.launch

class ViewModelActivity(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(
        UserService.getDatabase(application).userDAO(),
        PostService.getDatabase(application).postDAO()
    )

    val allUsers: LiveData<List<User>>
    val allPosts: LiveData<List<Post>>

    init {
        allUsers = repository.allUsers
        allPosts = repository.allPosts
    }

    fun insertUser(user: User) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun deleteUser(personId: Int) {
        viewModelScope.launch {
            repository.deleteUser(personId)
        }
    }

    fun deleteAllUsers() {
        viewModelScope.launch {
            repository.deleteAllUsers()
        }
    }

    fun insertPost(post: Post) {
        viewModelScope.launch {
            repository.insertPost(post)
        }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            repository.deletePost(postId)
        }
    }

    fun deleteAllPosts() {
        viewModelScope.launch {
            repository.deleteAllPosts()
        }
    }
}