package com.compfest16.sea_salon.features.domain.dummy

import com.compfest16.sea_salon.features.domain.model.BranchModel

object BranchDummy {
    var notFound = BranchModel(
        branchID = "notFound",
        branchName = "Not Found",
        branchAddress = "-",
        branchCoordinates = Pair(0.0, 0.0),
        openingHours = "00:00",
        closingHours = "00:00"
    )
    var malang = BranchModel(
        branchID = "dummy11",
        branchName = "Malang Veteran",
        branchAddress = "Jl. Veteran No. 100",
        branchCoordinates = Pair(-7.954181, 112.6146475),
        openingHours = "08:00",
        closingHours = "20:00"
    )

    var surabaya = BranchModel(
        branchID = "dummy12",
        branchName = "Surabaya Tunjungan",
        branchAddress = "Jl. Tunjungan No. 101",
        branchCoordinates = Pair(-7.257472, 112.752088),
        openingHours = "09:00",
        closingHours = "21:00"
    )

    var jakarta = BranchModel(
        branchID = "dummy13",
        branchName = "Jakarta Sudirman",
        branchAddress = "Jl. Jend. Sudirman No. 102",
        branchCoordinates = Pair(-6.21462, 106.84513),
        openingHours = "09:00",
        closingHours = "22:00"
    )

    var bandung = BranchModel(
        branchID = "dummy14",
        branchName = "Bandung Braga",
        branchAddress = "Jl. Braga No. 103",
        branchCoordinates = Pair(-6.917464, 107.619123),
        openingHours = "08:00",
        closingHours = "20:00"
    )

    var yogyakarta = BranchModel(
        branchID = "dummy15",
        branchName = "Yogyakarta Malioboro",
        branchAddress = "Jl. Malioboro No. 104",
        branchCoordinates = Pair(-7.79558, 110.36949),
        openingHours = "08:00",
        closingHours = "21:00"
    )

    var list = listOf(malang, surabaya, jakarta, bandung, yogyakarta)
}