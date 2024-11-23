package com.me.finaltestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.me.finaltestapp.db.Course
import com.me.finaltestapp.dbops.CourseRepository
import com.me.finaltestapp.generateSampleCourses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class CourseViewModel(
    private val courseRepository: CourseRepository
) : ViewModel() {

    val courseListLD = MutableStateFlow<List<Course>>(emptyList())

    init {
        addSampleCoursesIfNeeded()
        fetchCourse()
    }

    private fun addSampleCoursesIfNeeded() {
        viewModelScope.launch(Dispatchers.IO) {
            val existingContacts = courseRepository.getAllData()
            if (existingContacts.isEmpty()) {
                val sampleCourses = generateSampleCourses()
                sampleCourses.forEach { courseRepository.insert(it) }
            }
        }
    }

    fun fetchCourse() {
        viewModelScope.launch(Dispatchers.IO) {
            courseListLD.value = courseRepository.getAllData()
        }
    }
}



