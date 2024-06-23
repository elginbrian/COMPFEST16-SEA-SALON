package com.compfest16.sea_salon.features.data.repository

import android.util.Log
import com.compfest16.sea_salon.features.data.mapper.toHashMap
import com.compfest16.sea_salon.features.data.mapper.toReviewModel
import com.compfest16.sea_salon.features.data.mapper.toUserModel
import com.compfest16.sea_salon.features.domain.dummy.ReviewDummy
import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.ReviewRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ReviewRepositoryImpl: ReviewRepository {
    private val db = Firebase.firestore
    override suspend fun GetReviews(): Flow<List<ReviewModel>> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<List<ReviewModel>> { continuation ->
                    db.collection("review")
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            try {
                                val reviewList = mutableListOf<ReviewModel>()
                                for (document in querySnapshot.documents) {
                                    reviewList.add(document.toReviewModel())
                                }
                                Log.d("Review", "Fetched ${reviewList.size} reviews")
                                continuation.resume(reviewList)
                            } catch (e: Exception) {
                                Log.e("Review", "Error parsing documents: ${e.message}", e)
                                continuation.resume(emptyList())
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Review", "Error fetching reviews: ${exception.message}", exception)
                            continuation.resume(emptyList())
                        }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("Review", "Exception in GetReviews flow: ${e.message}", e)
                emit(emptyList())
            }
        }
    }


    override suspend fun GetReviewByID(id: String): Flow<ReviewModel> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<ReviewModel> { continuation ->
                    db.collection("review").document(id)
                        .get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                val review = documentSnapshot.toReviewModel()
                                Log.d("Review", "Fetched review: $review")
                                continuation.resume(review)
                            } else {
                                Log.d("Review", "Review not found for ID: $id")
                                continuation.resume(ReviewDummy.notFound)
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Review", "Error fetching review: ${exception.message}", exception)
                            continuation.resume(ReviewDummy.notFound)
                        }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("Review", "Exception in GetReviewByID flow: ${e.message}", e)
                emit(ReviewDummy.notFound)
            }
        }
    }


    override suspend fun GetReviewByBranchID(id: String): Flow<List<ReviewModel>> {
        return flow {
            try {
                val result = mutableListOf<ReviewModel>()
                val querySnapshot = db.collection("review")
                    .whereEqualTo("branch_id", id)
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    result.add(document.toReviewModel())
                }

                Log.d("Review", result.toString())
                emit(result)
            } catch (e: Exception) {
                Log.e("Review", "Error fetching reviews by branch ID: ${e.message}", e)
                emit(emptyList())
            }
        }
    }


    override suspend fun GetReviewByUserID(id: String): Flow<List<ReviewModel>> {
        return flow {
            try {
                val result = mutableListOf<ReviewModel>()
                val querySnapshot = db.collection("review")
                    .whereEqualTo("user_id", id)
                    .get()
                    .await()

                for (document in querySnapshot.documents) {
                    result.add(document.toReviewModel())
                }

                Log.d("Review", result.toString())
                emit(result)
            } catch (e: Exception) {
                Log.e("Review", "Error fetching reviews by user ID: ${e.message}", e)
                emit(emptyList())
            }
        }
    }


    override suspend fun PostReview(review: ReviewModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("review").document(review.reviewID)
                        .set(review.toHashMap())
                        .addOnSuccessListener {
                            Log.d("Review", "Review Posted Successfully")
                            continuation.resume("Review Posted Successfully")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Review", "Error posting review: ${exception.message}", exception)
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                Log.e("Review", "Exception in PostReview flow: ${e.message}", e)
                emit(e.message.toString())
            }
        }
    }


    override suspend fun PutReview(review: ReviewModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("review").document(review.reviewID)
                        .update(review.toHashMap())
                        .addOnSuccessListener {
                            Log.d("Review", "Review Updated Successfully")
                            continuation.resume("Review Updated Successfully")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Review", "Error updating review: ${exception.message}", exception)
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                Log.e("Review", "Exception in PutReview flow: ${e.message}", e)
                emit(e.message.toString())
            }
        }
    }


    override suspend fun DeleteReview(id: String): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("review").document(id)
                        .delete()
                        .addOnSuccessListener {
                            Log.d("Review", "Review Deleted Successfully")
                            continuation.resume("Review Deleted Successfully")
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Review", "Error deleting review: ${exception.message}", exception)
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                Log.e("Review", "Exception in DeleteReview flow: ${e.message}", e)
                emit(e.message.toString())
            }
        }
    }

}