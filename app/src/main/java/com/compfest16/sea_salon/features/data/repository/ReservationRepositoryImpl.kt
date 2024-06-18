package com.compfest16.sea_salon.features.data.repository

import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.compfest16.sea_salon.features.domain.repository.ReservationRepository
import kotlinx.coroutines.flow.Flow

class ReservationRepositoryImpl: ReservationRepository {
    override suspend fun GetReservations(): Flow<List<ReservationModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun GetReservationByID(id: String): Flow<ReservationModel> {
        TODO("Not yet implemented")
    }

    override suspend fun GetReservationByUserID(id: String): Flow<List<ReservationModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun GetReservationByBranchID(id: String): Flow<List<ReservationModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun PostReservation(reservation: ReservationModel): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun PutReservation(reservation: ReservationModel): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun DeleteReservation(id: String): Flow<String> {
        TODO("Not yet implemented")
    }
}