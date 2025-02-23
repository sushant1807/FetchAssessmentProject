package com.saika.fetchassessmentproject.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.saika.fetchassessmentproject.presentation.viewmodel.UserViewModel

@Composable
fun UsersView(viewModel: UserViewModel = hiltViewModel()) {
    val userList by viewModel.users.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    ListDisplayView(userList)
}