package com.compfest16.sea_salon.features.presentation.screen.reservation_section

import DatePicker
import TimePicker
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.presentation.component.button.RoundedBarButton
import com.compfest16.sea_salon.features.presentation.component.widget.BranchCard
import com.compfest16.sea_salon.features.presentation.component.widget.CityCard
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.presentation.component.button.DropDownButton
import com.compfest16.sea_salon.features.presentation.component.widget.SelectType
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.navigation.BottomBarNav
import com.compfest16.sea_salon.features.presentation.screen.dashboard_section.isValidTimeFormat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Reservation(bottomController: NavHostController = rememberNavController()) {
    val showDatePicker = remember { mutableStateOf(false) }
    val showTimePicker = remember { mutableStateOf(false) }
    val showTypePicker = remember { mutableStateOf(false) }
    val selectedDate   = remember { mutableStateOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy (HH:mm)")).toString()) }
    val selectedType   = remember { mutableStateOf("Haircuts and styling") }
    val branchId       = bottomController.currentBackStackEntry?.arguments?.getString("branch_id") ?: ""
    val viewModel      = getViewModel<ReservationViewModel>()
    val userId         = remember { mutableStateOf("") }
    val branchList     = remember { mutableStateOf(BranchDummy.list) }
    val currentBranch  = branchList.value.filter { it.branchID == branchId }.firstOrNull()
    val message        = remember { mutableStateOf("") }
    val isLoading      = remember { mutableStateOf(false) }
    val review         = remember { mutableStateOf(listOf<ReviewModel>()) }
    val userList       = remember { mutableStateOf(listOf<UserModel>()) }

    LaunchedEffect(Unit) {
        viewModel.getNearbyBranches {
            branchList.value = it
            Log.d("BranchList", it.toString())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getBranchReviews(branchId){
            review.value = it
            Log.d("Review", it.toString())

            viewModel.getAllUsers {
                Log.d("User", it.toString())
                userList.value = it
            }
        }
    }

    if(message.value.equals("Reservation Posted Successfully")){
        LaunchedEffect(Unit){
            delay(1000)
            bottomController.navigate(BottomBarNav.Home.route)
        }
    } else if(message.value.isNotEmpty()){
        LaunchedEffect(Unit) {
            delay(4000)
            message.value = ""
            isLoading.value = false
        }
    }

    LaunchedEffect(Unit) {
        val currentUserEmail = Firebase.auth.currentUser?.email
        if (currentUserEmail != null) {
            viewModel.getUserByEmail(currentUserEmail) { user ->
                userId.value = user.userID
                Log.d("TAG", "TopBar: ${userId.value}")
            }
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
                    Spacer(modifier = Modifier.height(120.dp))
                }

                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)) {
                        CityCard(
                            when(currentBranch?.branchName?.split(" ")?.get(0) ?: 3){
                                "Malang" -> 0
                                "Surabaya" -> 1
                                "Jakarta" -> 2
                                else -> 3
                            }
                        ){

                        }
                    }
                }

                items(branchList.value.filter { it.branchID == branchId }){
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)){
                        Spacer(modifier = Modifier.height(16.dp))
                        BranchCard(branchModel = it)
                    }
                }
                item{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)) {

                        Spacer(modifier = Modifier.height(8.dp))
                        DropDownButton(value = selectedDate.value, painter = painterResource(id = R.drawable.reservation)){
                            showDatePicker.value = true
                        }
                        if (showDatePicker.value) {
                            DatePicker(
                                onDateSelected = { date ->
                                    showTimePicker.value = true
                                    showDatePicker.value = false
                                },
                                onDismiss = {
                                    showDatePicker.value = false
                                },
                                onDateChanged = { date -> selectedDate.value = date }
                            )
                        }

                        if (showTimePicker.value) {
                            TimePicker(
                                onTimeSelected = { time ->
                                    selectedDate.value += " $time"
                                    showTimePicker.value = false
                                },
                                onDismiss = {
                                    showTimePicker.value = false
                                }
                            )
                        }
                        DropDownButton(value = selectedType.value, painter = painterResource(id = R.drawable.spa)){
                            showTypePicker.value = true
                        }
                        if (showTypePicker.value) {
                            SelectType(
                                currentSelection = selectedType.value,
                                closeSelection = {
                                    showTypePicker.value = false
                                    selectedType.value = it
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                item {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        RoundedBarButton(text = "Book Reservation"){
                            isLoading.value = true

                            if(isTimeWithinRange(
                                    selectedDate.value.split(" ")[3],
                                    currentBranch?.openingHours ?: "",
                                    currentBranch?.closingHours ?: ""
                            ).not()){
                                message.value = "Reservation time is not within branch opening hours"
                                return@RoundedBarButton
                            }

                            if(isDateInTheFuture(
                                date = selectedDate.value.split(" ").subList(0, 3).joinToString(" ")
                            ).not()){
                                message.value = "Reservation date must be in the future"
                                return@RoundedBarButton
                            }

                            viewModel.bookReservation(ReservationModel(
                                reservationID = UUID.randomUUID().toString(),
                                branchID = branchId ?: "",
                                userID = userId.value,
                                date = selectedDate.value,
                                reservationType = when(selectedType.value){
                                    "Haircuts and styling" -> 1
                                    "Manicure and pedicure" -> 2
                                    "Facial treatments" -> 3
                                    else -> 4
                                }
                            )){
                                message.value = it
                            }
                        }
                    }
                }

                item {
                    Text(text = "Reviews", modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
                }

                if(review.value.isNotEmpty()){
                    items(review.value){ review ->
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = (userList.value.find { it.userID == review.userID }?.fullName ?: ""), fontSize = 14.sp, color = CompfestWhite, fontWeight = FontWeight.SemiBold)
                            Text(text = review.comment + " - (" + review.star + " Stars)", fontSize = 14.sp, color = CompfestWhite)
                            Spacer(modifier = Modifier.height(12.dp))
                            HorizontalDivider()
                        }
                    }
                } else {
                    item {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "There are no reviews yet", color = Color.LightGray, textAlign = TextAlign.Center, modifier = Modifier
                                .fillMaxWidth(), fontSize = 12.sp)
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(160.dp))
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

@RequiresApi(Build.VERSION_CODES.O)
fun isTimeWithinRange(time: String, openingTime: String, closingTime: String): Boolean {
    if (!isValidTimeFormat(openingTime) || !isValidTimeFormat(closingTime)) {
        return false
    }

    val targetTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("(HH:mm)"))
    val opening = LocalTime.parse(openingTime, DateTimeFormatter.ofPattern("HH:mm"))
    val closing = LocalTime.parse(closingTime, DateTimeFormatter.ofPattern("HH:mm")).minusHours(1)

    return targetTime.isAfter(opening) && targetTime.isBefore(closing)
}

@RequiresApi(Build.VERSION_CODES.O)
fun isDateInTheFuture(date: String): Boolean {
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val selectedDate = LocalDate.parse(date, formatter)
        val currentDate = LocalDate.now()
        !selectedDate.isBefore(currentDate)
    } catch (e: DateTimeParseException) {
        false
    }
}
