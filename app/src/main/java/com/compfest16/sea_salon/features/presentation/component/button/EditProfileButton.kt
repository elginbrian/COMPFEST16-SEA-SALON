package com.compfest16.sea_salon.features.presentation.component.button

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple

@Preview
@Composable
fun EditProfileButton(
    color: Color = CompfestPurple
){
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri.value = uri
        }
    )

    Card(modifier = Modifier
        .size(100.dp),
        colors = CardDefaults.cardColors(color),
        border = BorderStroke(2.dp, color),
        shape = CircleShape
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }, contentAlignment = Alignment.Center){
            AsyncImage(model = selectedImageUri, contentDescription = "profile", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Icon(imageVector = Icons.Default.Add, contentDescription = "profile", tint = Color.White, modifier = Modifier.size(40.dp))
        }
    }
}