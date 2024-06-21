package com.compfest16.sea_salon.features.presentation.screen.auth_section

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.navigation.MainNav
import com.compfest16.sea_salon.features.presentation.navigation.SplashNav
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Splash1(
    navController: NavController = rememberNavController(),
    mainController: NavHostController = rememberNavController()
){
    LaunchedEffect(key1 = true){
        delay(3000)
        if(Firebase.auth.currentUser != null){
            mainController.navigate(MainNav.Main.route)
        } else {
            navController.navigate(SplashNav.Splash2.route)
        }
    }
    
    Scaffold(
        modifier = Modifier
            .padding()
            .fillMaxSize(),
        topBar = {},
        bottomBar = {},
        floatingActionButton = {},
        containerColor = CompfestBlack
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Box(modifier = Modifier.size(120.dp)){
                Image(painter = painterResource(id = R.drawable.compfest_logo), contentDescription = "")
            }
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                CircularProgressIndicator(color = CompfestPink)
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}