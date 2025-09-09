package com.example.mvvm_with_retrofit_dagger_2_and__coroutines

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.UserRequest
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.UserResponse
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.repository.UserRepository
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    fun registerUser(userRequest: UserRequest) {

        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }

    }

    fun loginUser(userRequest: UserRequest) {

        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }

    }

    //pair class in kotlin is used to return multiple values from a function
    fun validateCredentials(
        username: String,
        emailAddress: String,
        password: String,
        isLogin:Boolean
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if ((!isLogin && TextUtils.isEmpty(username)) || TextUtils.isEmpty(emailAddress) || TextUtils.isEmpty(
                password
            )
        ) {
            result = Pair(false, "Please provide the credentials")
        }
        //Android has Patterns class which has a EMAIL_ADDRESS, PHONE_NUMBER, URL, and DATE pattern to match
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            result = Pair(false, "The Email Address is invalid")
        } else if (password.length <= 4) {
            result = Pair(false, "Password length should be greater than 4")
        }
        return result

    }
}