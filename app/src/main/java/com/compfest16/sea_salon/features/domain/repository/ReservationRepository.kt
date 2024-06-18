package com.compfest16.sea_salon.features.domain.repository

import com.compfest16.sea_salon.features.domain.model.ReservationModel
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    suspend fun GetReservations(): Flow<List<ReservationModel>>
    suspend fun GetReservationByID(id: String): Flow<ReservationModel>
    suspend fun GetReservationByUserID(id: String): Flow<List<ReservationModel>>
    suspend fun GetReservationByBranchID(id: String): Flow<List<ReservationModel>>
    suspend fun PostReservation(reservation: ReservationModel): Flow<String>
    suspend fun PutReservation(reservation: ReservationModel): Flow<String>
    suspend fun DeleteReservation(id: String): Flow<String>
}