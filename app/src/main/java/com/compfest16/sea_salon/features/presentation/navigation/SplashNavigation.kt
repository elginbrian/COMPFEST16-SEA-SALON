package com.compfest16.sea_salon.features.presentation.navigation

import androidx.compose.runtime.Composable

sealed class SplashNav(val route: String){
    object Splash1: SplashNav("splash1")
}

@Composable
fun SplashNavigation(){

}