package com.compfest16.sea_salon.features.presentation.screen.review_section

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.features.presentation.component.button.DropDownButton
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.component.textfield.TransparentTextField
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun PostReview(bottomController: NavHostController = rememberNavController()) {
    val branchName = bottomController.currentBackStackEntry?.arguments?.getString("branch_name") ?: ""
    val date = bottomController.currentBackStackEntry?.arguments?.getString("date") ?: ""
    val reservationID = bottomController.currentBackStackEntry?.arguments?.getString("reservation_id") ?: ""
    val userID = bottomController.currentBackStackEntry?.arguments?.getString("user_id") ?: ""
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(CompfestBlack, CompfestGrey))),
        bottomBar = {
              Card(
                  modifier = Modifier
                      .fillMaxWidth()
                      .fillMaxHeight(0.7f),
                  colors = CardDefaults.cardColors(CompfestBlueGrey),
                  elevation = CardDefaults.cardElevation(12.dp),
                  shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
              ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    DropDownButton(value = branchName, painter = painterResource(id = R.drawable.location))
                    DropDownButton(value = date, painter = painterResource(id = R.drawable.reservation))
                    TransparentTextField(onValueChange = {}, hint = "Write down your review here!") {

                    }

                }
              }
        },
        floatingActionButton = {
              Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(CompfestLightGrey)) {
                  Text(text = "Submit", color = CompfestWhite)
              }
        },
        contentColor = CompfestBlack,
        containerColor = CompfestBlack
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            CompfestBlack,
                            CompfestGrey,
                            CompfestBlack
                        )
                    )
                ),
                contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                Card(modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(CompfestLightGrey)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = CompfestWhite)
                    }
                }
            }
        }
    }
}