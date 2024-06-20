package com.compfest16.sea_salon.features.presentation.screen.home_section

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.dummy.ReservationDummy
import com.compfest16.sea_salon.features.domain.dummy.ReviewDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.presentation.component.widget.BranchCard
import com.compfest16.sea_salon.features.presentation.component.widget.HistoryCard
import com.compfest16.sea_salon.features.presentation.component.widget.ReviewCard
import com.compfest16.sea_salon.features.presentation.component.widget.TopBar
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlack
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(name = "Pixel 3A", device = Devices.PIXEL_3A)
fun Home(){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 42.dp)){
                TopBar()
            }
        },
        contentColor = CompfestBlack,
        containerColor = CompfestBlack
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                    colors = CardDefaults.cardColors(CompfestBlueGrey),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(painter = painterResource(id = R.drawable.home_hero), contentDescription = "hero", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Bottom) {
                            Text(text = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE), color = CompfestWhite)
                        }
                    }
                }
            }

            item {
                Text(text = "History", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            item {
                LazyRow(modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)){
                    item{
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    items(ReservationDummy.list){
                        HistoryCard(
                            reservationModel = ReservationDummy.list.random(),
                            branchModel = BranchDummy.list.random()
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            item {
                Text(text = "SEA Salon™ Near You", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            items(BranchDummy.list.subList(0, 3)){
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)) {
                    BranchCard(branchModel = it)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Text(text = "Your Reviews", modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 8.dp), color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            }

            item {
                LazyRow(modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)){
                    item{
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                    items(ReviewDummy.list){
                        ReviewCard(reviewModel = it, branchModel = BranchDummy.list.random())
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(64.dp))
            }
        }
    }
}