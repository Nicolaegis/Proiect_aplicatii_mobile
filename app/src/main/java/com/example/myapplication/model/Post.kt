package com.example.myapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "post_table")
data class Post(
    @SerializedName("userId")
    @ColumnInfo(name = "userId")
    var userId: Int,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String,
    @SerializedName("body")
    @ColumnInfo(name = "body")
    var body: String,
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @ColumnInfo(name = "id")
    var id: Int? = null
) {
}