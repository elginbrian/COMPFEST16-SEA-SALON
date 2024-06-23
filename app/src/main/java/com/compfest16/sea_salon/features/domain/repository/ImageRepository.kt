package com.compfest16.sea_salon.features.domain.repository

import com.compfest16.sea_salon.features.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository{
    suspend fun GetImageById(affiliateID: String, role: String): Flow<List<ImageModel>>
    suspend fun PostImage(image: ImageModel): Flow<String>
    suspend fun DeleteImage(imageID: String, affiliateID: String, role: String): Flow<String>
}