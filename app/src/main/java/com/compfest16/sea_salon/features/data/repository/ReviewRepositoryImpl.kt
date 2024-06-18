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

class ReviewRepositoryImpl: ReviewRepository {
    private val db = Firebase.firestore
    override suspend fun GetReviews(): Flow<List<ReviewModel>> {
        return flow {
            try {
                val query = db.collection("review").get()
                val result = mutableListOf<ReviewModel>()

                for (document in query.result){
                    result.add(document.toReviewModel())
                }
                Log.d("Review", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(emptyList())
                Log.d("Review", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun GetReviewByID(id: String): Flow<ReviewModel> {
        return flow {
            try {
                val query = db.collection("review").get()
                var result = ReviewDummy.notFound

                for (document in query.result){
                    if (document.id == id){
                        result = document.toReviewModel()
                    }
                }
                Log.d("Review", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(ReviewDummy.notFound)
                Log.d("Review", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun GetReviewByBranchID(id: String): Flow<List<ReviewModel>> {
        return flow {
            try {
                val query = db.collection("review").get()
                val result = mutableListOf<ReviewModel>()

                for (document in query.result){
                    if (document.id == id){
                        result.add(document.toReviewModel())
                    }
                }
                Log.d("Review", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(emptyList())
                Log.d("Review", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun GetReviewByUserID(id: String): Flow<List<ReviewModel>> {
        return flow {
            try {
                val query = db.collection("review").get()
                val result = mutableListOf<ReviewModel>()

                for (document in query.result){
                    if (document.id == id){
                        result.add(document.toReviewModel())
                    }
                }
                Log.d("Review", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(emptyList())
                Log.d("Review", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun PostReview(review: ReviewModel): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("review").document(review.reviewID).set(review.toHashMap()).addOnSuccessListener {
                    messege = "Review Posted Successfully"
                    Log.d("Review", "Review Posted Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("Review", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Review", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun PutReview(review: ReviewModel): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("review").document(review.reviewID).update(review.toHashMap()).addOnSuccessListener {
                    messege = "Review Updated Successfully"
                    Log.d("Review", "Review Updated Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("Review", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Review", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun DeleteReview(id: String): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("review").document(id).delete().addOnSuccessListener {
                    messege = "Review Deleted Successfully"
                    Log.d("Review", "Review Deleted Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("Review", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Review", e.message.toString())
                return@flow
            }
        }
    }
}