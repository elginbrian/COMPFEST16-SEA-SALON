package com.compfest16.sea_salon.features.presentation.screen.reservation_section

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.features.presentation.component.widget.CityCard
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurpleDark
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurpleLight
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun SelectCity(bottomController: NavHostController = rememberNavController()) {
    val viewModel = getViewModel<ReservationViewModel>()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(CompfestBlack, CompfestGrey))),
        topBar = {

        },
        contentColor = CompfestBlack,
        containerColor = CompfestBlack
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(CompfestBlack, CompfestBlueGrey)))){
            LazyColumn(modifier = Modifier.fillMaxSize()
            ){
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }

                item {
                    Text(text = "Select Cities", modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp, bottom = 12.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                }

                items(4){
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)) {
                        CityCard(it){
                            val route = BottomBarNav.SelectBranches.createRoute(it)
                            bottomController.navigate(route)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}