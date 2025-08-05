package com.hfad.coaches.domain.repository
import com.hfad.module.Coach


interface CoachRepository {
    suspend fun getAllCoaches(): List<Coach>
}
