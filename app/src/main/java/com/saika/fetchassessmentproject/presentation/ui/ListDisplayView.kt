package com.saika.fetchassessmentproject.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.saika.fetchassessmentproject.R
import com.saika.fetchassessmentproject.data.model.UserDto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListDisplayView(userList: List<UserDto>) {

    var sortingOption by remember {
        mutableStateOf("Sort by List ID and Name")
    }

    val sortingOptions = listOf(
        "Sort by List ID & Name",
        "Group by List ID"
    )

    val filteredList = userList
        .filter { !it.name.isNullOrBlank() }

    val sortedList = when (sortingOptions) {
        listOf("Sort by List ID & Name") -> filteredList.sortedWith(compareBy<UserDto> { it.listId }.thenBy { it.name })
        listOf("Group by List ID") -> filteredList.sortedBy { it.listId }
        else -> filteredList
    }
    val listState = rememberLazyListState()

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                modifier = Modifier.shadow(10.dp)
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null
                        )
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            }) {
                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
            }
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {
            SortingDropDown(
                selectedOption = sortingOption,
                options = sortingOptions,
                optionsSelected = { sortingOption = it }
            )

            LazyColumn(
                contentPadding = paddingValues,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(16.dp),
                state = listState
            ) {
                if (sortingOption == "Group by List ID") {
                    sortedList
                        .groupBy { it.listId }
                        .toSortedMap()
                        .forEach { (listId, items) ->
                            item {
                                Text(
                                    text = "List ID: $listId",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                            items(items) { user ->
                                CardView(user = user)
                            }
                        }
                } else {

                    items(sortedList) { user ->
                        CardView(user = user)
                    }
                }

            }
        }
    }
}

@Composable
fun CardView(user: UserDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Id ${user.listId} : ${user.name.toString()}",
            fontSize = 16.sp,
            modifier = Modifier.padding(all = 16.dp)
        )
    }
}

@Composable
fun SortingDropDown(
    selectedOption: String,
    options: List<String>,
    optionsSelected: (String) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = selectedOption)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(text = option) }, onClick = {
                    optionsSelected(option)
                    expanded = false
                })
            }
        }
    }
}