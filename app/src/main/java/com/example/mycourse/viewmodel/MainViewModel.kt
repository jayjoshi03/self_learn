package com.example.mycourse.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycourse.model.MyCourse
import com.example.mycourse.repository.CourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val courseRepository: CourseRepository) :
    ViewModel() {

    fun fetchCurseDetails(): MutableLiveData<MyCourse> {
        val courseDetails = MutableLiveData<MyCourse>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                courseRepository.fetchCourseDetails({ response ->
                    courseDetails.postValue(response)
                }, { t ->
                    Log.e("Employee", "onFailure: ", t)
                })
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return courseDetails
    }
}