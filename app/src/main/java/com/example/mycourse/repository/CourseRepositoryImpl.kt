package com.example.mycourse.repository

import android.util.Log
import com.example.mycourse.api.ApiClient
import com.example.mycourse.model.MyCourse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseRepositoryImpl(private val api: ApiClient) : CourseRepository {
    override fun fetchCourseDetails(
        onSuccess: (response: MyCourse) -> Unit,
        onFailure: (t: Throwable) -> Unit
    ) {
        api.fetchCourseDetails().enqueue(object : Callback<MyCourse> {
            override fun onResponse(
                call: Call<MyCourse>,
                response: Response<MyCourse>
            ) {
                response.body()?.let { user ->
                    onSuccess.invoke(user)
                    Log.e("COURSE DETAILS", user.toString())
                }
            }

            override fun onFailure(call: Call<MyCourse>, t: Throwable) {
                onFailure.invoke(t)
                Log.e("COURSE DETAILS ERROR", t.toString())
            }
        })
    }

}