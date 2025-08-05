package com.hfad.coaches.data.repository


import com.hfad.coaches.data.api.CoachApi
import com.hfad.coaches.data.model.CoachDto
import com.hfad.coaches.domain.repository.CoachRepository
import javax.inject.Inject
import com.hfad.module.Coach
import com.hfad.module.SportType


class CoachRepositoryImpl @Inject constructor(
    private val api: CoachApi
) : CoachRepository {

    private var cached: List<Coach>? = null

    override suspend fun getAllCoaches(): List<Coach> {
        if (cached != null) return cached!!

        val response = api.getCoaches()
        val result = buildList {
            addAll(response.basketball.map { it.toCoach(SportType.BASKETBALL) })
            addAll(response.volleyball.map { it.toCoach(SportType.VOLLEYBALL) })
        }

        cached = result
        return result
    }

    private fun CoachDto.toCoach(sport: SportType): Coach {
        return Coach(
            id = name.hashCode(),
            name = name,
            imageUrl = imageUrl,
            bio = bio,
            sport = sport
        )
    }
}