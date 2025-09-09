package com.example.mvvm_with_retrofit_dagger_2_and__coroutines

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.databinding.FragmentLoginBinding
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.models.UserRequest
import com.example.mvvm_with_retrofit_dagger_2_and__coroutines.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first) {
                authViewModel.loginUser(getUserRequest())
            } else {
                binding.txtError.text = validationResult.second
            }
            bindObserver()
        }


        binding.btnSignUp.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val userRequest = getUserRequest()
        return authViewModel.validateCredentials(
            userRequest.userName,
            userRequest.email,
            userRequest.password, true
        )
    }

    private fun getUserRequest(): UserRequest {
        val emailAddress = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        return UserRequest(emailAddress, password, "")

    }

    private fun bindObserver() {
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)

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