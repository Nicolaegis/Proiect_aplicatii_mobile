package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "account_table")
data class Account(
    @SerializedName("email")
    @ColumnInfo(name = "username")
    private var email: String,
    @field:SerializedName("id")
    @ColumnInfo(name = "password")
    private var password: String
) {
    override fun toString(): String {
        return "Account(username='$email', password='$password')"
    }

    fun setUsername(username: String?) {
        this.email = username!!
    }

    fun setPassword(password: String?) {
        this.password = password!!
    }

    fun getUsername() = email

    fun getPassword() = password
}