package com.compfest16.sea_salon.features.presentation.screen.dashboard_section

import DatePicker
import TimePicker
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.presentation.component.button.DropDownButton
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.textfield.SingleLineTextField
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun CreateBranch(bottomController: NavHostController = rememberNavController()) {
    val viewmodel        = getViewModel<DashboardViewModel>()
    val branch           = remember { mutableStateOf(BranchDummy.empty) }
    val latitude         = remember { mutableStateOf("") }
    val longitude        = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(Uri.EMPTY) }
    val message          = remember { mutableStateOf("") }
    val isLoading        = remember { mutableStateOf(false) }
    val showOpeningHours = remember { mutableStateOf(false) }
    val showClosingHours = remember { mutableStateOf(false) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri.value = uri
        }
    )

    if(message.value.equals("Branch Registered Successfully") || message.value.equals("Image Uploaded Successfully")){
        LaunchedEffect(Unit) {
            delay(1000)
            bottomController.navigate(BottomBarNav.Dashboard.route)
        }
    } else if(message.value.isNotEmpty()){
        LaunchedEffect(Unit) {
            delay(4000)
            isLoading.value = false
            message.value = ""
        }
    }

    if(showOpeningHours.value){
        TimePicker(onTimeSelected = {
           branch.value = branch.value.copy(openingHours = it)
            showOpeningHours.value = false
        }, onDismiss = { showOpeningHours.value = false },
        isTimeOnly = true
        )
    }

    if(showClosingHours.value){
        TimePicker(onTimeSelected = {
           branch.value = branch.value.copy(closingHours = it)
            showClosingHours.value = false
        }, onDismiss = { showClosingHours.value = false },
            isTimeOnly = true
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {

        },
        contentColor = CompfestBlack,
        containerColor = CompfestBlack
    ){
        LazyColumn(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(CompfestBlack, CompfestBlueGrey)))){
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
                            AsyncImage(
                                model = selectedImageUri.value,
                                contentDescription = "branch-photo",
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
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Opening Hours", color = CompfestWhite, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                            DropDownButton(
                                value = branch.value.openingHours,
                                painter = painterResource(id = R.drawable.reservation),
                                onValueChange = {
                                    branch.value = branch.value.copy(openingHours = it)
                                }
                            ){
                                showOpeningHours.value = true
                            }
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Closing Hours", color = CompfestWhite, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                            DropDownButton(
                                painter = painterResource(id = R.drawable.reservation),
                                value = branch.value.closingHours,
                                onValueChange = {
                                    branch.value = branch.value.copy(closingHours = it)
                                }
                            ){
                                showClosingHours.value = true
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
                    RoundedBarButton(color = CompfestPink, text = "Save"){
                        isLoading.value = true
                        if(selectedImageUri.value == Uri.EMPTY){
                            message.value = "Please select the branch photo"
                            return@RoundedBarButton
                        }

                        if(branch.value.branchName.isEmpty()){
                            message.value = "Please enter the branch name"
                            return@RoundedBarButton
                        }

                        if(branch.value.branchAddress.isEmpty()){
                            message.value = "Please enter the branch address"
                            return@RoundedBarButton
                        }

                        if(isValidLatitude(latitude.value).not() || isValidLongitude(longitude.value).not()){
                            message.value = "Please enter valid latitude and longitude"
                            return@RoundedBarButton
                        }

                        if(isOpeningBeforeClosing(branch.value.openingHours, branch.value.closingHours).not()){
                            message.value = "Opening hours must be before closing hours"
                            return@RoundedBarButton
                        }

                        branch.value = branch.value.copy(branchCoordinates = Pair(latitude.value.toDoubleOrNull() ?: 0.0, longitude.value.toDoubleOrNull() ?: 0.0))

                        viewmodel.postBranch(
                            branch = branch.value,
                            image = ImageModel(
                                affiliateID = branch.value.branchID,
                                src = selectedImageUri.value ?: Uri.EMPTY,
                                alt = "",
                                role = 5
                            )
                        ){
                            message.value = it
                        }
                    }
                }
            }
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

fun isValidLatitude(latitude: String): Boolean {
    val lat = latitude.toDoubleOrNull()
    return lat != null && lat in -90.0..90.0
}

fun isValidLongitude(longitude: String): Boolean {
    val lon = longitude.toDoubleOrNull()
    return lon != null && lon in -180.0..180.0
}

@RequiresApi(Build.VERSION_CODES.O)
fun isValidTimeFormat(time: String): Boolean {
    return try {
        LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
        true
    } catch (e: DateTimeParseException) {
        false
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun isOpeningBeforeClosing(openingTime: String, closingTime: String): Boolean {
    if (!isValidTimeFormat(openingTime) || !isValidTimeFormat(closingTime)) {
        return false
    }
    val opening = LocalTime.parse(openingTime, DateTimeFormatter.ofPattern("HH:mm"))
    val closing = LocalTime.parse(closingTime, DateTimeFormatter.ofPattern("HH:mm"))
    return opening.isBefore(closing)
}