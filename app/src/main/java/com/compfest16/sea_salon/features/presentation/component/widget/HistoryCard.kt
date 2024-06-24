package com.compfest16.sea_salon.features.presentation.component.widget

import android.net.Uri
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.compfest16.sea_salon.features.data.repository.UserRepositoryImpl
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.dummy.ReservationDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.screen.home_section.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
@Preview
fun HistoryCard(
    reservationModel: ReservationModel = ReservationDummy.ramaMalang,
    branchModel: BranchModel = BranchDummy.malang,
    onCardClick: (Pair<BranchModel, ReservationModel>) -> Unit = {}
){
    val viewModel = getViewModel<HomeViewModel>()
    val image = remember { mutableStateOf(Uri.EMPTY) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit){
        viewModel.getImageByAffiliate(branchModel.branchID, "branch_image"){
            image.value = it.firstOrNull()?.src ?: Uri.EMPTY
            isLoading.value = false
        }
    }

    Card(
        modifier = Modifier
            .height(160.dp)
            .width(180.dp),
        colors = CardDefaults.cardColors(CompfestBlueGrey),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().clickable { onCardClick(Pair(branchModel, reservationModel)) }) {
            if (isLoading.value) {
                val infiniteTransition = rememberInfiniteTransition()
                val alpha by infiniteTransition.animateFloat(
                    initialValue = 0.3f,
                    targetValue = 1.0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CompfestLightGrey.copy(alpha = alpha)),
                    contentAlignment = Alignment.Center
                ) {
                }
            } else {
                AsyncImage(model = image.value, contentDescription = "branch-photo", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.Start) {
                Text(text = branchModel.branchName, color = Color.White, modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = reservationModel.date, color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                    colors = CardDefaults.cardColors(reservationModel.getHistoryColor())
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = reservationModel.getReservationType(), color = Color.White, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}
