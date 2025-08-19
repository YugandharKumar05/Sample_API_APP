package com.yugandhar.apiapp.data.remote

import com.yugandhar.apiapp.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): User
}