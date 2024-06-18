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

class BranchRepositoryImpl: BranchRepository {
    private val db = Firebase.firestore

    override suspend fun GetBranches(): Flow<List<BranchModel>> {
        return flow {
            try {
                val query = db.collection("branch").get()
                val result = mutableListOf<BranchModel>()

                for (document in query.result){
                    result.add(document.toBranchModel())
                }
                Log.d("Branch", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(emptyList())
                Log.d("Branch", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun GetBranchByID(id: String): Flow<BranchModel> {
        return flow {
            try {
                val query = db.collection("branch").get()
                var result = BranchDummy.notFound

                for (document in query.result){
                    if (document.id == id){
                        result = document.toBranchModel()
                    }
                }
                Log.d("Branch", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(BranchDummy.notFound)
                Log.d("Branch", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun PostBranch(branch: BranchModel): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("branch").document(branch.branchID).set(branch.toHashMap()).addOnSuccessListener {
                    messege = "Branch Registered Successfully"
                    Log.d("Branch", "Branch Registered Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("Branch", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Branch", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun PutBranch(branch: BranchModel): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("branch").document(branch.branchID).update(branch.toHashMap()).addOnSuccessListener {
                    messege = "Branch Updated Successfully"
                    Log.d("Branch", "Branch Updated Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("Branch", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Branch", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun DeleteBranch(id: String): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("branch").document(id).delete().addOnSuccessListener {
                    messege = "Branch Deleted Successfully"
                    Log.d("Branch", "Branch Deleted Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("Branch", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Branch", e.message.toString())
                return@flow
            }
        }
    }
}