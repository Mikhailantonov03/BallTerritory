package com.hfad.rent.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RentApi {

    @POST("/rental-request")
    suspend fun submitRentalRequest(
        @Body request: RentalRequestDto
    ): Response<RentalResponseDto>
}
