package com.compfest16.sea_salon.features.presentation.screen.auth_section

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.component.button.EditProfileButton
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.textfield.SecureLineTextField
import com.compfest16.sea_salon.features.presentation.component.textfield.SingleLineTextField
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.SplashNav

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun SignUp(
    splahController: NavController = rememberNavController()
){
    val signUp = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {},
        bottomBar = {},
        containerColor = CompfestBlueGrey,
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .padding(top = 82.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
            Text(text = "Sign-Up", fontSize = 42.sp, color = CompfestWhite, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                EditProfileButton(color = CompfestAqua)
            }

            Spacer(modifier = Modifier.height(16.dp))
            SingleLineTextField(title = "Username", painter = painterResource(id = R.drawable.person))
            SingleLineTextField(title = "Email", painter = painterResource(id = R.drawable.email), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
            SingleLineTextField(title = "Phone Number", painter = painterResource(id = R.drawable.email), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
            SecureLineTextField(title = "Password", painter = painterResource(id = R.drawable.password))
            SecureLineTextField(title = "Confirm Password", painter = painterResource(id = R.drawable.password))
            Spacer(modifier = Modifier.height(32.dp))
            RoundedBarButton(text = "Create Account", color = CompfestAqua){
                signUp.value = true
            }
        }

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom) {
            Text(text = "Already have an account?", fontSize = 14.sp, color = CompfestWhite)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Login", fontSize = 14.sp, color = CompfestPurple, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable {
                splahController.navigate(SplashNav.Login.route)
            })
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            if(signUp.value){
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = CompfestAqua, trackColor = CompfestGrey)
            }
        }
    }
}