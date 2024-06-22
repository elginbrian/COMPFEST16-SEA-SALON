package com.compfest16.sea_salon.features.presentation.component.widget

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
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestBlueGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun BranchCard(
    branchModel: BranchModel = BranchDummy.malang,
    onClick: (String) -> Unit = {}
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(80.dp),
        colors = CardDefaults.cardColors(CompfestBlueGrey),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize().clickable {
            onClick(branchModel.branchID)
        }){
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                Card(modifier = Modifier.size(80.dp),
                    colors = CardDefaults.cardColors(CompfestGrey),
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                ){

                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    Text(text = branchModel.branchName, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium, lineHeight = 28.sp)
                    Text(text = branchModel.branchAddress, color = Color.LightGray, fontSize = 12.sp)
                }
            }

            Row(modifier = Modifier.fillMaxSize().padding(end = 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End){
                androidx.compose.material3.Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = CompfestPink, modifier = Modifier.size(24.dp))
            }
        }
    }
}