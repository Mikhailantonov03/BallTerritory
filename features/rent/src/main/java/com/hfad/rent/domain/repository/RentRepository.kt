package com.hfad.rent.domain.repository

import com.hfad.rent.domain.RentalRequest


interface RentRepository {
    suspend fun submitRentalRequest(request: RentalRequest): Result<Unit>
}

