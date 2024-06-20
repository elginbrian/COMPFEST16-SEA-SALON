package com.compfest16.sea_salon.features.data.repository

import android.util.Log
import com.compfest16.sea_salon.features.data.mapper.toHashMap
import com.compfest16.sea_salon.features.data.mapper.toUserModel
import com.compfest16.sea_salon.features.domain.dummy.UserDummy
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserRepositoryImpl : UserRepository {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    override suspend fun SignUpUser(user: UserModel, image: ImageModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine { continuation ->
                    auth.createUserWithEmailAndPassword(user.email, user.password)
                        .addOnSuccessListener {
                            val updateProfile = userProfileChangeRequest {
                                displayName = user.fullName
                                photoUri = image.src
                            }
                            auth.currentUser?.updateProfile(updateProfile)?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("SignUp", "SignUp Success")
                                    continuation.resume("SignUp Success")
                                } else {
                                    val errorMsg = task.exception?.message.toString()
                                    Log.d("SignUp", errorMsg)
                                    continuation.resumeWithException(Exception(errorMsg))
                                }
                            }
                        }
                        .addOnFailureListener {
                            val errorMsg = it.message.toString()
                            Log.d("SignUp", errorMsg)
                            continuation.resumeWithException(Exception(errorMsg))
                        }
                }
                emit(message)
            } catch (e: Exception) {
                emit(e.message.toString())
            }
        }
    }


    override suspend fun LoginUser(email: String, password: String): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener {
                            Log.d("Login", "Login Success")
                            continuation.resume("Login Success")
                        }
                        .addOnFailureListener { exception ->
                            Log.d("Login", exception.message.toString())
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                Log.d("Login", e.message.toString())
                emit(e.message.toString())
            }
        }
    }


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
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("user").document(user.userID).set(user.toHashMap())
                        .addOnSuccessListener {
                            Log.d("User", "User Registered Successfully")
                            continuation.resume("User Registered Successfully")
                        }
                        .addOnFailureListener { exception ->
                            Log.d("User", exception.message.toString())
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                Log.d("User", e.message.toString())
                emit(e.message.toString())
            }
        }
    }


    override suspend fun PutUser(user: UserModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("user").document(user.userID).update(user.toHashMap())
                        .addOnSuccessListener {
                            Log.d("User", "User Updated Successfully")
                            continuation.resume("User Updated Successfully")
                        }
                        .addOnFailureListener { exception ->
                            Log.d("User", exception.message.toString())
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                Log.d("User", e.message.toString())
                emit(e.message.toString())
            }
        }
    }


    override suspend fun DeleteUser(id: String): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("user").document(id).delete()
                        .addOnSuccessListener {
                            Log.d("User", "User Deleted Successfully")
                            continuation.resume("User Deleted Successfully")
                        }
                        .addOnFailureListener { exception ->
                            Log.d("User", exception.message.toString())
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                Log.d("User", e.message.toString())
                emit(e.message.toString())
            }
        }
    }

}