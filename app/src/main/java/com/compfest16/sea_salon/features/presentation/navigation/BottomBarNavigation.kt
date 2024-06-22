package com.compfest16.sea_salon.features.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compfest16.sea_salon.features.presentation.component.widget.TopBar
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.screen.home_section.Home
import com.compfest16.sea_salon.features.presentation.screen.nearby_section.Nearby
import com.compfest16.sea_salon.features.presentation.component.widget.BottomBar
import com.compfest16.sea_salon.features.presentation.screen.reservation_section.Reservation
import com.compfest16.sea_salon.features.presentation.screen.reservation_section.SelectBranch
import com.compfest16.sea_salon.features.presentation.screen.reservation_section.SelectCity

sealed class BottomBarNav(val route: String){
    object Home : BottomBarNav("home")
    object Nearby : BottomBarNav("nearby")
    object SelectCities : BottomBarNav("select_cities")
    object SelectBranches : BottomBarNav("select_branches/{city_index}") {
        fun createRoute(cityIndex: Int) = "select_branches/$cityIndex"
    }
    object Reservation : BottomBarNav("reservation/{branch_id}"){
        fun createRoute(branchId: String) = "reservation/$branchId"
    }
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
            BottomBar(bottomController)
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

            composable(BottomBarNav.SelectCities.route, enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
                )
            }){
                SelectCity(bottomController)
            }

            composable(BottomBarNav.SelectBranches.route, enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
                )
            }, arguments = listOf(navArgument("city_index") { type = NavType.IntType })
            ){
                SelectBranch(bottomController)
            }

            composable(BottomBarNav.Reservation.route, enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down, tween(700)
                )
            }, arguments = listOf(navArgument("branch_id") { type = NavType.StringType })
            ){
                Reservation(bottomController)
            }
        }
    }
}