package com.compfest16.sea_salon.features.domain.model

import android.net.Uri
import java.util.UUID

data class ImageModel(
    val imageID: String = UUID.randomUUID().toString(),
    val affiliateID: String,
    val src: Uri,
    val alt: String = "",
    val role: Int
){
    fun getRole(): String{
        return when(role){
            1 -> "user_profile_picture"
            2 -> "user_banner"
            3 -> "review_image"
            4 -> "reservation_image"
            5 -> "branch_image"
            else -> "none"
        }
    }
}
