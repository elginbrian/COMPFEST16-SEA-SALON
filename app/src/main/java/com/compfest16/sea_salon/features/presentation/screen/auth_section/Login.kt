package com.compfest16.sea_salon.features.presentation.screen.auth_section

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.textfield.SecureLineTextField
import com.compfest16.sea_salon.features.presentation.component.textfield.SingleLineTextField
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.MainNav
import com.compfest16.sea_salon.features.presentation.navigation.SplashNav
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Login(
    splashController: NavController = rememberNavController(),
    mainController: NavHostController = rememberNavController()
){
    val viewModel  = getViewModel<AuthViewModel>()
    val login      = remember{ mutableStateOf(false) }
    val email      = remember{ mutableStateOf("") }
    val password   = remember{ mutableStateOf("") }
    val message    = remember { mutableStateOf("") }

    if(message.value.equals("Login Success, redirecting to homepage...")){
        LaunchedEffect(Unit){
            delay(2000)
            mainController.navigate(MainNav.Main.route)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {},
        bottomBar = {},
        containerColor = CompfestBlueGrey,
    ){
        Box(modifier = Modifier
            .fillMaxSize()){
            Image(painter = painterResource(id = R.drawable.login), contentDescription = "background", contentScale = ContentScale.Crop)
        }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp), horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
            Text(text = "Greetings!", fontSize = 42.sp, color = CompfestWhite, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(32.dp))

            SingleLineTextField(
                title = "Email",
                painter = painterResource(id = R.drawable.email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = email.value,
                onValueChange = {
                    email.value = it
                }
            )
            SecureLineTextField(
                title = "Password",
                painter = painterResource(id = R.drawable.password),
                value = password.value,
                onValueChange = {
                    password.value = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Forgot Password?", fontSize = 14.sp, color = CompfestWhite)
            Spacer(modifier = Modifier.height(16.dp))
            RoundedBarButton(text = "Login"){
                login.value = true

                if(email.value.isEmpty() || password.value.isEmpty()){
                    message.value = "Please fill all fields"
                    return@RoundedBarButton
                }

                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()){
                    message.value = "Invalid email"
                    return@RoundedBarButton
                }

                if(password.value.length < 6){
                    message.value = "Password must be at least 6 characters"
                    return@RoundedBarButton
                }

                message.value = "Verifying..."
                viewModel.login(email.value, password.value){
                    message.value = it
                }
            }
        }

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom) {
            Text(text = "Don't have an account?", fontSize = 14.sp, color = CompfestWhite)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign Up", fontSize = 14.sp, color = CompfestAqua, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable {
                splashController.navigate(SplashNav.SignUp.route)
            })
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            if(login.value){
                Text(text = message.value, fontSize = 14.sp, color = CompfestWhite, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = CompfestPurple, trackColor = CompfestGrey)
            }
        }
    }
}