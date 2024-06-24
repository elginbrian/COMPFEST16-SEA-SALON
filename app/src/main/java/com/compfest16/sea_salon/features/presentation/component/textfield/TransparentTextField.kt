package com.compfest16.sea_salon.features.presentation.component.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compfest16.sea_salon.features.presentation.design_system.CompfestWhite
import java.lang.reflect.Modifier

@Composable
fun TransparentTextField(
    text: String = "",
    hint: String = "",
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    fontSize: TextUnit = 14.sp,
    onFocusChange: (FocusState) -> Unit
) {
    Box(modifier = androidx.compose.ui.Modifier.fillMaxWidth()) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = TextStyle(color = Color.White, fontSize = fontSize),
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) }
        )
        if (isHintVisible && text.isEmpty()) {
            Text(
                text = hint,
                style = TextStyle(color = Color.White.copy(alpha = 0.5f)),
                fontSize = fontSize,
                modifier = androidx.compose.ui.Modifier.padding(start = 4.dp, top = 8.dp)
            )
        }
    }
}