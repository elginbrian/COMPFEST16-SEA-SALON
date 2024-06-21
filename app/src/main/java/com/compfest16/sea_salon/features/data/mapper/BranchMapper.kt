package com.compfest16.sea_salon.features.data.mapper

import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot

fun BranchModel.toHashMap(): HashMap<String, Any>{
    var map = HashMap<String, Any>()
    map = hashMapOf(
        "branch_id" to this.branchID,
        "branch_name" to this.branchName,
        "branch_address" to this.branchAddress,
        "branch_coordinate" to this.branchCoordinates,
        "opening_hours" to this.openingHours,
        "closing_hours" to this.closingHours
    )
    return map
}

fun DocumentSnapshot.toBranchModel(): BranchModel {
    val branchID = getString("branch_id") ?: ""
    val branchName = getString("branch_name") ?: ""
    val branchAddress = getString("branch_address") ?: ""
    val coordinates = get("branch_coordinate") as? Map<*, *>
    val latitude = coordinates?.get("first") as? Double ?: 0.0
    val longitude = coordinates?.get("second") as? Double ?: 0.0
    val branchCoordinates = Pair(latitude, longitude)
    val openingHours = getString("opening_hours") ?: ""
    val closingHours = getString("closing_hours") ?: ""

    return BranchModel(
        branchID = branchID,
        branchName = branchName,
        branchAddress = branchAddress,
        branchCoordinates = branchCoordinates,
        openingHours = openingHours,
        closingHours = closingHours
    )
}
