package com.compfest16.sea_salon.features.data.mapper

import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

fun ReservationModel.toHashMap(): HashMap<String, Any>{
    var map = HashMap<String, Any>()
    map = hashMapOf(
        "reservation_id" to this.reservationID,
        "branch_id" to this.branchID,
        "user_id" to this.userID,
        "reservation_type" to this.reservationType,
        "date" to this.date
    )
    return map
}

fun QueryDocumentSnapshot.toReservationModel(): ReservationModel{
    return ReservationModel(
        reservationID = this["reservation_id"] as String,
        branchID = this["branch_id"] as String,
        userID = this["user_id"] as String,
        reservationType = this["reservation_type"] as Int,
        date = this["date"] as String
    )
}

fun DocumentSnapshot.toReservationModel(): ReservationModel {
    return ReservationModel(
        reservationID = this.getString("reservation_id") ?: "",
        branchID = this.getString("branch_id") ?: "",
        userID = this.getString("user_id") ?: "",
        reservationType = this.getLong("reservation_type")?.toInt() ?: 0,
        date = this.getString("date") ?: ""
    )
}