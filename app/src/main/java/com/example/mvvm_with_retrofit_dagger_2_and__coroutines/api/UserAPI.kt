package com.example.mvvm_with_retrofit_dagger_2_and__coroutines.api

import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.UserRequest
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//all end points
interface UserAPI {

    @POST("users/signup")
    suspend fun signup(@Body userRequest:UserRequest) : Response<UserResponse>

    @POST("users/signin")
    suspend fun signin(@Body userRequest:UserRequest) : Response<UserResponse>

}