package com.me.finaltestapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_table")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val courseName: String,
    val interestedGeeks: String,
    val rating: String,
    val des: String,
    val courseDifficulty: String,
    val courseImage: Int,
    val seatsLeft: String
)