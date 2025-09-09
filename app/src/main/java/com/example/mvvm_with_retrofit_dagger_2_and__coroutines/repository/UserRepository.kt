package com.example.mvvm_with_retrofit_dagger_2_and__coroutines.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.api.UserAPI
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.UserRequest
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.UserResponse
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.Constants.TAG
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.NetworkResult
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {
    //mutable live data are not publically accessible and data can be changed in to it, Live data are read only
    //and publically accessible and data can't be changed from outside
    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData

    @SuppressLint("SuspiciousIndentation")
    suspend fun registerUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.signup(userRequest)
        handleResponse(response)
    }


    suspend fun loginUser(userRequest: UserRequest) {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.signin(userRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {

            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))

        } else if (response.errorBody() != null) {

            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))

        } else {
            _userResponseLiveData.postValue(
                NetworkResult.Error(
                    "Something went wrong"
                )!!
            )
        }
    }


}