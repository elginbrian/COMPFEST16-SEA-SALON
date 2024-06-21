package com.compfest16.sea_salon.features.presentation.navigation

import android.os.Build
import android.transition.Fade
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

sealed class MainNav(val route: String){
    object Main: MainNav("main")
    object Splash: MainNav("splash")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation(){
    val mainController = rememberNavController()
    NavHost(navController = mainController, startDestination = MainNav.Splash.route, modifier = Modifier.background(CompfestBlack)){
        composable(MainNav.Splash.route, enterTransition = {
            return@composable fadeIn(tween(700))
        }, popExitTransition = {
            return@composable fadeOut(tween(700))
        }){
            SplashNavigation(mainController)
        }

        composable(MainNav.Main.route, enterTransition = {
            return@composable fadeIn(tween(700))
        }, popExitTransition = {
            return@composable fadeOut(tween(700))
        }){
            BottomBarNavigation(mainController)
        }
    }
}

