package com.compfest16.sea_salon.features.domain.dummy

import com.compfest16.sea_salon.features.domain.model.ReservationModel

object ReservationDummy {
    var notFound = ReservationModel(
        reservationID = "notFound",
        branchID = "notFound",
        userID = "notFound",
        date = "2000-01-01",
        reservationType = 1
    )
    var bagasMalang = ReservationModel(
        reservationID = "dummy41",
        branchID = "dummy11",
        userID = "dummy1",
        date = "2024-01-01",
        reservationType = 1
    )

    var aryaSurabaya = ReservationModel(
        reservationID = "dummy42",
        branchID = "dummy12",
        userID = "dummy2",
        date = "2024-01-02",
        reservationType = 2
    )

    var dewiJakarta = ReservationModel(
        reservationID = "dummy43",
        branchID = "dummy13",
        userID = "dummy3",
        date = "2024-01-03",
        reservationType = 3
    )

    var ramaBandung = ReservationModel(
        reservationID = "dummy44",
        branchID = "dummy14",
        userID = "dummy4",
        date = "2024-01-04",
        reservationType = 1
    )

    var sintaYogyakarta = ReservationModel(
        reservationID = "dummy45",
        branchID = "dummy15",
        userID = "dummy5",
        date = "2024-01-05",
        reservationType = 2
    )

    var bagasSurabaya = ReservationModel(
        reservationID = "dummy46",
        branchID = "dummy12",
        userID = "dummy1",
        date = "2024-01-06",
        reservationType = 3
    )

    var aryaJakarta = ReservationModel(
        reservationID = "dummy47",
        branchID = "dummy13",
        userID = "dummy2",
        date = "2024-01-07",
        reservationType = 1
    )

    var dewiBandung = ReservationModel(
        reservationID = "dummy48",
        branchID = "dummy14",
        userID = "dummy3",
        date = "2024-01-08",
        reservationType = 2
    )

    var ramaYogyakarta = ReservationModel(
        reservationID = "dummy49",
        branchID = "dummy15",
        userID = "dummy4",
        date = "2024-01-09",
        reservationType = 3
    )

    var sintaMalang = ReservationModel(
        reservationID = "dummy50",
        branchID = "dummy11",
        userID = "dummy5",
        date = "2024-01-10",
        reservationType = 1
    )

    var bagasJakarta = ReservationModel(
        reservationID = "dummy51",
        branchID = "dummy13",
        userID = "dummy1",
        date = "2024-01-11",
        reservationType = 2
    )

    var aryaBandung = ReservationModel(
        reservationID = "dummy52",
        branchID = "dummy14",
        userID = "dummy2",
        date = "2024-01-12",
        reservationType = 3
    )

    var dewiYogyakarta = ReservationModel(
        reservationID = "dummy53",
        branchID = "dummy15",
        userID = "dummy3",
        date = "2024-01-13",
        reservationType = 1
    )

    var ramaMalang = ReservationModel(
        reservationID = "dummy54",
        branchID = "dummy11",
        userID = "dummy4",
        date = "2024-01-14",
        reservationType = 2
    )

    var sintaSurabaya = ReservationModel(
        reservationID = "dummy55",
        branchID = "dummy12",
        userID = "dummy5",
        date = "2024-01-15",
        reservationType = 3
    )

    var list = listOf(
        bagasMalang, aryaSurabaya, dewiJakarta, ramaBandung, sintaYogyakarta,
        bagasSurabaya, aryaJakarta, dewiBandung, ramaYogyakarta, sintaMalang,
        bagasJakarta, aryaBandung, dewiYogyakarta, ramaMalang, sintaSurabaya
    )
}
