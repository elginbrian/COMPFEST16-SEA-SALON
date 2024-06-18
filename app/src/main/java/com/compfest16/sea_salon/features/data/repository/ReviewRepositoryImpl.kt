package com.compfest16.sea_salon.features.data.repository

import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.compfest16.sea_salon.features.domain.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow

class ReviewRepositoryImpl: ReviewRepository {
    override suspend fun GetReviews(): Flow<List<ReviewModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun GetReviewByID(id: String): Flow<ReviewModel> {
        TODO("Not yet implemented")
    }

    override suspend fun GetReviewByBranchID(id: String): Flow<List<ReviewModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun GetReviewByUserID(id: String): Flow<List<ReviewModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun PostReview(review: ReviewModel): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun PutReview(review: ReviewModel): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun DeleteReview(id: String): Flow<String> {
        TODO("Not yet implemented")
    }
}