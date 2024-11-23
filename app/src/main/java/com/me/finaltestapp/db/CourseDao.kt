package com.me.finaltestapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course)

    @Query("SELECT * FROM my_table")
    fun getAllCourse(): List<Course>

    @Delete
    suspend fun delete(course: Course)

    @Update
    suspend fun update(course: Course)
}