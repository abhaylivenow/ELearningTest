package com.me.finaltestapp.dbops

import com.me.finaltestapp.db.Course
import com.me.finaltestapp.db.CourseDao
import kotlinx.coroutines.flow.Flow

class CourseRepository(private val dao: CourseDao) {

    suspend fun getAllData(): List<Course> = dao.getAllCourse()

    suspend fun insert(course: Course) {
        dao.insert(course)
    }

    suspend fun delete(course: Course) {
        dao.delete(course)
    }

    suspend fun update(course: Course) {
        dao.update(course)
    }
}