package com.yugandhar.apiapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yugandhar.apiapp.data.model.User
import com.yugandhar.apiapp.data.repository.UserRepository
import com.yugandhar.apiapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _users = MutableStateFlow<Resource<List<User>>>(Resource.Loading)
    val users: StateFlow<Resource<List<User>>> = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        repository.getUsers().onEach { resource: Resource<List<User>> ->
            _users.value = resource
        }.launchIn(viewModelScope)
    }
}