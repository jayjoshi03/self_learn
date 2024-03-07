package com.example.mycourse.module

import com.example.mycourse.api.ApiClient
import com.example.mycourse.repository.CourseRepository
import com.example.mycourse.repository.CourseRepositoryImpl
import com.example.mycourse.utilities.Default
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("Normal")
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Default.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    }

    @Provides
    @Singleton
    @Named("NormalApi")
    fun providesApi(@Named("Normal") retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    @Provides
    @Singleton
    fun providesCourseRepository(@Named("NormalApi") api: ApiClient): CourseRepository =
        CourseRepositoryImpl(api)

}