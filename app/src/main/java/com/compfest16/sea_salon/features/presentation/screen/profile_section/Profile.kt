package com.compfest16.sea_salon.features.presentation.screen.profile_section

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.ContactsContract.Profile
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.dummy.ImageDummy
import com.compfest16.sea_salon.features.domain.dummy.UserDummy
import com.compfest16.sea_salon.features.presentation.component.button.EditProfileButton
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.textfield.SecureLineTextField
import com.compfest16.sea_salon.features.presentation.component.textfield.SingleLineTextField
import com.compfest16.sea_salon.features.presentation.component.widget.RoundedProfile
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.MainNav
import com.compfest16.sea_salon.features.presentation.navigation.SplashNav
import com.compfest16.sea_salon.features.presentation.screen.auth_section.AuthViewModel
import com.compfest16.sea_salon.features.presentation.screen.home_section.HomeViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Profile(
    mainController: NavHostController = rememberNavController()
){
    val user              = remember { mutableStateOf(UserDummy.notFound) }
    val viewModel         = getViewModel<HomeViewModel>()
    val profilePictureUrl = remember { mutableStateOf(Uri.EMPTY) }

    LaunchedEffect(Unit) {
        val currentUserEmail = Firebase.auth.currentUser?.email
        if (currentUserEmail != null) {
            viewModel.getUserByEmail(currentUserEmail) {
                user.value = it
                viewModel.getImageByAffiliate(user.value.userID, "user_profile_picture") { images ->
                    profilePictureUrl.value = images.firstOrNull()?.src ?: Uri.EMPTY
                    Log.d("TAG", "TopBar: $profilePictureUrl")
                }
            }
        }
    }
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
            .padding(top = 130.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
            Text(text = "Your Profile", fontSize = 42.sp, color = CompfestWhite, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                RoundedProfile(selectedImageUri = profilePictureUrl.value)
            }

            Spacer(modifier = Modifier.height(16.dp))
            SingleLineTextField(
                title = user.value.fullName,
                painter = painterResource(id = R.drawable.person),
            )
            SingleLineTextField(
                title = user.value.email,
                painter = painterResource(id = R.drawable.email),
            )
            SingleLineTextField(
                title = user.value.phoneNum,
                painter = painterResource(id = R.drawable.phone),
            )
            Spacer(modifier = Modifier.height(32.dp))

            RoundedBarButton(text = "Logout", color = Color(0xFFEC4444)){
                Firebase.auth.signOut()
                mainController.navigate(MainNav.Splash.route)
            }
        }
    }
}