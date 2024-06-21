package com.compfest16.sea_salon.features.domain.dummy

import com.compfest16.sea_salon.features.domain.model.BranchModel
import java.util.UUID

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

    val jakartaBranches = mutableListOf(
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta UI Depok",
            branchAddress = "Jl. Margonda Raya No. 100",
            branchCoordinates = Pair(-6.362938, 106.824389),
            openingHours = "08:00",
            closingHours = "20:00"
        ),
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta Pondok Labu",
            branchAddress = "Jl. Pondok Labu Raya No. 45",
            branchCoordinates = Pair(-6.308496, 106.772046),
            openingHours = "09:00",
            closingHours = "21:00"
        ),
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta Fatmawati",
            branchAddress = "Jl. RS Fatmawati No. 15",
            branchCoordinates = Pair(-6.292076, 106.797813),
            openingHours = "08:30",
            closingHours = "20:30"
        ),
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta Ragunan",
            branchAddress = "Jl. Harsono RM No. 9",
            branchCoordinates = Pair(-6.307144, 106.800401),
            openingHours = "09:30",
            closingHours = "21:30"
        ),
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta Lebak Bulus",
            branchAddress = "Jl. Lebak Bulus No. 25",
            branchCoordinates = Pair(-6.289953, 106.781295),
            openingHours = "10:00",
            closingHours = "22:00"
        ),
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta Kemang",
            branchAddress = "Jl. Kemang Raya No. 30",
            branchCoordinates = Pair(-6.266589, 106.814151),
            openingHours = "09:00",
            closingHours = "21:00"
        ),
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta Senayan",
            branchAddress = "Jl. Asia Afrika No. 8",
            branchCoordinates = Pair(-6.226983, 106.798581),
            openingHours = "08:30",
            closingHours = "20:30"
        ),
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta Sudirman",
            branchAddress = "Jl. Jenderal Sudirman No. 50",
            branchCoordinates = Pair(-6.208763, 106.821559),
            openingHours = "09:30",
            closingHours = "21:30"
        ),
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta Thamrin",
            branchAddress = "Jl. MH Thamrin No. 10",
            branchCoordinates = Pair(-6.196922, 106.822030),
            openingHours = "10:00",
            closingHours = "22:00"
        ),
        BranchModel(
            branchID = UUID.randomUUID().toString(),
            branchName = "Jakarta Menteng",
            branchAddress = "Jl. Cikini Raya No. 78",
            branchCoordinates = Pair(-6.192191, 106.836002),
            openingHours = "08:00",
            closingHours = "20:00"
        )
    )
}