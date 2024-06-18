package com.compfest16.sea_salon.features.domain.dummy

import com.compfest16.sea_salon.features.domain.model.ReviewModel

object ReviewDummy {
    var notFound = ReviewModel(
        reviewID = "notFound",
        userID = "notFound",
        branchID = "notFound",
        star = 0,
        comment = "not found"
    )
    var bagasMalang = ReviewModel(
        reviewID = "dummy21",
        userID = "dummy1",
        branchID = "dummy11",
        star = 4,
        comment = "bagus banget"
    )

    var aryaSurabaya = ReviewModel(
        reviewID = "dummy22",
        userID = "dummy2",
        branchID = "dummy12",
        star = 5,
        comment = "pelayanan memuaskan"
    )

    var dewiJakarta = ReviewModel(
        reviewID = "dummy23",
        userID = "dummy3",
        branchID = "dummy13",
        star = 3,
        comment = "cukup baik, tapi bisa ditingkatkan"
    )

    var ramaBandung = ReviewModel(
        reviewID = "dummy24",
        userID = "dummy4",
        branchID = "dummy14",
        star = 4,
        comment = "nyaman dan bersih"
    )

    var sintaYogyakarta = ReviewModel(
        reviewID = "dummy25",
        userID = "dummy5",
        branchID = "dummy15",
        star = 5,
        comment = "tempat yang sangat bagus"
    )

    var bagasSurabaya = ReviewModel(
        reviewID = "dummy26",
        userID = "dummy1",
        branchID = "dummy12",
        star = 2,
        comment = "tidak sesuai ekspektasi"
    )

    var aryaJakarta = ReviewModel(
        reviewID = "dummy27",
        userID = "dummy2",
        branchID = "dummy13",
        star = 4,
        comment = "bagus tapi parkirnya susah"
    )

    var dewiBandung = ReviewModel(
        reviewID = "dummy28",
        userID = "dummy3",
        branchID = "dummy14",
        star = 5,
        comment = "sangat nyaman dan pelayanan ramah"
    )

    var ramaYogyakarta = ReviewModel(
        reviewID = "dummy29",
        userID = "dummy4",
        branchID = "dummy15",
        star = 3,
        comment = "lumayan tapi agak ramai"
    )

    var sintaMalang = ReviewModel(
        reviewID = "dummy30",
        userID = "dummy5",
        branchID = "dummy11",
        star = 4,
        comment = "bagus, tempatnya nyaman"
    )

    var bagasJakarta = ReviewModel(
        reviewID = "dummy31",
        userID = "dummy1",
        branchID = "dummy13",
        star = 3,
        comment = "biasa saja"
    )

    var aryaBandung = ReviewModel(
        reviewID = "dummy32",
        userID = "dummy2",
        branchID = "dummy14",
        star = 5,
        comment = "sangat puas dengan pelayanannya"
    )

    var dewiYogyakarta = ReviewModel(
        reviewID = "dummy33",
        userID = "dummy3",
        branchID = "dummy15",
        star = 4,
        comment = "baik, tapi bisa lebih baik lagi"
    )

    var ramaMalang = ReviewModel(
        reviewID = "dummy34",
        userID = "dummy4",
        branchID = "dummy11",
        star = 2,
        comment = "tidak begitu puas"
    )

    var sintaSurabaya = ReviewModel(
        reviewID = "dummy35",
        userID = "dummy5",
        branchID = "dummy12",
        star = 5,
        comment = "sempurna!"
    )

    var list = listOf(
        bagasMalang, aryaSurabaya, dewiJakarta, ramaBandung,
        sintaYogyakarta, bagasSurabaya, aryaJakarta, dewiBandung, ramaYogyakarta, sintaMalang,
        bagasJakarta, aryaBandung, dewiYogyakarta, ramaMalang, sintaSurabaya
    )
}