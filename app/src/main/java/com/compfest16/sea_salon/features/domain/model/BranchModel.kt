package com.compfest16.sea_salon.features.domain.model

import java.util.UUID

data class BranchModel(
    val branchID: String = UUID.randomUUID().toString(),
    var branchName: String,
    val branchAddress: String,
    val branchCoordinates: Pair<Double, Double>,
    val openingHours: String,
    val closingHours: String
)
