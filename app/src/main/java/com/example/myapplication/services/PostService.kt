package com.example.myapplication.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.myapplication.dao.PostDAO
import com.example.myapplication.dao.UserDAO
import com.example.myapplication.model.Post
import com.example.myapplication.model.User

@Database(entities = [Post::class], version = 1, exportSchema = false)
public abstract class PostService : RoomDatabase(){
    abstract fun postDAO(): PostDAO

    companion object {
        @Volatile
        private var INSTANCE: PostService? = null

        fun getDatabase(context: Context): PostService {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance;

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostService::class.java,
                    "posts3_table"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}