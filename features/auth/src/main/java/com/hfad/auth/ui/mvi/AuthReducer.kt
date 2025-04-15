package com.hfad.auth.ui.mvi

class AuthReducer {

    fun <T> reduceResult(
        result: Result<T>,
        onSuccessState: (T) -> AuthUiState,
        onErrorMessage: String = "Произошла ошибка"
    ): AuthUiState {
        return result.fold(
            onSuccess = onSuccessState,
            onFailure = {
                AuthUiState(status = UiStatus.Error(it.message ?: onErrorMessage))
            }
        )
    }
}
