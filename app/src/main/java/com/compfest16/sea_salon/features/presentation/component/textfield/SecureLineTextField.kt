package com.compfest16.sea_salon.features.presentation.component.textfield

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.BasicSecureTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compfest16.sea_salon.R
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun SecureLineTextField(
    title: String = "Title",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    painter: Painter = painterResource(id = R.drawable.arrow_right),
){
    val hide = remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(4.dp))
    Box(modifier = Modifier.fillMaxWidth().height(48.dp).clickable {
        hide.value = true
    }, contentAlignment = Alignment.CenterStart){
        BasicSecureTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CompfestWhite, RoundedCornerShape(50.dp))
                .background(Color.Transparent)
                .padding(12.dp)
                .padding(start = 32.dp).clickable {
                    hide.value = true
                },
            textStyle = TextStyle(color = CompfestWhite),
            cursorBrush = SolidColor(CompfestWhite),
            keyboardType = KeyboardType.Password
        )
        Icon(painter, contentDescription = "leading icon", tint = CompfestWhite, modifier = Modifier.padding(start = 16.dp).size(16.dp).clickable {
            hide.value = true
        })
        if(!hide.value){
            Text(text = title, style = TextStyle(color = CompfestWhite), modifier = Modifier.padding(start = 42.dp).clickable { hide.value = true })
        }
    }
}