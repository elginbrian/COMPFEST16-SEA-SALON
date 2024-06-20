package com.compfest16.sea_salon.features.presentation.screen.auth_section

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.collectAsState
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
import com.compfest16.sea_salon.features.domain.dummy.ImageDummy
import com.compfest16.sea_salon.features.domain.dummy.UserDummy
import com.compfest16.sea_salon.features.presentation.component.button.EditProfileButton
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.textfield.SecureLineTextField
import com.compfest16.sea_salon.features.presentation.component.textfield.SingleLineTextField
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.SplashNav
import org.koin.androidx.compose.getViewModel
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun SignUp(
    splashController: NavController = rememberNavController()
){
    val viewModel  = getViewModel<AuthViewModel>()
    val signUp     = remember { mutableStateOf(false) }
    val imageModel = remember { mutableStateOf(ImageDummy.notFound) }
    val userModel  = remember { mutableStateOf(UserDummy.empty) }
    val message    = remember { mutableStateOf("") }

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
                EditProfileButton(color = CompfestAqua){
                    imageModel.value = imageModel.value.copy(
                        src = it,
                        alt = "profile-picture",
                        role = 1,
                        imageID = UUID.randomUUID().toString()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            SingleLineTextField(
                title = "Username",
                painter = painterResource(id = R.drawable.person),
                value = userModel.value.fullName,
                onValueChange = {
                    userModel.value.fullName = it
                }
            )
            SingleLineTextField(
                title = "Email",
                painter = painterResource(id = R.drawable.email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = userModel.value.email,
                onValueChange = {
                    userModel.value.email = it
                }
            )
            SingleLineTextField(
                title = "Phone Number",
                painter = painterResource(id = R.drawable.email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                value = userModel.value.phoneNum,
                onValueChange = {
                    userModel.value.phoneNum = it
                }
            )
            SecureLineTextField(
                title = "Password",
                painter = painterResource(id = R.drawable.password),
                value = userModel.value.password,
                onValueChange = {
                    userModel.value.password = it
                }
            )
            SecureLineTextField(
                title = "Confirm Password",
                painter = painterResource(id = R.drawable.password),
                value = userModel.value.password,
                onValueChange = {
                    userModel.value.password
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            RoundedBarButton(text = "Create Account", color = CompfestAqua){
                signUp.value = true
                userModel.value.userID = UUID.randomUUID().toString()
                imageModel.value.affiliateID = userModel.value.userID

                message.value = "Verifying..."
                viewModel.signUp(userModel.value, imageModel.value){
                    message.value = it
                }

                userModel.value = UserDummy.empty
                imageModel.value = ImageDummy.notFound
            }
        }

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom) {
            Text(text = "Already have an account?", fontSize = 14.sp, color = CompfestWhite)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Login", fontSize = 14.sp, color = CompfestPurple, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable {
                splashController.navigate(SplashNav.Login.route)
            })
        }

        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
            if(signUp.value){
                Text(text = message.value, fontSize = 14.sp, color = CompfestWhite)
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = CompfestAqua, trackColor = CompfestGrey)
            }
        }
    }
}