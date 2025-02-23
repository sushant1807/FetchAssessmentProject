package com.saika.fetchassessmentproject.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String? = "Unknown",
    @SerializedName("listId") val listId: Int,
)