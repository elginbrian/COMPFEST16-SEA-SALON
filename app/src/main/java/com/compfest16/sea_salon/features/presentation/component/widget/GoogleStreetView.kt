package com.compfest16.sea_salon.features.presentation.component.widget

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.google.android.gms.maps.StreetViewPanoramaOptions
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.streetview.StreetView
import com.google.maps.android.ktx.MapsExperimentalFeature

@Composable
@OptIn(MapsExperimentalFeature::class)
fun GoogleStreetView(selectedCoordinates: MutableState<BranchModel>) {
    val context = LocalContext.current
    val showStreetView = remember { mutableStateOf(true) }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Sorry, Streetview is currently\nunavailable \uD83D\uDE14", color = CompfestWhite, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
        if (showStreetView.value){
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
    }
}