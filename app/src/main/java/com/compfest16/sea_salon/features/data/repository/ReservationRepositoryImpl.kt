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

class ReservationRepositoryImpl: ReservationRepository {
    private val db = Firebase.firestore
    override suspend fun GetReservations(): Flow<List<ReservationModel>> {
        return flow {
            try {
                val query = db.collection("reservation").get()
                val result = mutableListOf<ReservationModel>()

                for (document in query.result){
                    result.add(document.toReservationModel())
                }
                Log.d("Reservation", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(emptyList())
                Log.d("Reservation", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun GetReservationByID(id: String): Flow<ReservationModel> {
        return flow {
            try {
                val query = db.collection("reservation").get()
                var result = ReservationDummy.notFound
                for (document in query.result){
                    if(document.id == id) {
                        result = document.toReservationModel()
                    }
                }
                Log.d("Reservation", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(ReservationDummy.notFound)
                Log.d("Reservation", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun GetReservationByUserID(id: String): Flow<List<ReservationModel>> {
        return flow {
            try {
                val query = db.collection("reservation").get()
                val result = mutableListOf<ReservationModel>()

                for (document in query.result){
                    if(document.id == id) {
                        result.add(document.toReservationModel())
                    }
                }
                Log.d("Reservation", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(emptyList())
                Log.d("Reservation", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun GetReservationByBranchID(id: String): Flow<List<ReservationModel>> {
        return flow {
            try {
                val query = db.collection("reservation").get()
                val result = mutableListOf<ReservationModel>()

                for (document in query.result){
                    if(document.id == id) {
                        result.add(document.toReservationModel())
                    }
                }
                Log.d("Reservation", result.toString())
                emit(result)
                return@flow
            } catch (e: Exception){
                emit(emptyList())
                Log.d("Reservation", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun PostReservation(reservation: ReservationModel): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("reservation").document(reservation.reservationID).set(reservation.toHashMap()).addOnSuccessListener {
                    messege = "Reservation Posted Successfully"
                    Log.d("Reservation", "Reservation Posted Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("Reservation", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Reservation", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun PutReservation(reservation: ReservationModel): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("reservation").document(reservation.reservationID).update(reservation.toHashMap()).addOnSuccessListener {
                    messege = "Reservation Updated Successfully"
                    Log.d("Reservation", "Reservation Updated Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("Reservation", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Reservation", e.message.toString())
                return@flow
            }
        }
    }

    override suspend fun DeleteReservation(id: String): Flow<String> {
        return flow {
            try {
                var messege: String = ""
                db.collection("reservation").document(id).delete().addOnSuccessListener {
                    messege = "Reservation Deleted Successfully"
                    Log.d("Reservation", "Reservation Deleted Successfully")
                }.addOnFailureListener{
                    messege = it.message.toString()
                    Log.d("Reservation", it.message.toString())
                }
                emit(messege)
                return@flow
            } catch (e: Exception){
                emit(e.message.toString())
                Log.d("Reservation", e.message.toString())
                return@flow
            }
        }
    }
}