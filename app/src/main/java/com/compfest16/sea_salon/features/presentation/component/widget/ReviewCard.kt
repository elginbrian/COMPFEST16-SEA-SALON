package com.compfest16.sea_salon.features.presentation.component.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.dummy.ReviewDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite

@Composable
@Preview
fun ReviewCard(
    reviewModel: ReviewModel = ReviewDummy.ramaMalang,
    branchList: List<BranchModel> = BranchDummy.list,
){
    val thisBranch = branchList.find { it.branchID == reviewModel.branchID } ?: BranchDummy.list.first()

    Card(modifier = Modifier.size(160.dp),
        shape = RoundedCornerShape(bottomEnd = 24.dp),
        colors = CardDefaults.cardColors(listOf(CompfestPurple, CompfestAqua, CompfestPink).random())
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 8.dp)) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "close", tint = CompfestWhite, modifier = Modifier.size(24.dp))
                Text(text = thisBranch.branchName, color = CompfestWhite, modifier = Modifier.padding(top = 1.dp, start = 2.dp), fontSize = 12.sp, maxLines = 1)
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp), verticalArrangement = Arrangement.Center) {
                Text(text = reviewModel.comment, color = CompfestWhite, maxLines = 4, lineHeight = 16.sp, fontSize = 12.sp)
            }
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = "${reviewModel.star} stars", color = CompfestWhite, fontSize = 10.sp)
            }
        }
    }
}