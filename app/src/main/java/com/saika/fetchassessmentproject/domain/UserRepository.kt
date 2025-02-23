package com.saika.fetchassessmentproject.domain

import com.saika.fetchassessmentproject.data.model.UserDto

interface UserRepository {

    suspend fun getUsersInfo(): List<UserDto>
}