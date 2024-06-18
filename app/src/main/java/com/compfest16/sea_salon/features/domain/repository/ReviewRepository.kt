package com.compfest16.sea_salon.features.domain.repository

import com.compfest16.sea_salon.features.domain.model.ReviewModel
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun GetReviews(): Flow<List<ReviewModel>>
    suspend fun GetReviewByID(id: String): Flow<ReviewModel>
    suspend fun GetReviewByBranchID(id: String): Flow<List<ReviewModel>>
    suspend fun GetReviewByUserID(id: String): Flow<List<ReviewModel>>
    suspend fun PostReview(review: ReviewModel): Flow<String>
    suspend fun PutReview(review: ReviewModel): Flow<String>
    suspend fun DeleteReview(id: String): Flow<String>
}