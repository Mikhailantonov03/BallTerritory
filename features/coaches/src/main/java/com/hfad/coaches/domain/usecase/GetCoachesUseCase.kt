package com.hfad.coaches.domain.usecase

import com.hfad.coaches.domain.repository.CoachRepository
import com.hfad.module.Coach
import javax.inject.Inject

class GetCoachesUseCase @Inject constructor(
    private val repository: CoachRepository
) {
    suspend operator fun invoke(): List<Coach> = repository.getAllCoaches()
}
