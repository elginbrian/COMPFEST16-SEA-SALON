package com.compfest16.sea_salon.features.presentation.component.widget

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline

@Composable
fun GoogleMapsDark(
    cameraPositionState: CameraPositionState,
    properties: MutableState<MapProperties>,
    uiSettings: MutableState<MapUiSettings>,
    currentLocation: MutableState<Pair<Double, Double>>,
    closestBranch: MutableState<Pair<BranchModel, Double>>,
    branchList: MutableState<List<BranchModel>>,
    selectedCoordinates: MutableState<BranchModel>,
    isStreetView: MutableState<Boolean>,
    context: Context
) {
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties.value,
        uiSettings = uiSettings.value,
    ) {
        MarkerInfoWindow(
            state = MarkerState(
                position = LatLng(
                    currentLocation.value.first,
                    currentLocation.value.second
                )
            ),
            icon = BitmapDescriptorFactory.fromResource(R.drawable.mylocation)
        ) {
            Column {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .border(
                            BorderStroke(1.dp, CompfestBlueGrey),
                            RoundedCornerShape(24.dp)
                        )
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    CompfestPink,
                                    CompfestAqua
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Text("Your Location", fontWeight = FontWeight.Bold, color = Color.White)
                    Text(
                        "Closes Branch: ${closestBranch.value.first.branchName}",
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        branchList.value.forEach { branch ->
            Marker(
                state = MarkerState(
                    position = LatLng(
                        branch.branchCoordinates.first,
                        branch.branchCoordinates.second
                    )
                ),
                title = branch.branchName,
                snippet = branch.branchAddress,
                onInfoWindowClick = {
                    selectedCoordinates.value = branch
                    isStreetView.value = true
                },
                onClick = {
                    selectedCoordinates.value = branch
                    false
                }
            )
        }

        Polyline(
            points = listOf(
                LatLng(currentLocation.value.first, currentLocation.value.second),
                LatLng(
                    closestBranch.value.first.branchCoordinates.first,
                    closestBranch.value.first.branchCoordinates.second
                )
            ),
            clickable = true,
            color = CompfestAqua,
            width = 5f
        )
    }
}