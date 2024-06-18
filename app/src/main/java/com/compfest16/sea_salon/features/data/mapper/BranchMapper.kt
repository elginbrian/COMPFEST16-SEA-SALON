package com.compfest16.sea_salon.features.data.mapper

import com.compfest16.sea_salon.features.domain.model.BranchModel
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

fun QueryDocumentSnapshot.toBranchModel(): BranchModel{
    return BranchModel(
        branchID = this["branch_id"] as String,
        branchName = this["branch_name"] as String,
        branchAddress = this["branch_address"] as String,
        branchCoordinates = this["branch_coordinate"] as Pair<Double, Double>,
        openingHours = this["opening_hours"] as String,
        closingHours = this["closing_hours"] as String
    )
}