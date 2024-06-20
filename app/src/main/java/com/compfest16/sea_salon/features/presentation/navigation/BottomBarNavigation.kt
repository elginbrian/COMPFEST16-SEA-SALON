package com.compfest16.sea_salon.features.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.screen.home_section.Home

sealed class BottomBarNav(val route: String){
    object Home : BottomBarNav("home")
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
                Home()
            }
        }
    }
}