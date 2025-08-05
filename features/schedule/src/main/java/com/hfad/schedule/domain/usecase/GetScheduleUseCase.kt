package com.hfad.schedule.domain.usecase

  import android.os.Build
  import androidx.annotation.RequiresApi
  import com.hfad.schedule.domain.repository.ScheduleRepository
 import com.hfad.schedule.ui.model.ScheduleItem
  import com.hfad.schedule.ui.mvi.Schedule.toLocalDate
  import java.time.LocalDate
  import javax.inject.Inject

class GetScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) {
    suspend operator fun invoke(): Result<List<ScheduleItem>> {
        return scheduleRepository.getSchedule()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(date: LocalDate): Result<List<ScheduleItem>> {
        return scheduleRepository.getSchedule().map { list ->
            list.filter { it.timestamp.toLocalDate() == date }
        }
    }
}
