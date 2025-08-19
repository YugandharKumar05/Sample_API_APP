package com.yugandhar.apiapp.data.repository

import com.yugandhar.apiapp.data.model.User
import com.yugandhar.apiapp.data.remote.ApiService
import com.yugandhar.apiapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {
    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun getUsers(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading)
        try {
            val users = apiService.getUsers()
            emit(Resource.Success(users))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

    fun getUserById(userId: Int): Flow<Resource<User>> = flow {
        emit(Resource.Loading)
        try {
            val user = apiService.getUserById(userId)
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }
}