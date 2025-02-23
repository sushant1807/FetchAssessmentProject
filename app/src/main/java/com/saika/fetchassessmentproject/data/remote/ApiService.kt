package com.saika.fetchassessmentproject.data.remote

import com.saika.fetchassessmentproject.data.model.UserDto
import retrofit2.http.GET

interface ApiService {

    @GET("hiring.json")
    suspend fun getUsers(): List<UserDto>
}