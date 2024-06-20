package com.compfest16.sea_salon.features.presentation.screen.auth_section

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    val userRepository: UserRepository,
    val imageRepository: ImageRepository
) : ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun signUp(
        user: UserModel,
        image: ImageModel,
        onMessageReceived: (String) -> Unit
    ) {
        _authState.update {
            it.copy(message = mutableListOf())
        }

        viewModelScope.launch {
            userRepository.SignUpUser(user, image).collect{ result ->
                Log.d("AuthViewModel", "SignUpUser Result: $result")
                onMessageReceived(result)
                _authState.update {
                    it.copy(it.message.add(result))
                }

                viewModelScope.launch {
                    userRepository.PostUser(user).collect { postResult ->
                        Log.d("AuthViewModel", "PostUser Result: $postResult")
                        onMessageReceived(postResult)
                        _authState.update {
                            it.copy(it.message.add(postResult))
                        }
                    }
                }

                viewModelScope.launch {
                    imageRepository.PostImage(image).collect { imageResult ->
                        Log.d("AuthViewModel", "PostImage Result: $imageResult")
                        onMessageReceived(imageResult)
                        _authState.update {
                            it.copy(it.message.add(imageResult))
                        }
                    }
                }
            }
        }

        _authState.update {
            it.copy(isLoading = false)
        }
    }

    fun login(
        email: String,
        password: String,
        onMessageReceived: (String) -> Unit
    ){
        _authState.update {
            it.copy(message = mutableListOf())
        }

        viewModelScope.launch {
            userRepository.LoginUser(email, password).collect {result ->
                Log.d("AuthViewModel", "LoginUser Result: $result")
                onMessageReceived(result)
                _authState.update {
                    it.copy(it.message.add(result))
                }
            }
        }

        _authState.update {
            it.copy(isLoading = false)
        }
    }
}
