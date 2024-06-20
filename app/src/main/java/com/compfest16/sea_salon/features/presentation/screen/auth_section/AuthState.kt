package com.compfest16.sea_salon.features.presentation.screen.auth_section

data class AuthState(
    val isLoading: Boolean = true,
    val message: MutableList<String> = mutableListOf()
)