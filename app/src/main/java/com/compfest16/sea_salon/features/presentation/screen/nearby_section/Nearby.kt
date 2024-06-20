package com.compfest16.sea_salon.features.presentation.screen.nearby_section

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.presentation.component.widget.TopBar
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Nearby(
    bottomController: NavController = rememberNavController(),
) {
    val context = LocalContext.current
    val currentLocation = remember { mutableStateOf(BranchDummy.malang.branchCoordinates) }

    RequestLocationPermission(
        onPermissionGranted = {
            initializeLocationProvider(context)
            getLastUserLocation(
                onGetLastLocationSuccess = { location ->
                    currentLocation.value = Pair(location.latitude, location.longitude)
                },
                onGetLastLocationFailed = { exception ->
                    // Handle location retrieval failure
                },
                context = context
            )
        },
        onPermissionDenied = {
            // Handle permission denied
        },
        onPermissionsRevoked = {

        }
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(currentLocation.value.first, currentLocation.value.second), 18f)
    }

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
        topBar = {
            Box(modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 42.dp)){
                TopBar()
            }
        },
        contentColor = CompfestBlack,
        containerColor = CompfestBlack
    ){
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties.value,
            uiSettings = uiSettings.value,
        )
    }
}

