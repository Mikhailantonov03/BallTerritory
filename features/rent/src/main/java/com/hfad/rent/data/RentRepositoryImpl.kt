package com.hfad.rent.data

import com.hfad.rent.data.api.RentApi
import com.hfad.rent.data.api.RentalRequestDto
import com.hfad.rent.domain.RentalRequest
import com.hfad.rent.domain.repository.RentRepository
import javax.inject.Inject

class RentRepositoryImpl @Inject constructor(
    private val api: RentApi
) : RentRepository {

    override suspend fun submitRentalRequest(request: RentalRequest): Result<Unit> {
        return try {
            val response = api.submitRentalRequest(
                RentalRequestDto(
                    hallName = request.hallName,
                    userId = request.userId,
                    userName = request.userName,
                    userPhone = request.userPhone,
                    userEmail = request.userEmail
                )
            )

            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.body()?.message ?: "Ошибка отправки заявки"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
