package com.hfad.rent.domain.usecase

import com.hfad.rent.domain.RentalRequest
import com.hfad.rent.domain.repository.RentRepository
import javax.inject.Inject

class SubmitRentalRequestUseCase @Inject constructor(
    private val repository: RentRepository
) {
    suspend operator fun invoke(request: RentalRequest): Result<Unit> {
        return repository.submitRentalRequest(request)
    }
}
