package com.compfest16.sea_salon.features.presentation.navigation

import androidx.compose.runtime.Composable

sealed class MainNav(val route: String){
    object Main: MainNav("main")
    object Splash: MainNav("splash")
}

@Composable
fun MainNavigation(){

}

