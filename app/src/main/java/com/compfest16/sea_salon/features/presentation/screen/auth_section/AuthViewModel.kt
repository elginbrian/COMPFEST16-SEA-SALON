package com.compfest16.sea_salon.features.presentation.screen.auth_section

import androidx.lifecycle.ViewModel
import com.compfest16.sea_salon.features.domain.repository.UserRepository

class AuthViewModel(
    val userRepository: UserRepository
): ViewModel() {

}