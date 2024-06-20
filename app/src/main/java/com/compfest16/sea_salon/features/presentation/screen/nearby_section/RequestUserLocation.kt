package com.compfest16.sea_salon.features.presentation.screen.nearby_section

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource

private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

/**
 * Initialize the FusedLocationProviderClient where needed (e.g., in your activity or fragment).
 */
fun initializeLocationProvider(context: Context) {
    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
}

/**
 * Retrieves the last known user location asynchronously.
 *
 * @param onGetLastLocationSuccess Callback function invoked when the location is successfully retrieved.
 *        It provides a LatLng object representing latitude and longitude.
 * @param onGetLastLocationFailed Callback function invoked when an error occurs while retrieving the location.
 *        It provides the Exception that occurred.
 * @param context Context instance required for checking permissions.
 */
@SuppressLint("MissingPermission")
fun getLastUserLocation(
    onGetLastLocationSuccess: (LatLng) -> Unit,
    onGetLastLocationFailed: (Exception) -> Unit,
    context: Context
) {
    // Check if location permissions are granted
    if (areLocationPermissionsGranted(context)) {
        // Retrieve the last known location
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    // If location is not null, invoke the success callback with LatLng object
                    val latLng = LatLng(it.latitude, it.longitude)
                    onGetLastLocationSuccess(latLng)
                } ?: run {
                    // Location is null, handle accordingly
                    onGetLastLocationFailed(Exception("Last location is null"))
                }
            }
            .addOnFailureListener { exception ->
                // If an error occurs, invoke the failure callback with the exception
                onGetLastLocationFailed(exception)
            }
    } else {
        // Handle case where permissions are not granted
        onGetLastLocationFailed(Exception("Location permissions not granted"))
    }
}

/**
 * Checks if location permissions are granted.
 *
 * @param context Context instance required for checking permissions.
 * @return true if both ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permissions are granted; false otherwise.
 */
fun areLocationPermissionsGranted(context: Context): Boolean {
    return (ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
}