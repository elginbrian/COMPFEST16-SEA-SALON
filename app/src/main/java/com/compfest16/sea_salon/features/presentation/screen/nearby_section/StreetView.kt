package com.compfest16.sea_salon.features.presentation.screen.nearby_section

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.google.android.gms.maps.StreetViewPanoramaOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.streetview.StreetView
import com.google.maps.android.ktx.MapsExperimentalFeature
import org.koin.androidx.compose.getViewModel

@OptIn(MapsExperimentalFeature::class)
@Composable
fun StreetView() {
    val viewModel = getViewModel<NearbyViewModel>()
    val coordinates = viewModel.selectedCoordinates.collectAsState().value

    StreetView(
        streetViewPanoramaOptionsFactory = {
            StreetViewPanoramaOptions().position(LatLng(
                BranchDummy.malang.branchCoordinates.first,
                BranchDummy.malang.branchCoordinates.second
            ))
        },
        isPanningGesturesEnabled = true,
        isStreetNamesEnabled = true,
        isUserNavigationEnabled = true,
        isZoomGesturesEnabled = true
    )
}