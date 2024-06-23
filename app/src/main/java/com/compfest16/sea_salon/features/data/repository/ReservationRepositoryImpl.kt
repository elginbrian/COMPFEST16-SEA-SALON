package com.compfest16.sea_salon.features.data.repository

import android.util.Log
import com.compfest16.sea_salon.features.data.mapper.toHashMap
import com.compfest16.sea_salon.features.data.mapper.toReservationModel
import com.compfest16.sea_salon.features.data.mapper.toReviewModel
import com.compfest16.sea_salon.features.domain.dummy.ReservationDummy
import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.compfest16.sea_salon.features.domain.repository.ReservationRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class ReservationRepositoryImpl: ReservationRepository {
    private val db = Firebase.firestore
    override suspend fun GetReservations(): Flow<List<ReservationModel>> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<MutableList<ReservationModel>> { continuation ->
                    db.collection("reservation").get()
                        .addOnSuccessListener { querySnapshot ->
                            try {
                                val reservationList = mutableListOf<ReservationModel>()
                                for (document in querySnapshot.documents) {
                                    reservationList.add(document.toReservationModel())
                                }
                                Log.d("Reservation", reservationList.toString())
                                continuation.resume(reservationList)
                            } catch (e: Exception) {
                                Log.e("Reservation", "Error parsing documents: ${e.message}", e)
                                continuation.resume(mutableListOf())
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Reservation", "Error fetching reservations: ${exception.message}", exception)
                            continuation.resume(mutableListOf())
                        }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("Reservation", "Exception in GetReservations flow: ${e.message}", e)
                emit(emptyList())
            }
        }
    }


    override suspend fun GetReservationByID(id: String): Flow<ReservationModel> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<ReservationModel?> { continuation ->
                    db.collection("reservation").document(id).get()
                        .addOnSuccessListener { documentSnapshot ->
                            try {
                                val reservationModel = if (documentSnapshot.exists()) {
                                    documentSnapshot.toReservationModel()
                                } else {
                                    ReservationDummy.notFound
                                }
                                Log.d("Reservation", reservationModel.toString())
                                continuation.resume(reservationModel)
                            } catch (e: Exception) {
                                Log.e("Reservation", "Error parsing document: ${e.message}", e)
                                continuation.resume(ReservationDummy.notFound)
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Reservation", "Error fetching reservation: ${exception.message}", exception)
                            continuation.resume(ReservationDummy.notFound)
                        }
                }
                emit(result ?: ReservationDummy.notFound)
            } catch (e: Exception) {
                Log.e("Reservation", "Exception in GetReservationByID flow: ${e.message}", e)
                emit(ReservationDummy.notFound)
            }
        }
    }


    override suspend fun GetReservationByUserID(id: String): Flow<List<ReservationModel>> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<List<ReservationModel>> { continuation ->
                    db.collection("reservation")
                        .whereEqualTo("user_id", id)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            try {
                                val reservationList = mutableListOf<ReservationModel>()
                                for (document in querySnapshot.documents) {
                                    reservationList.add(document.toReservationModel())
                                }
                                Log.d("Reservation", reservationList.toString())
                                continuation.resume(reservationList)
                            } catch (e: Exception) {
                                Log.e("Reservation", "Error parsing documents: ${e.message}", e)
                                continuation.resume(emptyList())
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Reservation", "Error fetching reservations: ${exception.message}", exception)
                            continuation.resume(emptyList())
                        }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("Reservation", "Exception in GetReservationByUserID flow: ${e.message}", e)
                emit(emptyList())
            }
        }
    }


    override suspend fun GetReservationByBranchID(id: String): Flow<List<ReservationModel>> {
        return flow {
            try {
                val result = suspendCancellableCoroutine<List<ReservationModel>> { continuation ->
                    db.collection("reservation")
                        .whereEqualTo("branch_id", id)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            try {
                                val reservationList = mutableListOf<ReservationModel>()
                                for (document in querySnapshot.documents) {
                                    reservationList.add(document.toReservationModel())
                                }
                                Log.d("Reservation", reservationList.toString())
                                continuation.resume(reservationList)
                            } catch (e: Exception) {
                                Log.e("Reservation", "Error parsing documents: ${e.message}", e)
                                continuation.resume(emptyList())
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Reservation", "Error fetching reservations: ${exception.message}", exception)
                            continuation.resume(emptyList())
                        }
                }
                emit(result)
            } catch (e: Exception) {
                Log.e("Reservation", "Exception in GetReservationByBranchID flow: ${e.message}", e)
                emit(emptyList())
            }
        }
    }


    override suspend fun PostReservation(reservation: ReservationModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("reservation").document(reservation.reservationID)
                        .set(reservation.toHashMap())
                        .addOnSuccessListener {
                            val successMessage = "Reservation Posted Successfully"
                            Log.d("Reservation", successMessage)
                            continuation.resume(successMessage)
                        }
                        .addOnFailureListener { exception ->
                            val errorMessage = "Error posting reservation: ${exception.message}"
                            Log.e("Reservation", errorMessage, exception)
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                val errorMessage = "Exception in PostReservation flow: ${e.message}"
                Log.e("Reservation", errorMessage, e)
                emit(errorMessage)
            }
        }
    }


    override suspend fun PutReservation(reservation: ReservationModel): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("reservation").document(reservation.reservationID)
                        .update(reservation.toHashMap())
                        .addOnSuccessListener {
                            val successMessage = "Reservation Updated Successfully"
                            Log.d("Reservation", successMessage)
                            continuation.resume(successMessage)
                        }
                        .addOnFailureListener { exception ->
                            val errorMessage = "Error updating reservation: ${exception.message}"
                            Log.e("Reservation", errorMessage, exception)
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                val errorMessage = "Exception in PutReservation flow: ${e.message}"
                Log.e("Reservation", errorMessage, e)
                emit(errorMessage)
            }
        }
    }


    override suspend fun DeleteReservation(id: String): Flow<String> {
        return flow {
            try {
                val message = suspendCancellableCoroutine<String> { continuation ->
                    db.collection("reservation").document(id)
                        .delete()
                        .addOnSuccessListener {
                            val successMessage = "Reservation Deleted Successfully"
                            Log.d("Reservation", successMessage)
                            continuation.resume(successMessage)
                        }
                        .addOnFailureListener { exception ->
                            val errorMessage = "Error deleting reservation: ${exception.message}"
                            Log.e("Reservation", errorMessage, exception)
                            continuation.resumeWithException(exception)
                        }
                }
                emit(message)
            } catch (e: Exception) {
                val errorMessage = "Exception in DeleteReservation flow: ${e.message}"
                Log.e("Reservation", errorMessage, e)
                emit(errorMessage)
            }
        }
    }

}