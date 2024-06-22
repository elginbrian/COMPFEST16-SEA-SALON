package com.compfest16.sea_salon.features.presentation.component.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite

@Composable
@Preview
fun CityCard(
    type: Int = 0,
    onClick: (Int) -> Unit = {}
){
    val cityList = listOf(
        Pair("Malang", R.drawable.malang),
        Pair("Surabaya", R.drawable.surabaya),
        Pair("Jakarta", R.drawable.jakarta),
        Pair("Other", R.drawable.other)
    )
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(CompfestBlueGrey)
    ) {
        Box(modifier = Modifier.fillMaxSize().clickable {
            onClick(type)
        }){
            Image(painter = painterResource(id = cityList[type].second), contentDescription = cityList[type].first,
                modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(text = cityList[type].first, color = CompfestWhite, fontSize = 24.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}