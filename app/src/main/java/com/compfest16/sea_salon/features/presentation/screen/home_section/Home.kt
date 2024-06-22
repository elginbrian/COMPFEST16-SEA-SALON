package com.compfest16.sea_salon.features.presentation.screen.home_section

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.dummy.ReservationDummy
import com.compfest16.sea_salon.features.domain.dummy.ReviewDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.presentation.component.widget.BranchCard
import com.compfest16.sea_salon.features.presentation.component.widget.HistoryCard
import com.compfest16.sea_salon.features.presentation.component.widget.ReviewCard
import com.compfest16.sea_salon.features.presentation.component.widget.TopBar
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.NearbyViewModel
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.RequestLocationPermission
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.find3ClosestBranches
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.findClosestBranch
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.getCurrentLocation
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.initializeLocationProvider
import com.compfest16.sea_salon.features.presentation.screen.test_section.Test
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Home(bottomController: NavHostController = rememberNavController()) {

    val viewModel = getViewModel<HomeViewModel>()
    val context = LocalContext.current
    val currentLocation = remember { mutableStateOf(BranchDummy.malang.branchCoordinates) }
    val isLoading  = remember { mutableStateOf(true) }
    val message    = remember { mutableStateOf("") }
    val branchList = remember { mutableStateOf(listOf<BranchModel>()) }
    val closestBranch = remember { mutableStateOf(listOf(Pair(BranchDummy.malang, 0.0))) }

    viewModel.getNearbyBranches {
        branchList.value = it
        Log.d("BranchList", it.toString())
        closestBranch.value = find3ClosestBranches(currentLocation.value.first, currentLocation.value.second, it)
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
                    Log.d("Nearby", "Location: $location")
                    closestBranch.value = find3ClosestBranches(currentLocation.value.first, currentLocation.value.second, branchList.value)
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
            message.value = "Location permission revoked"
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

        },
        contentColor = CompfestBlack,
        containerColor = CompfestBlack
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                    colors = CardDefaults.cardColors(CompfestBlueGrey),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(painter = painterResource(id = R.drawable.home_hero), contentDescription = "hero", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                            Text(text = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE), color = CompfestWhite)
                        }
                    }
                }
            }

            item {
                Text(text = "History", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            item {
                LazyRow(modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)){
                    item{
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    items(ReservationDummy.list){
                        HistoryCard(
                            reservationModel = ReservationDummy.list.random(),
                            branchModel = BranchDummy.list.random()
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            item {
                Text(text = "SEA Salon™ Near You", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            items(closestBranch.value){
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    BranchCard(branchModel = it.first)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(text = "Your Reviews", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            item {
                LazyRow(modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)){
                    item{
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    items(ReviewDummy.list){
                        ReviewCard(reviewModel = it, branchModel = BranchDummy.list.random())
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(160.dp))
            }
        }
    }
}