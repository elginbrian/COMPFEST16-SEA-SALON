package com.compfest16.sea_salon.features.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.screen.auth_section.Login
import com.compfest16.sea_salon.features.presentation.screen.auth_section.SignUp
import com.compfest16.sea_salon.features.presentation.screen.auth_section.Splash1
import com.compfest16.sea_salon.features.presentation.screen.auth_section.Splash2
import com.compfest16.sea_salon.features.presentation.screen.auth_section.Splash3
import com.compfest16.sea_salon.features.presentation.screen.auth_section.Splash4

sealed class SplashNav(val route: String){
    object Splash1: SplashNav("splash1")
    object Splash2: SplashNav("splash2")
    object Splash3: SplashNav("splash3")
    object Splash4: SplashNav("splash4")
    object Login: SplashNav("login")
    object SignUp: SplashNav("signup")
}

@Composable
fun SplashNavigation(){
    val splashController = rememberNavController()
    NavHost(navController = splashController, startDestination = SplashNav.Splash1.route, modifier = Modifier.background(
        CompfestBlack)){
        composable(SplashNav.Splash1.route, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
            )
        }, popExitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, tween(700)
            )
        }){
            Splash1(splashController)
        }

        composable(SplashNav.Splash2.route, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
            )
        }, popExitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, tween(700)
            )
        }){
            Splash2(splashController)
        }

        composable(SplashNav.Splash3.route, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
            )
        }, popExitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, tween(700)
            )
        }){
            Splash3(splashController)
        }

        composable(SplashNav.Splash4.route, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
            )
        }, popExitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, tween(700)
            )
        }){
            Splash4(splashController)
        }

        composable(SplashNav.Login.route, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
            )
        }, popExitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, tween(700)
            )
        }){
            Login(splashController)
        }

        composable(SplashNav.SignUp.route, enterTransition = {
            return@composable slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, tween(700)
            )
        }, popExitTransition = {
            return@composable slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, tween(700)
            )
        }){
            SignUp(splashController)
        }
    }
}