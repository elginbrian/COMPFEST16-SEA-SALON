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
                                    continuation.resume("SignUp Success, adding user to Firestore...")
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
                            continuation.resume("Login Success, redirecting to homepage...")
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
                val result = suspendCancellableCoroutine<MutableList<UserModel>> { continuation ->
                    db.collection("user").get()
                        .addOnSuccessListener { querySnapshot ->
                            try {
                                val userList = mutableListOf<UserModel>()
                                for (document in querySnapshot) {
                                    userList.add(document.toUserModel())
                                }
                                Log.d("User", userList.toString())
                                continuation.resume(userList)
                            } catch (e: Exception) {
                                Log.e("User", "Error parsing documents: ${e.message}", e)
                                continuation.resume(mutableListOf())
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("User", "Error fetching users: ${exception.message}", exception)
                            continuation.resume(mutableListOf())
                        }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("User", "Exception in GetUsers flow: ${e.message}", e)
                emit(emptyList())
            }
        }
    }


    override suspend fun GetUserByID(id: String): Flow<UserModel> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<UserModel> { continuation ->
                    db.collection("user").document(id).get()
                        .addOnSuccessListener { documentSnapshot ->
                            try {
                                val userModel = if (documentSnapshot.exists()) {
                                    documentSnapshot.toUserModel()
                                } else {
                                    UserDummy.notFound
                                }
                                Log.d("User", userModel.toString())
                                continuation.resume(userModel)
                            } catch (e: Exception) {
                                Log.e("User", "Error parsing document: ${e.message}", e)
                                continuation.resume(UserDummy.notFound)
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("User", "Error fetching user: ${exception.message}", exception)
                            continuation.resume(UserDummy.notFound)
                        }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("User", "Exception in GetUserByID flow: ${e.message}", e)
                emit(UserDummy.notFound)
            }
        }
    }

    override suspend fun GetUserByEmail(email: String): Flow<UserModel> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<UserModel> { continuation ->
                    db.collection("user")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            try {
                                if (querySnapshot.documents.isNotEmpty()) {
                                    val documentSnapshot = querySnapshot.documents.first()
                                    val userModel = documentSnapshot.toUserModel()
                                    Log.d("User", "User found: $userModel")
                                    continuation.resume(userModel)
                                } else {
                                    Log.d("User", "User not found for email: $email")
                                    continuation.resume(UserDummy.notFound)
                                }
                            } catch (e: Exception) {
                                Log.e("User", "Error parsing document: ${e.message}", e)
                                continuation.resume(UserDummy.notFound)
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("User", "Error fetching user: ${exception.message}", exception)
                            continuation.resume(UserDummy.notFound)
                        }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("User", "Exception in GetUserByEmail flow: ${e.message}", e)
                emit(UserDummy.notFound)
            }
        }
    }



    override suspend fun PostUser(user: UserModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("user").document(user.userID).set(user.toHashMap())
                        .addOnSuccessListener {
                            val successMessage = "User Added to Firestore, uploading image..."
                            Log.d("User", successMessage)
                            continuation.resume(successMessage)
                        }
                        .addOnFailureListener { exception ->
                            val errorMessage = exception.message.toString()
                            Log.e("User", errorMessage, exception)
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                val errorMessage = e.message.toString()
                Log.e("User", errorMessage, e)
                emit(errorMessage)
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