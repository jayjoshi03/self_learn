package com.example.mycourse.repository

import com.example.mycourse.model.MyCourse

interface CourseRepository {
    fun fetchCourseDetails(
        onSuccess: (response: MyCourse) -> Unit,
        onFailure: (t: Throwable) -> Unit
    )
}