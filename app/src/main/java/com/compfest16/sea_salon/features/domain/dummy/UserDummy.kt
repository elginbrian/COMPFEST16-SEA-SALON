package com.compfest16.sea_salon.features.domain.dummy

import com.compfest16.sea_salon.features.domain.model.UserModel

object UserDummy {
    var bagas = UserModel(
        userID = "dummy1",
        fullName = "Bagas Raditya",
        email = "bagas@gmail.com",
        phoneNum = "081234567890",
        password = "bagas123"
    )

    var arya = UserModel(
        userID = "dummy2",
        fullName = "Arya Wijaya",
        email = "arya@gmail.com",
        phoneNum = "081234567891",
        password = "arya123"
    )

    var dewi = UserModel(
        userID = "dummy3",
        fullName = "Dewi Anggraini",
        email = "dewi@gmail.com",
        phoneNum = "081234567892",
        password = "dewi123"
    )

    var rama = UserModel(
        userID = "dummy4",
        fullName = "Rama Santoso",
        email = "rama@gmail.com",
        phoneNum = "081234567893",
        password = "rama123"
    )

    var sinta = UserModel(
        userID = "dummy5",
        fullName = "Sinta Dewanti",
        email = "sinta@gmail.com",
        phoneNum = "081234567894",
        password = "sinta123"
    )

    var list = listOf(bagas, arya, dewi, rama, sinta)
}