package com.hfad.coaches.data.model

data class CoachesResponse(
    val basketball: List<CoachDto>,
    val volleyball: List<CoachDto>
)