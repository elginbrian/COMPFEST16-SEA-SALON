package com.compfest16.sea_salon.features.domain.model

import java.util.UUID

data class UserModel(
    val userID: String = UUID.randomUUID().toString(),
    val fullName: String,
    val email: String,
    val phoneNum: String,
    val password: String,
    val isCustomer: Boolean = true
)
