package com.example.mycourse.activity

import android.content.Context
import android.content.SharedPreferences

class SharePreference(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("FilterPrefs", Context.MODE_PRIVATE)

    fun saveFilterValue(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getFilterValue(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun clearAllFilterValues() {
        sharedPreferences.edit().clear().apply()
    }

}