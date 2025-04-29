package com.hfad.auth.ui.viewModels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.auth.data.model.PhoneRequest
import com.hfad.auth.domain.usecase.CompleteProfileUseCase
import com.hfad.auth.domain.model.SaveResult
import com.hfad.auth.domain.usecase.SendPhoneUseCase
import com.hfad.auth.domain.usecase.VerifyCodeAndSaveUseCase
import com.hfad.auth.ui.mvi.AuthIntents
import com.hfad.auth.ui.mvi.AuthReducer
import com.hfad.auth.ui.mvi.AuthUiState
import com.hfad.module.UiStatus
import com.hfad.data.token.tokens.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendPhoneUseCase: SendPhoneUseCase,
    private val verifyCodeAndSaveUseCase: VerifyCodeAndSaveUseCase,
    private val completeProfileUseCase: CompleteProfileUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val sessionManager: SessionManager
) : ViewModel() {

    fun markAsGuest(onDone: () -> Unit) {
        viewModelScope.launch {
            sessionManager.setGuestMode(true)
            onDone()
        }
    }
        companion object {
        private const val KEY_PHONE = "phone_number"
    }

    private var phoneNumber: String
        get() = savedStateHandle[KEY_PHONE] ?: ""
        set(value) {
            savedStateHandle[KEY_PHONE] = value
        }

    private val reducer = AuthReducer()
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onIntent(intent: AuthIntents) {
        viewModelScope.launch {
            when (intent) {
                is AuthIntents.OnPhoneEntered -> handlePhoneEntered(intent.phone)
                is AuthIntents.OnCodeEntered -> handleCodeEntered(intent.code)
                is AuthIntents.OnProfileCompleted -> handleProfileCompleted(intent.name, intent.email)
            }
        }
    }

    private suspend fun handlePhoneEntered(phone: String) {
        phoneNumber = phone
        Log.d("DEBUG", "Считанный номер из get: $phoneNumber")
        _uiState.value = AuthUiState(UiStatus.Loading)
        val result = sendPhoneUseCase(PhoneRequest(phone))
        _uiState.value = reducer.reduceResult(
            result = result,
            onSuccessState = { AuthUiState(status = UiStatus.Idle, phoneSent = true) },
            onErrorMessage = "Ошибка при отправке номера"
        )
    }

    private suspend fun handleCodeEntered(code: String) {
        Log.d("DEBUG", "Отправка кода: $code, телефон: $phoneNumber")
        _uiState.value = AuthUiState(status = UiStatus.Loading)

        val result = verifyCodeAndSaveUseCase(phoneNumber, code)
        _uiState.value = reducer.reduceResult(
            result = result,
            onSuccessState = { saveResult ->

                when (saveResult) {
                    is SaveResult.NewUser -> AuthUiState(
                        tokens = saveResult.tokens,
                        user = saveResult.user,
                        requiresProfileCompletion = true
                    )
                    is SaveResult.ExistingUser -> AuthUiState(
                        accessToken = saveResult.accessToken
                    )
                }
            },
            onErrorMessage = "Ошибка при проверке кода"
        )
    }

    private suspend fun handleProfileCompleted(name: String, email:String) {
        val result = completeProfileUseCase(name,email)

        _uiState.value = if (result.isSuccess) {
            AuthUiState(status = UiStatus.Idle)
        } else {
            AuthUiState(status = UiStatus.Error("Не удалось сохранить профиль"))
        }
    }
}
