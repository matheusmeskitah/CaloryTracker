package com.meskitah.onboarding_domain.use_case

import com.meskitah.core.R
import com.meskitah.core.util.UiText

class ValidateNutrients {
    operator fun invoke(
        carbsRationText: String,
        proteinRationText: String,
        fatRationText: String
    ): Result {
        val carbsRatio = carbsRationText.toFloatOrNull()
        val proteinRatio = proteinRationText.toFloatOrNull()
        val fatRatio = fatRationText.toFloatOrNull()

        if (carbsRatio == null || proteinRatio == null || fatRatio == null)
            return Result.Error(UiText.StringResource(R.string.error_invalid_values))

        if (carbsRatio + proteinRatio + fatRatio != 100f)
            return Result.Error(UiText.StringResource(R.string.error_not_100_percent))

        return Result.Success(carbsRatio, proteinRatio, fatRatio)
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float
        ) : Result()

        data class Error(val message: UiText) : Result()
    }
}