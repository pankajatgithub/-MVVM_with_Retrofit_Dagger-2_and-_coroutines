package com.example.mvvm_with_retrofit_dagger_2_and__coroutines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.databinding.FragmentRegisterBinding
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.UserRequest
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    //In fragment we have kotlin extension ,
    // by the help of this we make viewmodel object, we don't use viewmodel provider, behind the scene same is called
    private val authViewModel by viewModels<AuthViewModel>() //here we are using by viewmodel delegation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnLogin.setOnClickListener {
//            authViewModel.loginUser(UserRequest("test5@gmail.com","12345","test5"))
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnSignUp.setOnClickListener {
            val validateuserInput = validateuserInput()
            if (validateuserInput.first) {
                authViewModel.registerUser(getUserRequest())
            } else {
                binding.txtError.text = validateuserInput.second
            }


//            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)

        }

        //in fragment ,viewLifecycleOwner is the life cycle owner of fragment
        bindObserver()
    }

    private fun getUserRequest(): UserRequest {
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        val username = binding.txtUsername.text.toString()
        return UserRequest(emailAddress, password, username)

    }

    private fun validateuserInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials(
            userRequest.userName,
            userRequest.email,
            userRequest.password,
            false
        )

    }


    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    //add token handling
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)

                }

                is NetworkResult.Error -> {
                    binding.txtError.text = it.message
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}