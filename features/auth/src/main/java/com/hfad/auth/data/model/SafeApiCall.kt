package com.hfad.auth.data.model


import retrofit2.Response

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(NetworkError.UnknownError("Empty response"))
        } else {
            Result.failure(NetworkError.ApiError)
        }
    } catch (e: Exception) {
        Result.failure(NetworkError.NetworkConnectionError)
    }
}
