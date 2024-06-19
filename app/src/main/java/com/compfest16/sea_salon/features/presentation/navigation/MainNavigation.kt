package com.compfest16.sea_salon.features.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class MainNav(val route: String){
    object Main: MainNav("main")
    object Splash: MainNav("splash")
}

@Composable
fun MainNavigation(){
    val mainController = rememberNavController()
    NavHost(navController = mainController, startDestination = MainNav.Splash.route){
        composable(MainNav.Splash.route){
            SplashNavigation()
        }

        composable(MainNav.Main.route){

        }
    }
}

