package com.saika.fetchassessmentproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saika.fetchassessmentproject.data.model.UserDto
import com.saika.fetchassessmentproject.domain.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private val userList = MutableStateFlow<List<UserDto>>(emptyList())
    val users: StateFlow<List<UserDto>> = userList

    fun fetchUsers() {
        viewModelScope.launch {
            userList.value = userRepository.getUsersInfo()
        }
    }
}