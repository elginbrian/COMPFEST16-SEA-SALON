package com.compfest16.sea_salon.features.presentation.screen.dashboard_section

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.presentation.component.button.DropDownButton
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.textfield.SingleLineTextField
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun CreateBranch(bottomController: NavHostController = rememberNavController()) {
    val viewmodel = getViewModel<DashboardViewModel>()
    val branch    = remember { mutableStateOf(BranchDummy.empty) }
    val latitude  = remember { mutableStateOf("") }
    val longitude = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

        },
        contentColor = CompfestBlack,
        containerColor = CompfestBlack
    ){
        LazyColumn(modifier = Modifier.fillMaxSize()){
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }

            item {
                Text(text = "Create New Branch", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 12.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)){
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            110.dp
                        ),
                        colors = CardDefaults.cardColors(CompfestGrey),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, CompfestWhite)
                    ) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        CompfestBlack,
                                        CompfestGrey,
                                        CompfestBlack
                                    )
                                )
                            ),
                            contentAlignment = Alignment.Center
                        ){
                            Card(modifier = Modifier.size(48.dp),
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(CompfestLightGrey)
                            ) {
                                Box(modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {

                                    }, contentAlignment = Alignment.Center){
                                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = CompfestWhite)
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)){
                    SingleLineTextField(
                        title = "Branch Name",
                        painter = painterResource(id = R.drawable.spa),
                        value = branch.value.branchName,
                        onValueChange = {
                            branch.value = branch.value.copy(branchName = it)
                        }
                    )

                    SingleLineTextField(
                        title = "Branch Address",
                        painter = painterResource(id = R.drawable.location),
                        value = branch.value.branchAddress,
                        onValueChange = {
                            branch.value = branch.value.copy(branchAddress = it)
                        }
                    )

                    Row(modifier = Modifier.fillMaxWidth()){
                        Column(modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 8.dp)) {
                            SingleLineTextField(
                                title = "Latitude",
                                painter = painterResource(id = R.drawable.location),
                                value = latitude.value,
                                onValueChange = {
                                    latitude.value = it
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            SingleLineTextField(
                                title = "Longitude",
                                painter = painterResource(id = R.drawable.location),
                                value = longitude.value,
                                onValueChange = {
                                    longitude.value = it
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth()){
                        Column(modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(end = 8.dp)) {
                            DropDownButton(
                                value = "Opening Hours",
                                painter = painterResource(id = R.drawable.reservation)
                            ){

                            }
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            DropDownButton(
                                painter = painterResource(id = R.drawable.reservation),
                                value = "Closing Hours",
                            ){

                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)){
                    RoundedBarButton(color = CompfestPink, text = "Save")
                }
            }
        }
    }
}