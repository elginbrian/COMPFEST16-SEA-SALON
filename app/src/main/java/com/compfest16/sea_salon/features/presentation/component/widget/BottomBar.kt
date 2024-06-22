package com.compfest16.sea_salon.features.presentation.component.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurpleLight
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav

@Composable
@Preview
fun BottomBar(bottomController: NavHostController = rememberNavController()) {
    val index = remember { mutableStateOf(0) }
    BottomAppBar(
        containerColor = CompfestBlueGrey,
        modifier = Modifier.height(96.dp),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                bottomController.navigate(BottomBarNav.Home.route)
                index.value = 0
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    tint = if(index.value == 0) CompfestPurpleLight else CompfestLightGrey,
                    modifier = Modifier.size(if(index.value == 0) 26.dp else 24.dp)
                )
            }
            IconButton(onClick = {
                bottomController.navigate(BottomBarNav.Nearby.route)
                index.value = 1
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = "Location",
                    tint = if(index.value == 1) CompfestPurpleLight else CompfestLightGrey,
                    modifier = Modifier.size(if(index.value == 1) 26.dp else 24.dp)
                )
            }
            IconButton(onClick = {
                bottomController.navigate(BottomBarNav.SelectCities.route)
                index.value = 2
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.reservation),
                    contentDescription = "Reservation",
                    tint = if(index.value == 2) CompfestPurpleLight else CompfestLightGrey,
                    modifier = Modifier.size(if(index.value == 2) 26.dp else 24.dp)
                )
            }
            IconButton(onClick = {
                index.value = 3
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = "Star",
                    tint = if(index.value == 3) CompfestPurpleLight else CompfestLightGrey,
                    modifier = Modifier.size(if(index.value == 3) 26.dp else 24.dp)
                )
            }
        }
    }
}