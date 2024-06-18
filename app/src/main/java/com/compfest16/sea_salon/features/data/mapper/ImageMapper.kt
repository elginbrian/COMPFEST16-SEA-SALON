package com.compfest16.sea_salon.features.data.mapper

fun String.toImageRole(): Int {
    return when (this){
        "user_profile_picture" -> 1
        "user_banner" -> 2
        "review_image" -> 3
        "reservation_image" -> 4
        "branch_image" -> 5
        else -> 0
    }
}