package com.compfest16.sea_salon.features.data.mapper

import com.compfest16.sea_salon.features.domain.model.UserModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

fun UserModel.toHashMap(): HashMap<String, Any>{
    var map = HashMap<String, Any>()
    map = hashMapOf(
        "user_id" to this.userID,
        "full_name" to this.fullName,
        "email" to this.email,
        "phone_num" to this.phoneNum,
        "password" to this.password,
        "is_customer" to this.isCustomer
    )
    return map
}

fun QueryDocumentSnapshot.toUserModel(): UserModel{
    return UserModel(
        userID = this["user_id"] as String,
        fullName = this["full_name"] as String,
        email = this["email"] as String,
        phoneNum = this["phone_num"] as String,
        password = this["password"] as String,
        isCustomer = this["is_customer"] as Boolean
    )
}

fun DocumentSnapshot.toUserModel(): UserModel {
    return UserModel(
        userID = this.getString("user_id") ?: "",
        fullName = this.getString("full_name") ?: "",
        email = this.getString("email") ?: "",
        phoneNum = this.getString("phone_num") ?: "",
        password = this.getString("password") ?: "",
        isCustomer = this.getBoolean("is_customer") ?: false
    )
}

