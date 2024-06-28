package com.compfest16.sea_salon.features.presentation.screen.dashboard_section

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compfest16.sea_salon.features.domain.dummy.UserDummy
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun SeeUsers(){
    val userList  = remember { mutableStateOf(listOf<UserModel>()) }
    val viewmodel = getViewModel<DashboardViewModel>()

    LaunchedEffect(Unit) {
        viewmodel.getAllUsers {
            userList.value = it
        }
    }

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
                    Text(text = "All Users", modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp, bottom = 12.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                }

                items(userList.value){
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "userID : " + it.userID, fontSize = 12.sp, color = CompfestWhite, lineHeight = 18.sp)
                        Text(text = "fullname : " + it.fullName, fontSize = 12.sp, color = CompfestWhite, lineHeight = 18.sp)
                        Text(text = "email : " + it.email, fontSize = 12.sp, color = CompfestWhite, lineHeight = 18.sp)
                        Text(text = "phone : " + it.phoneNum, fontSize = 12.sp, color = CompfestWhite, lineHeight = 18.sp)
                        Text(text = "isCustomer : " + it.isCustomer, fontSize = 12.sp, color = CompfestWhite, lineHeight = 18.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(thickness = 1.dp, color = CompfestPink)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(160.dp))
                }
            }
        }
    }
}