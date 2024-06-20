package com.compfest16.sea_salon.features.presentation.component.widget

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.screen.home_section.HomeViewModel

@Composable
@Preview
fun HistoryCard(
    reservationModel: ReservationModel = ReservationDummy.ramaMalang,
    branchModel: BranchModel = BranchDummy.malang
){
    Card(
        modifier = Modifier
            .height(160.dp)
            .width(190.dp),
        colors = CardDefaults.cardColors(CompfestBlueGrey),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            AsyncImage(model = "", contentDescription = "branch-photo", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

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
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = reservationModel.getReservationType(), color = Color.White, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}