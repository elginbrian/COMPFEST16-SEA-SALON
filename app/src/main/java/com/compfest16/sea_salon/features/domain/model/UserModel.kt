package com.compfest16.sea_salon.features.domain.model

import com.compfest16.sea_salon.features.domain.dummy.ImageDummy
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import java.util.UUID

data class UserModel(
    var userID: String = UUID.randomUUID().toString(),
    var fullName: String,
    var email: String,
    var phoneNum: String,
    var password: String,
    val isCustomer: Boolean = true
){
    suspend fun getProfilePicture(
        repository: ImageRepository,
        onSuccess: (ImageModel) -> Unit
    ){
        repository.GetImageById(userID, "user_profile_picture").collect{
            onSuccess(it.firstOrNull() ?: ImageDummy.notFound)
        }
    }

    suspend fun getBanner(
        repository: ImageRepository,
        onSuccess: (ImageModel) -> Unit
    ){
        repository.GetImageById(userID, "user_banner").collect{
            onSuccess(it.firstOrNull() ?: ImageDummy.notFound)
        }
    }
}
