package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.Post

@Dao
interface PostDAO {

    @Query("Select * from post_table ORDER BY userId ASC")
    fun getPosts(): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: Post)

    @Query("DELETE FROM post_table WHERE id = :deletePost")
    suspend fun delete(deletePost : Int)

    @Query("DELETE FROM post_table")
    suspend fun deleteAll();
}