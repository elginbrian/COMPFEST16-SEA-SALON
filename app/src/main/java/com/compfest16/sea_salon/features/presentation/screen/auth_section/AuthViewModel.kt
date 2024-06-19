package com.compfest16.sea_salon.features.presentation.screen.auth_section

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    val userRepository: UserRepository,
    val imageRepository: ImageRepository
): ViewModel() {
    fun signUp(
        user: UserModel,
        image: ImageModel,
        onFinished: (MutableList<String>) -> Unit
    ){
        val message = mutableListOf<String>()
        viewModelScope.launch {
            userRepository.SignUpUser(user, image).collect{
                message.add(it)
            }
            userRepository.PostUser(user).collect{
                message.add(it)
            }
            imageRepository.PostImage(image).collect{
                message.add(it)
            }
            Log.d("AuthViewModel",message.toString())
            onFinished(message)
        }
    }
}