package com.example.myapplication.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.User

@Dao
interface UserDAO {

    @Query("Select * from user_table ORDER BY id ASC")
    fun getUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table WHERE id = :deleteUser")
    suspend fun delete(deleteUser : Int)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll();
}