package com.compfest16.sea_salon.features.presentation.screen.nearby_section

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.widget.BranchCard
import com.compfest16.sea_salon.features.presentation.component.widget.TopBar
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav
import com.google.android.gms.maps.StreetViewPanoramaOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.ktx.MapsExperimentalFeature
import kotlinx.coroutines.flow.update
import org.koin.androidx.compose.getViewModel

@OptIn(MapsExperimentalFeature::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Nearby(
    bottomController: NavController = rememberNavController(),
) {
    val viewModel = getViewModel<NearbyViewModel>()
    val context = LocalContext.current
    val currentLocation = remember { mutableStateOf(BranchDummy.malang.branchCoordinates) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(currentLocation.value.first, currentLocation.value.second), 14f)
    }
    val isLoading  = remember { mutableStateOf(true) }
    val message    = remember { mutableStateOf("") }
    val branchList = remember { mutableStateOf(listOf<BranchModel>()) }
    val closestBranch = remember { mutableStateOf(Pair(BranchDummy.malang, 0.0)) }
    val isStreetView = remember { mutableStateOf(false) }
    val selectedCoordinates = remember { mutableStateOf(closestBranch.value.first) }

    viewModel.getNearbyBranches {
        branchList.value = it
        Log.d("BranchList", it.toString())
        closestBranch.value = findClosestBranch(currentLocation.value.first, currentLocation.value.second, it)
    }

    RequestLocationPermission(
        onPermissionGranted = {
            Log.d("Nearby", "Permission granted")
            message.value = "Getting your location..."
            initializeLocationProvider(context)
            getCurrentLocation(
                onGetCurrentLocationSuccess = { location ->
                    message.value = ""
                    currentLocation.value = Pair(location.first, location.second)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(location.first, location.second), 14f)
                    Log.d("Nearby", "Location: $location")
                    closestBranch.value = findClosestBranch(currentLocation.value.first, currentLocation.value.second, branchList.value)
                    isLoading.value = false
                },
                onGetCurrentLocationFailed = { exception ->
                    Log.e("Nearby", "getLastLocation:exception", exception)
                    message.value = "Failed to get your location"
                    isLoading.value = false
                },
                context = context
            )
        },
        onPermissionDenied = {
            bottomController.navigate(BottomBarNav.Home.route)
            message.value = "Location permission denied"
            Log.d("Nearby", "Permission denied")
            isLoading.value = false
        },
        onPermissionsRevoked = {
            Log.d("Nearby", "Permission revoked")
            bottomController.navigate(BottomBarNav.Home.route)
            message.value = "Location permission revoked"
        }
    )

    val uiSettings = remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }

    val properties = remember {
        mutableStateOf(MapProperties(
            mapType = MapType.NORMAL,
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.maps))
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                BranchCard(branchModel = selectedCoordinates.value)
                Spacer(modifier = Modifier.height(8.dp))
                RoundedBarButton(text = "Book Reservation")
                Spacer(modifier = Modifier.height(140.dp))
            }
        },
        contentColor = CompfestBlack,
        containerColor = CompfestBlack
    ){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            if(isStreetView.value){
                com.google.maps.android.compose.streetview.StreetView(
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

            } else {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = properties.value,
                    uiSettings = uiSettings.value,
                ){
                    MarkerInfoWindow(
                        state = MarkerState(position = LatLng(currentLocation.value.first, currentLocation.value.second)),
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
                                Text("Closes Branch: ${closestBranch.value.first.branchName}", fontWeight = FontWeight.Normal, color = Color.White)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    branchList.value.forEach{branch ->
                        Marker(
                            state = MarkerState(position = LatLng(branch.branchCoordinates.first, branch.branchCoordinates.second)),
                            title = branch.branchName,
                            snippet = branch.branchAddress,
                            onInfoWindowClick = {
                                selectedCoordinates.value = branch
                                isStreetView.value = true
                            },
                            onClick = {
                                selectedCoordinates.value = branch
                                Toast.makeText(context, "Click the white card to open StreetView", Toast.LENGTH_SHORT).show()
                                false
                            }
                        )
                    }

                    Polyline(
                        points = listOf(
                            LatLng(currentLocation.value.first, currentLocation.value.second),
                            LatLng(closestBranch.value.first.branchCoordinates.first, closestBranch.value.first.branchCoordinates.second)
                        ),
                        clickable = true,
                        color = CompfestAqua,
                        width = 5f
                    )
            }
        }

            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                Text(text = message.value, fontSize = 14.sp, color = CompfestWhite)
                Spacer(modifier = Modifier.height(8.dp))
                if (isLoading.value){
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = CompfestPink, trackColor = CompfestAqua)
                }
                Spacer(modifier = Modifier.height(96.dp))
            }
        }
    }
}