package com.compfest16.sea_salon.features.presentation.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite

@Composable
@Preview
fun StarButton(onRatingChanged: (Int) -> Unit = {}) {
    val selectedRating = remember { mutableStateOf(0) }

    fun selectStar(index: Int) {
        selectedRating.value = index + 1
        onRatingChanged(selectedRating.value)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Rating:",
            fontSize = 12.sp,
            color = CompfestWhite
        )
        for (index in 0..4) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "star ${index + 1}",
                modifier = Modifier
                    .size(28.dp)
                    .clickable { selectStar(index) },
                tint = if (index < selectedRating.value) CompfestAqua else CompfestLightGrey
            )
        }
    }
}

