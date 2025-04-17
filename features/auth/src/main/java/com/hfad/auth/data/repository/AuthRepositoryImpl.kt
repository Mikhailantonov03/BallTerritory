package com.hfad.auth.data.repository



import com.hfad.auth.data.api.AuthApi
import com.hfad.auth.data.model.CodeRequest
import com.hfad.auth.data.model.PhoneRequest
import com.hfad.auth.data.model.ProfileRequest
import com.hfad.auth.data.model.safeApiCall
import com.hfad.auth.data.model.toDomain
import com.hfad.auth.domain.repository.AuthRepository
import com.hfad.module.AuthTokens
import com.hfad.module.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): AuthRepository {
    override suspend fun sendPhone(phone: PhoneRequest): Result<Unit>{
        return safeApiCall{ authApi.sendPhoneNumber(phone)}
    }


    override suspend fun verifyCode(code: CodeRequest): Result<Pair<User, AuthTokens>> {
        return safeApiCall { authApi.verifyCode(code) }
            .map { authResponse ->
                authResponse.toDomain(phoneFromInput = code.phone)
            }
    }
    override suspend fun completeProfile(userId: String, name: String,email: String): Result<Unit> {
        return safeApiCall {
            authApi.completeProfile(ProfileRequest(name,email))
        }
    }


}