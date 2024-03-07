package com.example.mycourse.api

import com.example.mycourse.model.MyCourse
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {

    @GET("tfRSPNS")
    fun fetchCourseDetails(): Call<MyCourse>

}