package com.hfad.rent.ui.mvi

sealed interface RentIntent {
    data class SelectHallType(val hallType: String) : RentIntent
    object SubmitRequest : RentIntent
    object ResetState : RentIntent
}
