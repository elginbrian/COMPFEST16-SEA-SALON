package com.compfest16.sea_salon.features.data.repository

import android.util.Log
import com.compfest16.sea_salon.features.data.mapper.toBranchModel
import com.compfest16.sea_salon.features.data.mapper.toHashMap
import com.compfest16.sea_salon.features.data.mapper.toUserModel
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume

class BranchRepositoryImpl: BranchRepository {
    private val db = Firebase.firestore

    override suspend fun GetBranches(): Flow<List<BranchModel>> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<MutableList<BranchModel>> { continuation ->
                    db.collection("branch").get().addOnSuccessListener { querySnapshot ->
                        try {
                            Log.d("Branch", "Successfully fetched branches: ${querySnapshot.documents.size} documents")
                            val innerResult = mutableListOf<BranchModel>()
                            for (document in querySnapshot.documents) {
                                innerResult.add(document.toBranchModel())
                            }
                            Log.d("Branch", "Parsed branch models: $innerResult")
                            continuation.resume(innerResult)
                        } catch (e: Exception) {
                            Log.e("Branch", "Error parsing documents: ${e.message}", e)
                            continuation.resume(mutableListOf())
                        }
                    }.addOnFailureListener { exception ->
                        Log.e("Branch", "Error fetching branches: ${exception.message}", exception)
                        continuation.resume(mutableListOf())
                    }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("Branch", "Exception in GetBranches flow: ${e.message}", e)
                emit(listOf())
            }
        }
    }

    override suspend fun GetBranchByID(id: String): Flow<BranchModel> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<BranchModel> { continuation ->
                    db.collection("branch").document(id).get()
                        .addOnSuccessListener { documentSnapshot ->
                            try {
                                val branchModel = if (documentSnapshot.exists()) {
                                    documentSnapshot.toBranchModel()
                                } else {
                                    BranchDummy.notFound
                                }
                                Log.d("Branch", "Successfully fetched branch: $branchModel")
                                continuation.resume(branchModel)
                            } catch (e: Exception) {
                                Log.e("Branch", "Error parsing document: ${e.message}", e)
                                continuation.resume(BranchDummy.notFound)
                            }
                        }.addOnFailureListener { exception ->
                            Log.e("Branch", "Error fetching branch: ${exception.message}", exception)
                            continuation.resume(BranchDummy.notFound)
                        }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("Branch", "Exception in GetBranchByID flow: ${e.message}", e)
                emit(BranchDummy.notFound)
            }
        }
    }


    override suspend fun PostBranch(branch: BranchModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("branch").document(branch.branchID).set(branch.toHashMap())
                        .addOnSuccessListener {
                            val successMessage = "Branch Registered Successfully"
                            Log.d("Branch", successMessage)
                            continuation.resume(successMessage)
                        }
                        .addOnFailureListener { exception ->
                            val errorMessage = exception.message.toString()
                            Log.e("Branch", errorMessage, exception)
                            continuation.resume(errorMessage)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                val errorMessage = e.message.toString()
                Log.e("Branch", errorMessage, e)
                emit(errorMessage)
            }
        }
    }


    override suspend fun PutBranch(branch: BranchModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("branch").document(branch.branchID).update(branch.toHashMap())
                        .addOnSuccessListener {
                            val successMessage = "Branch Updated Successfully"
                            Log.d("Branch", successMessage)
                            continuation.resume(successMessage)
                        }
                        .addOnFailureListener { exception ->
                            val errorMessage = exception.message.toString()
                            Log.e("Branch", errorMessage, exception)
                            continuation.resume(errorMessage)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                val errorMessage = e.message.toString()
                Log.e("Branch", errorMessage, e)
                emit(errorMessage)
            }
        }
    }


    override suspend fun DeleteBranch(id: String): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("branch").document(id).delete()
                        .addOnSuccessListener {
                            val successMessage = "Branch Deleted Successfully"
                            Log.d("Branch", successMessage)
                            continuation.resume(successMessage)
                        }
                        .addOnFailureListener { exception ->
                            val errorMessage = exception.message.toString()
                            Log.e("Branch", errorMessage, exception)
                            continuation.resume(errorMessage)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                val errorMessage = e.message.toString()
                Log.e("Branch", errorMessage, e)
                emit(errorMessage)
            }
        }
    }

}