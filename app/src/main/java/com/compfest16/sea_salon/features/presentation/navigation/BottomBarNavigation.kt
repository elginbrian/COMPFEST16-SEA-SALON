package com.compfest16.sea_salon.features.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.features.presentation.component.widget.TopBar
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.screen.home_section.Home
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.Nearby
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.StreetView

sealed class BottomBarNav(val route: String){
    object Home : BottomBarNav("home")
    object Nearby : BottomBarNav("nearby")
    object StreetView : BottomBarNav("street_view")
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomBarNavigation(mainController: NavHostController) {
    val bottomController = rememberNavController()
    Scaffold(
        contentColor = CompfestBlack,
        containerColor = CompfestBlack,
        bottomBar = {
            BottomAppBar(
                containerColor = CompfestBlueGrey,
                modifier = Modifier.height(96.dp),
            ){
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { bottomController.navigate(BottomBarNav.Home.route) }) {
                        Icon(painter = painterResource(id = R.drawable.home), contentDescription = "Home", tint = CompfestWhite, modifier = Modifier.size(26.dp))
                    }
                    IconButton(onClick = { bottomController.navigate(BottomBarNav.Nearby.route) }) {
                        Icon(painter = painterResource(id = R.drawable.location), contentDescription = "Location", tint = CompfestWhite, modifier = Modifier.size(26.dp))
                    }
                    IconButton(onClick = { /* Handle favorite click */ }) {
                        Icon(painter = painterResource(id = R.drawable.reservation), contentDescription = "Reservation", tint = CompfestWhite, modifier = Modifier.size(26.dp))
                    }
                    IconButton(onClick = { /* Handle favorite click */ }) {
                        Icon(painter = painterResource(id = R.drawable.star), contentDescription = "Star", tint = CompfestWhite, modifier = Modifier.size(26.dp))
                    }
                }
            }
        },
        topBar = {
            Box(modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 42.dp)){
                TopBar()
            }
        }
    ) {
        NavHost(navController = bottomController, startDestination = BottomBarNav.Home.route){
            composable(BottomBarNav.Home.route, enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
                )
            }){
                Home(bottomController)
            }

            composable(BottomBarNav.Nearby.route, enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
                )
            }){
                Nearby(bottomController)
            }
            
            composable(BottomBarNav.StreetView.route, enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
                )
            }){
                StreetView()
            }
        }
    }
}