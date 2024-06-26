package com.compfest16.sea_salon.features.presentation.screen.auth_section

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.SplashNav
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Splash2(navController: NavController = rememberNavController()){
    var currentProgress = remember { mutableStateOf(0f) }

    LaunchedEffect(Unit){
        loadProgress {
            currentProgress.value = it
        }
        navController.navigate(SplashNav.Splash3.route)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {},
        bottomBar = {},
        floatingActionButton = {
            Column(modifier = Modifier.padding(bottom = 36.dp, end = 12.dp)) {
                Card(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(CompfestPurple)
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate(SplashNav.Splash3.route)
                        }, contentAlignment = Alignment.Center){
                        Icon(painter = painterResource(id = R.drawable.arrow_right), contentDescription = "arrow", modifier = Modifier.size(32.dp), tint = CompfestWhite)
                    }
                }
            }
        },
        containerColor = CompfestBlack,
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 36.dp)){
            Image(painter = painterResource(id = R.drawable.splash2), contentDescription = "background", contentScale = ContentScale.Crop)
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = CompfestPurple, trackColor = CompfestGrey, progress = currentProgress.value)
        }

        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.6f)
            .padding(bottom = 42.dp, start = 16.dp), verticalArrangement = Arrangement.Bottom) {
            Text(text = "Developed by:", color = CompfestWhite, fontSize = 12.sp, fontWeight = FontWeight.Medium, lineHeight = 14.sp)
            Text(text = "Elgin Brian Wahyu Bramadhika", color = CompfestWhite, fontSize = 12.sp, fontWeight = FontWeight.Normal, lineHeight = 14.sp)
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
            Text(text = "Faculty of Computer Science,\nUniversity of Brawijaya", color = CompfestWhite, fontSize = 12.sp, fontWeight = FontWeight.Normal, fontStyle = FontStyle.Italic, lineHeight = 14.sp)
        }
    }
}

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    val steps = 1000
    for (i in 1..steps) {
        updateProgress(i.toFloat() / steps)
        delay(10)
    }
}
