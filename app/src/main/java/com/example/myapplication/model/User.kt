package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "user_table")
data class User(
    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String,
    @SerializedName("username")
    @ColumnInfo(name = "username")
    var username: String,
    @SerializedName("email")
    @ColumnInfo(name = "email")
    var email: String,
    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    var phone: String,
    @SerializedName("website")
    @ColumnInfo(name = "website")
    var website: String,
    @SerializedName("image")
    @ColumnInfo(name = "image")
    var image: String? = String(),
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int? = null
) {
}