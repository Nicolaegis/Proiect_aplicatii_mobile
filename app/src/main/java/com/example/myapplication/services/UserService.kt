package com.example.myapplication.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.dao.UserDAO
import com.example.myapplication.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
public abstract class UserService : RoomDatabase(){
    abstract fun userDAO(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: UserService? = null

        fun getDatabase(context: Context): UserService {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance;

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserService::class.java,
                    "users2_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}