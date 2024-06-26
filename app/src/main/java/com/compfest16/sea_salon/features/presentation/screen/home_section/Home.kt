package com.compfest16.sea_salon.features.presentation.screen.home_section

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.dummy.ReviewDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.widget.BranchCard
import com.compfest16.sea_salon.features.presentation.component.widget.HistoryCard
import com.compfest16.sea_salon.features.presentation.component.widget.ReviewCard
import com.compfest16.sea_salon.features.presentation.component.widget.ServiceCard
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.RequestLocationPermission
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.find3ClosestBranches
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.getCurrentLocation
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.initializeLocationProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Home(bottomController: NavHostController = rememberNavController()) {

    val viewModel       = getViewModel<HomeViewModel>()
    val context         = LocalContext.current
    val currentLocation = remember { mutableStateOf(BranchDummy.malang.branchCoordinates) }
    val isLoading       = remember { mutableStateOf(true) }
    val message         = remember { mutableStateOf("") }
    val branchList      = remember { mutableStateOf(listOf<BranchModel>()) }
    val closestBranch   = remember { mutableStateOf(listOf(Pair(BranchDummy.malang, 0.0))) }
    val historyList     = remember { mutableStateOf(listOf<ReservationModel>()) }
    val review          = remember { mutableStateOf(listOf<ReviewModel>()) }
    val id              = remember { mutableStateOf("") }
    val intent          = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/08164829372")) }
    val listState       = rememberLazyListState()
    val coroutineScope  = rememberCoroutineScope()
    val itemCount       = 3

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                delay(3000)
                val nextIndex = (listState.firstVisibleItemIndex + 1) % itemCount
                listState.animateScrollToItem(index = nextIndex)
            }
        }
    }

    LaunchedEffect(Unit) {
        isLoading.value = true
        viewModel.getBranchList {
            branchList.value = it
            Log.d("BranchList", it.toString())
            closestBranch.value = find3ClosestBranches(currentLocation.value.first, currentLocation.value.second, it)
            isLoading.value = false
        }
    }

    LaunchedEffect(Unit) {
        isLoading.value = true
        val currentUserEmail = Firebase.auth.currentUser?.email
        if (currentUserEmail != null) {
            viewModel.getUserByEmail(currentUserEmail) { user ->
                id.value = user.userID
                Log.d("TAG", "TopBar: $id")
                viewModel.getUserHistory(user.userID){
                    historyList.value = it
                    isLoading.value = false
                }

                viewModel.getUserReview(
                    affiliate = id.value
                ){
                    review.value = it
                }
            }
        }
    }

    RequestLocationPermission(
        onPermissionGranted = {
            Log.d("Nearby", "Permission granted")
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
                    shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(painter = painterResource(id = R.drawable.home_hero), contentDescription = "hero", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                            Text(text = " SEA SALON™", fontSize = 48.sp, fontWeight = FontWeight.SemiBold, lineHeight = 50.sp, style = TextStyle(brush = Brush.horizontalGradient(
                                listOf(CompfestPink, CompfestWhite)
                            )))
                            Text(text = "\"Beauty and Elegance Redefined.\"", color = CompfestWhite, fontSize = 14.sp, fontWeight = FontWeight.Normal, textAlign = TextAlign.Right, fontStyle = FontStyle.Italic)
                        }
                    }
                }
            }

            item {
                Text(text = "Our Services", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(CompfestBlueGrey),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    LazyRow(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(3) { index ->
                            ServiceCard(index)
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
                    .height(
                        if (historyList.value.isEmpty()) 0.dp else 160.dp
                    )){
                    item{
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    items(historyList.value){ reservation ->
                        HistoryCard(
                            reservationModel = reservation,
                            branchModel = branchList.value.filter { it.branchID == reservation.branchID }.first()
                        ){
                            val route = BottomBarNav.Review.createRoute(
                                branchName = it.first.branchName,
                                date = it.second.date,
                                userId = id.value,
                                branchId = it.first.branchID
                            )
                            bottomController.navigate(route)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
            
            if(historyList.value.isEmpty()){
                item { 
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)) {
                        Text(text = "You haven't made any reservation yet", color = Color.LightGray, textAlign = TextAlign.Center, modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp), fontSize = 12.sp)
                        RoundedBarButton("Book Reservation Now!", color = CompfestAqua){
                            bottomController.navigate(BottomBarNav.SelectCities.route)
                        }
                    }
                }
            }

            item {
                Text(text = "SEA Salon™ Near You", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            items(closestBranch.value){
                if(!isLoading.value){
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)) {
                        BranchCard(branchModel = it.first){
                            val route = BottomBarNav.Reservation.createRoute(it)
                            bottomController.navigate(route)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                } else {

                }
            }

            item {
                Text(text = "Your Reviews", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            item {
                LazyRow(modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        if (review.value.isEmpty()) 0.dp else 160.dp
                    )){
                    item{
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    items(review.value){
                        ReviewCard(reviewModel = it, branchList = branchList.value)
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            if(review.value.isEmpty()){
                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)) {
                        Text(text = "You haven't reviewed any branch yet", color = Color.LightGray, textAlign = TextAlign.Center, modifier = Modifier
                            .fillMaxWidth(), fontSize = 12.sp)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(if(review.value.isEmpty()) 24.dp else 48.dp))
                Box(modifier = Modifier
                    .height(260.dp)
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf(CompfestPink, CompfestAqua)
                        )
                    )){
                    Row(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp), verticalAlignment = Alignment.CenterVertically){
                        Column(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f)
                            .padding(bottom = 96.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Contact Person:", color = CompfestWhite, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                            Text(text = "- 08123456789 (Thomas)", color = CompfestWhite, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp)
                            Text(text = "- 08164829372 (Sekar)", color = CompfestWhite, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp)
                        }
                        Column(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .padding(bottom = 96.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Button(onClick = {
                                startActivity(context, intent, null)
                         }, colors = ButtonDefaults.buttonColors(
                                CompfestWhite)) {
                                Text(text = "Chat with us!", color = CompfestAqua)
                            }
                        }
                    }
                }
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