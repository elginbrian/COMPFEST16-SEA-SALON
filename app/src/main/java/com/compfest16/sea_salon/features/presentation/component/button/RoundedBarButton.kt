package com.compfest16.sea_salon.features.presentation.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite

@Composable
@Preview
fun RoundedBarButton(
    text: String = "Button",
    color: androidx.compose.ui.graphics.Color = CompfestPurple,
    onClick: () -> Unit = {},
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(50.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = color
        ),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            androidx.compose.material3.Text(text = text, color = CompfestWhite)
        }
    }
}