package com.compfest16.sea_salon.features.presentation.screen.review_section

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.widget.HistoryCard
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun History(bottomController: NavHostController = rememberNavController()) {
    val viewModel   = getViewModel<ReviewViewModel>()
    val branchList   = remember { mutableStateOf(listOf<BranchModel>()) }
    val historyList = remember { mutableStateOf(listOf<ReservationModel>()) }
    val id          = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getBranchList {
            branchList.value = it
            Log.d("BranchList", it.toString())
        }
    }

    LaunchedEffect(Unit) {
        val currentUserEmail = Firebase.auth.currentUser?.email
        if (currentUserEmail != null) {
            viewModel.getUserByEmail(currentUserEmail) { user ->
                id.value = user.userID
                Log.d("TAG", "TopBar: $id")
                viewModel.getUserHistory(user.userID){
                    historyList.value = it
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(CompfestBlack, CompfestGrey))),
        contentColor = CompfestWhite,
        containerColor = CompfestBlack
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(CompfestBlack, CompfestBlueGrey)))
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp), // Vertical spacing between items
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(60.dp))
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Text(
                        text = "Reservation History",
                        modifier = Modifier
                            .padding(top = 24.dp),
                        color = CompfestWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                items(historyList.value) { reservation ->
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
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Spacer(modifier = Modifier.height(120.dp))
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            if(historyList.value.isEmpty()){
                Text(text = "You haven't made any reservation yet", color = Color.LightGray, textAlign = TextAlign.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp), fontSize = 12.sp)
                RoundedBarButton("Book Reservation Now!", color = CompfestAqua){
                    bottomController.navigate(BottomBarNav.SelectCities.route)
                }
                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }
}

