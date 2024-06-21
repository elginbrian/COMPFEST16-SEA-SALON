package com.compfest16.sea_salon.features.presentation.screen.nearby_section

import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel

fun findClosestBranch(currentLat: Double, currentLon: Double, branches: List<BranchModel>): Pair<BranchModel, Double> {
    var closestBranch: BranchModel = BranchDummy.malang
    var shortestDistance = Double.MAX_VALUE

    for (branch in branches) {
        val distance = distanceInKm(currentLat, currentLon, branch.branchCoordinates.first, branch.branchCoordinates.second)
        if (distance < shortestDistance) {
            shortestDistance = distance
            closestBranch = branch
        }
    }

    return Pair(closestBranch, shortestDistance)
}

fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val radiusOfEarth = 6371 // Earth's radius in kilometers
    val latDistance = Math.toRadians(lat2 - lat1)
    val lonDistance = Math.toRadians(lon2 - lon1)
    val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    return radiusOfEarth * c // Distance in kilometers
}