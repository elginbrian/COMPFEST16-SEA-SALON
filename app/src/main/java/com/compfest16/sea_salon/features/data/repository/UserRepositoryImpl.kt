package com.compfest16.sea_salon.features.data.repository

import android.util.Log
import com.compfest16.sea_salon.features.data.mapper.toHashMap
import com.compfest16.sea_salon.features.data.mapper.toUserModel
import com.compfest16.sea_salon.features.domain.dummy.UserDummy
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl: UserRepository {
    private val db = Firebase.firestore

    override suspend fun GetUsers(): Flow<List<UserModel>> {
        return flow {
            try {
                val query = db.collection("user").get()
                val result = mutableListOf<UserModel>()

                for (document in query.result){
                    result.add(document.toUserModel())
                }
                Log.d("User", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(emptyList())
                Log.d("User", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun GetUserByID(id: String): Flow<UserModel> {
        return flow {
            try {
                val query = db.collection("user").get()
                var result = UserDummy.notFound

                for (document in query.result){
                    if(document.id == id){
                        result = document.toUserModel()
                    }
                }
                Log.d("User", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(UserDummy.notFound)
                Log.d("User", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun PostUser(user: UserModel): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("user").document(user.userID).set(user.toHashMap()).addOnSuccessListener {
                    messege = "User Registered Successfully"
                    Log.d("User", "User Registered Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("User", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("User", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun PutUser(user: UserModel): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("user").document(user.userID).update(user.toHashMap()).addOnSuccessListener {
                    messege = "User Updated Successfully"
                    Log.d("User", "User Updated Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("User", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("User", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun DeleteUser(id: String): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("user").document(id).delete().addOnSuccessListener {
                    messege = "User Deleted Successfully"
                    Log.d("User", "User Deleted Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("User", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("User", e.message.toString())
                return@flow
            }
        }
    }
}