package com.compfest16.sea_salon.features.domain.dummy

import android.net.Uri
import com.compfest16.sea_salon.features.domain.model.ImageModel

object ImageDummy {
    var notFound = ImageModel(
        imageID = "notFound",
        affiliateID = "notFound",
        src = Uri.EMPTY,
        alt = "Image not found",
        role = 0
    )
}