package com.meskitah.tracker_data.repository

import com.meskitah.tracker_data.local.TrackerDao
import com.meskitah.tracker_data.mapper.toTrackableFood
import com.meskitah.tracker_data.mapper.toTrackedFood
import com.meskitah.tracker_data.mapper.toTrackedFoodEntity
import com.meskitah.tracker_data.remote.OpenFoodApi
import com.meskitah.tracker_domain.model.TrackableFood
import com.meskitah.tracker_domain.model.TrackedFood
import com.meskitah.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(query, page, pageSize)
            Result.success(
                searchDto.products
                    .filter {
                        val calculatedCalories =
                            (it.nutriments?.carbohydrates100g ?: 0.0) * 4f +
                                    (it.nutriments?.proteins100g ?: 0.0) * 4f +
                                    (it.nutriments?.fat100g ?: 0.0) * 9f

                        val lowerBound = calculatedCalories * 0.99
                        val upperBound = calculatedCalories * 1.01

                        (it.nutriments?.energyKcal100g ?: 0.0) in (lowerBound..upperBound)
                    }
                    .mapNotNull { it.toTrackableFood() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(food: TrackedFood) {
        dao.insertTrackedFood(food.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(food: TrackedFood) {
        dao.deleteTrackedFood(food.toTrackedFoodEntity())
    }

    override fun getFoodsForDate(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodsForDate(
            day = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { entities -> entities.map { it.toTrackedFood() } }
    }
}