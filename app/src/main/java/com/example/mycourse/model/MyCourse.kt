package com.example.mycourse.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyCourse(
    @SerializedName("result") var result: Result? = Result()
) : Parcelable

@Parcelize
data class Result(
    @SerializedName("index") var index: ArrayList<Index> = arrayListOf(),
    @SerializedName("collections") var collections: Collections? = Collections()
) : Parcelable

@Parcelize
data class Collections(
    @SerializedName("smart") var smart: ArrayList<Smart> = arrayListOf(),
    @SerializedName("user") var user: ArrayList<User> = arrayListOf(),
    @SerializedName("curated") var curated: ArrayList<String> = arrayListOf()
) : Parcelable

@Parcelize
data class Index(
    @SerializedName("downloadid") var downloadId: Int? = null,
    @SerializedName("cd_downloads") var cdDownloads: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("authorid") var authorId: Int? = null,
    @SerializedName("video_count") var videoCount: Int? = null,
    @SerializedName("style_tags") var styleTags: ArrayList<String> = arrayListOf(),
    @SerializedName("skill_tags") var skillTags: ArrayList<String> = arrayListOf(),
    @SerializedName("series_tags") var seriesTags: ArrayList<String> = arrayListOf(),
    @SerializedName("curriculum_tags") var curriculumTags: ArrayList<String> = arrayListOf(),
    @SerializedName("educator") var educator: String? = null,
    @SerializedName("owned") var owned: Int? = null,
    @SerializedName("sale") var sale: Int? = null,
    @SerializedName("watched") var watched: Int? = null,
) : Parcelable

@Parcelize
data class Smart(
    @SerializedName("id") var id: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("smart") var smart: String? = null,
    @SerializedName("courses") var courses: ArrayList<Int> = arrayListOf(),
    @SerializedName("is_default") var isDefault: Int? = null,
    @SerializedName("is_archive") var isArchive: Int? = null,
    @SerializedName("description") var description: String? = null
) : Parcelable

@Parcelize
data class User(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("is_default") var isDefault: Int? = null,
    @SerializedName("is_archive") var isArchive: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("courses") var courses: ArrayList<Int> = arrayListOf()
) : Parcelable

