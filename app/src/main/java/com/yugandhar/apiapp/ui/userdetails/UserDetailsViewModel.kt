package com.yugandhar.apiapp.ui.main.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yugandhar.apiapp.data.model.User
import com.yugandhar.apiapp.data.repository.UserRepository
import com.yugandhar.apiapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserDetailsViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _user = MutableStateFlow<Resource<User>>(Resource.Loading)
    val user: StateFlow<Resource<User>> = _user

    fun fetchUser(userId: Int) {
        repository.getUserById(userId).onEach { resource ->
            _user.value = resource
        }.launchIn(viewModelScope)
    }
}