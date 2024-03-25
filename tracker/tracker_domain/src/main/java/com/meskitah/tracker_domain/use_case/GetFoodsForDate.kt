package com.meskitah.tracker_domain.use_case

import com.meskitah.tracker_domain.model.TrackedFood
import com.meskitah.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

class GetFoodsForDate(
    private val repository: TrackerRepository
) {

    operator fun invoke(date: LocalDate): Flow<List<TrackedFood>> {
        return repository.getFoodsForDate(date)
    }
}