package com.compfest16.sea_salon.features.presentation.screen.review_section

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.compfest16.sea_salon.features.presentation.component.button.DropDownButton
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.compfest16.sea_salon.features.presentation.component.button.StarButton
import com.compfest16.sea_salon.features.presentation.component.textfield.TransparentTextField
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun PostReview(bottomController: NavHostController = rememberNavController()) {
    val viewModel        = getViewModel<ReviewViewModel>()
    val branchName       = bottomController.currentBackStackEntry?.arguments?.getString("branch_name") ?: ""
    val date             = bottomController.currentBackStackEntry?.arguments?.getString("date") ?: ""
    val branchID         = bottomController.currentBackStackEntry?.arguments?.getString("branch_id") ?: ""
    val userID           = bottomController.currentBackStackEntry?.arguments?.getString("user_id") ?: ""
    val stars            = remember { mutableStateOf(0) }
    val note             = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(Uri.EMPTY) }
    val message          = remember { mutableStateOf("") }
    val isLoading        = remember { mutableStateOf(false) }
    val reviewID         = remember { mutableStateOf(UUID.randomUUID().toString()) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri.value = uri
        }
    )

    if(message.value.equals("Review Posted Successfully") || message.value.equals("Image Uploaded Successfully")){
        LaunchedEffect(Unit) {
            delay(1000)
            bottomController.navigate(BottomBarNav.Home.route)
        }
    } else if(message.value.isNotEmpty()){
        LaunchedEffect(Unit) {
            delay(4000)
            isLoading.value = false
            message.value = ""
        }
    }

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
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = CompfestBlueGrey,
                    contentColor = CompfestBlueGrey,
                    bottomBar = {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 72.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            StarButton(onRatingChanged = { newRating ->
                                stars.value = newRating
                            })
                            Spacer(modifier = Modifier.height(
                                if(isLoading.value) 140.dp else 100.dp
                            ))
                        }
                    }
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        DropDownButton(value = branchName, painter = painterResource(id = R.drawable.location))
                        DropDownButton(value = date, painter = painterResource(id = R.drawable.reservation))
                        Spacer(modifier = Modifier.height(12.dp))
                        TransparentTextField(text = note.value, onValueChange = {
                           note.value = it
                        }, hint = "Write down your review here!") {}
                    }

                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                        Text(text = message.value, fontSize = 14.sp, color = CompfestWhite)
                        Spacer(modifier = Modifier.height(8.dp))
                        if (isLoading.value){
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = CompfestPink, trackColor = CompfestAqua)
                        }
                        Spacer(modifier = Modifier.height(96.dp))
                    }
                }
            }
        },
        floatingActionButton = {
            Button(onClick = {
               if(stars.value != 0 && note.value.isNotEmpty()){
                   isLoading.value = true
                   viewModel.postReview(reviewModel = ReviewModel(
                       reviewID = reviewID.value,
                       branchID = branchID,
                       userID = userID,
                       star = stars.value,
                       comment = note.value,
                   ), image = ImageModel(
                       imageID = UUID.randomUUID().toString(),
                       affiliateID = reviewID.value,
                       src = selectedImageUri.value ?: Uri.EMPTY,
                       role = 3
                   )
                   ){
                       message.value = it
                   }
               } else {
                   isLoading.value = true
                   message.value = "Please fill all the forms"
               }
            }, colors = ButtonDefaults.buttonColors(CompfestLightGrey)) {
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
                AsyncImage(
                    model = selectedImageUri.value,
                    contentDescription = "review-photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Card(modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(CompfestLightGrey)
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }, contentAlignment = Alignment.Center){
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = CompfestWhite)
                    }
                }
            }
        }
    }
}