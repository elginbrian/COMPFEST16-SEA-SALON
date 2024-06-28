package com.compfest16.sea_salon.features.presentation.component.widget

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import com.compfest16.sea_salon.features.presentation.screen.home_section.HomeViewModel
import com.compfest16.sea_salon.features.presentation.screen.reservation_section.isTimeWithinRange
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun BranchCard(
    branchModel: BranchModel = BranchDummy.malang,
    showImage: Boolean = true,
    isShowStreetView: MutableState<Boolean> = mutableStateOf(false),
    onClick: (String) -> Unit = {}
){
    val viewModel = getViewModel<HomeViewModel>()
    val image     = remember { mutableStateOf(Uri.EMPTY) }
    val isLoading = remember { mutableStateOf(true) }
    val isOpen    = remember {
        mutableStateOf(isTimeWithinRange(
            time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("(HH:mm)")),
            openingTime = branchModel.openingHours,
            closingTime = branchModel.closingHours
        ))
    }

    LaunchedEffect(Unit){
        viewModel.getImageByAffiliate(branchModel.branchID, "branch_image"){
            image.value = it.firstOrNull()?.src ?: Uri.EMPTY
            isLoading.value = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(CompfestBlueGrey),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick(branchModel.branchID)
            }){
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                if (showImage){
                    Card(
                        modifier = Modifier.size(80.dp),
                        colors = CardDefaults.cardColors(CompfestGrey),
                        shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                    ) {
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
                            AsyncImage(
                                model = image.value,
                                contentDescription = "branch-image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                } else {
                    Box(modifier = Modifier.size(80.dp).clickable{
                       isShowStreetView.value = !isShowStreetView.value
                    }, contentAlignment = Alignment.Center){
                        Image(painter = painterResource(id = R.drawable.streetview), contentDescription = "steetview", modifier = Modifier.size(40.dp))
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    Text(text = branchModel.branchName, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium, lineHeight = 28.sp)
                    Text(text = branchModel.branchAddress, color = Color.LightGray, fontSize = 12.sp)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                if(isOpen.value){
                    Text(text = branchModel.openingHours, color = CompfestWhite, fontSize = 12.sp)
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(0.1f), color = CompfestLightGrey)
                    Text(text = branchModel.closingHours, color = CompfestWhite, fontSize = 12.sp)
                } else {
                    Text(text = "Closed", color = Color(0xFFEC4444), fontSize = 12.sp)
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(0.1f), color = CompfestLightGrey)
                    Text(text = "Open: " + branchModel.openingHours, color = CompfestWhite, fontSize = 12.sp)
                }
            }
        }
    }
}
