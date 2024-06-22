package com.compfest16.sea_salon.features.presentation.component.widget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.google.android.gms.maps.StreetViewPanoramaOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.streetview.StreetView
import com.google.maps.android.ktx.MapsExperimentalFeature

@Composable
@OptIn(MapsExperimentalFeature::class)
fun GoogleStreetView(selectedCoordinates: MutableState<BranchModel>) {
    StreetView(
        modifier = Modifier.fillMaxSize(),
        streetViewPanoramaOptionsFactory = {
            StreetViewPanoramaOptions().position(
                LatLng(
                    selectedCoordinates.value.branchCoordinates.first,
                    selectedCoordinates.value.branchCoordinates.second
                )
            )
        },
        isPanningGesturesEnabled = true,
        isStreetNamesEnabled = true,
        isUserNavigationEnabled = true,
        isZoomGesturesEnabled = true
    )
}