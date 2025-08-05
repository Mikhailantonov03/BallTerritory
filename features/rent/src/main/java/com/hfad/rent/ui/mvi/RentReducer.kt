package com.hfad.rent.ui.mvi

class RentReducer {

    fun reduceSelectHall(previousState: RentUiState, hallType: String): RentUiState {
        return previousState.copy(
            selectedHallType = hallType,
            isRequestSent = false,
            errorMessage = null,
            needsAuth = false
        )
    }

    fun reduceSubmitResult(previousState: RentUiState, result: Result<Unit>): RentUiState {
        return if (result.isSuccess) {
            previousState.copy(
                isRequestSent = true,
                errorMessage = null,
                needsAuth = false
            )
        } else {
            previousState.copy(
                isRequestSent = false,
                errorMessage = result.exceptionOrNull()?.message ?: "Ошибка отправки заявки",
                needsAuth = false
            )
        }
    }

    fun reduceNeedsAuth(previousState: RentUiState): RentUiState {
        return previousState.copy(
            needsAuth = true,
            isRequestSent = false,
            errorMessage = null
        )
    }

    fun reduceResetState(previousState: RentUiState): RentUiState {
        return previousState.copy(
            isRequestSent = false,
            errorMessage = null,
            needsAuth = false
        )
    }
}
