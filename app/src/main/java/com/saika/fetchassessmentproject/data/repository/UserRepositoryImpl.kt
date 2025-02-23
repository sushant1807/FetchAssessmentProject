package com.saika.fetchassessmentproject.data.repository

import com.saika.fetchassessmentproject.data.model.UserDto
import com.saika.fetchassessmentproject.data.remote.ApiService
import com.saika.fetchassessmentproject.domain.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiService: ApiService): UserRepository {
    override suspend fun getUsersInfo(): List<UserDto> {
        return apiService.getUsers()
    }
}