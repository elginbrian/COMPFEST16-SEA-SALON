package com.compfest16.sea_salon.features.presentation.screen.dashboard_section

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.widget.ActionCard
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav
import com.compfest16.sea_salon.features.presentation.navigation.MainNav
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Dashboard(
    bottomController: NavHostController = rememberNavController(),
    mainController: NavHostController = rememberNavController()
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

        },
        contentColor = CompfestBlack,
        containerColor = CompfestBlack
    ){
        LazyColumn(modifier = Modifier.fillMaxSize()){
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
                            ))
                            )
                            Text(text = "-- Admin Dashboard --", color = CompfestWhite, fontSize = 14.sp, fontWeight = FontWeight.Normal, textAlign = TextAlign.Right, fontStyle = FontStyle.Italic)
                        }
                    }
                }
            }

            item {
                Text(text = "Actions", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    ActionCard(
                        header = "Create New Branch",
                        description = "Create a new branch location"
                    ){
                        bottomController.navigate(BottomBarNav.CreateBranch.route)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    ActionCard(
                        header = "See All Reservations",
                        description = "See reservations from all branches",
                        tint = CompfestAqua,
                        painter = painterResource(id = R.drawable.reservation)
                    ){
                        bottomController.navigate(BottomBarNav.SeeReservation.route)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    ActionCard(
                        header = "See All Reviews",
                        description = "See reviews from all branches",
                        tint = CompfestPurple,
                        painter = painterResource(id = R.drawable.star)
                    ){
                        bottomController.navigate(BottomBarNav.SeeReview.route)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    ActionCard(
                        header = "See All Customers",
                        description = "See all customers information",
                        tint = CompfestPink,
                        painter = painterResource(id = R.drawable.person)
                    ){
                        bottomController.navigate(BottomBarNav.SeeUser.route)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                   RoundedBarButton(text = "logout", color = Color(0xFFEC4444)){
                       Firebase.auth.signOut()
                       mainController.navigate(MainNav.Splash.route)
                   }
                }
            }

            item {
                Spacer(modifier = Modifier.height(160.dp))
            }
        }
    }
}