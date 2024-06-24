package com.compfest16.sea_salon.features.presentation.component.widget

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.screen.home_section.HomeViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import org.koin.androidx.compose.getViewModel

@Composable
@Preview
fun TopBar(
    profilePicture: Uri = Uri.EMPTY,
    userName: String = Firebase.auth.currentUser?.displayName ?: "SEA Salon",
    onClick: () -> Unit = {}
){
    val id                = remember { mutableStateOf("") }
    val viewModel         = getViewModel<HomeViewModel>()
    val profilePictureUrl = remember { mutableStateOf(profilePicture) }

    LaunchedEffect(Unit) {
        val currentUserEmail = Firebase.auth.currentUser?.email
        if (currentUserEmail != null) {
            viewModel.getUserByEmail(currentUserEmail) { user ->
                id.value = user.userID
                Log.d("TAG", "TopBar: $id")
                viewModel.getImageByAffiliate(id.value, "user_profile_picture") { images ->
                    profilePictureUrl.value = images.firstOrNull()?.src ?: Uri.EMPTY
                    Log.d("TAG", "TopBar: $profilePictureUrl")
                }
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(50.dp),
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .background(Brush.horizontalGradient(listOf(CompfestPurple, CompfestPink))),
        ) {
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(modifier = Modifier.size(44.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(CompfestLightGrey)
                ) {
                    Box(modifier = Modifier.fillMaxSize()){
                        AsyncImage(model = profilePictureUrl.value, contentDescription = "profile-picture", modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                onClick()
                            }, contentScale = ContentScale.Crop)
                    }

                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = userName, color = CompfestWhite, fontSize = 16.sp, modifier = Modifier.clickable {
                    onClick()
                })
            }
        }
    }
}