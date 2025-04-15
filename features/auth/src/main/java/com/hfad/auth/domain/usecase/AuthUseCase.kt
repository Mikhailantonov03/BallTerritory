package com.hfad.auth.domain.usecase

import com.hfad.auth.data.model.CodeRequest
import com.hfad.auth.data.model.PhoneRequest
import com.hfad.auth.domain.repository.AuthRepository
import com.hfad.module.AuthTokens
import com.hfad.module.User
import javax.inject.Inject

class SendPhoneUseCase @Inject constructor(private val repository: AuthRepository){
    suspend operator fun invoke(phone: PhoneRequest): Result<Unit>{
        return repository.sendPhone(phone)
    }
}

class VerifyCodeUseCase @Inject constructor(private val repository: AuthRepository){
    suspend operator fun invoke(code: CodeRequest): Result<Pair<User, AuthTokens>>{
        if (code.code.length != 4) {
            return Result.failure(IllegalArgumentException("Code must be 4 digits"))
        }
        return repository.verifyCode(code)
    }
}