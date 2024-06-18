package com.compfest16.sea_salon.features.presentation.navigation

import androidx.compose.runtime.Composable

sealed class BottomBarNav(val route: String){
    object Home : BottomBarNav("home")
}

@Composable
fun BottomBarNavigation(){

}