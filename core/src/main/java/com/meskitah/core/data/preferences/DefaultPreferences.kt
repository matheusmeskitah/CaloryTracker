package com.meskitah.core.data.preferences

import android.content.SharedPreferences
import com.meskitah.core.domain.model.ActivityLevel
import com.meskitah.core.domain.model.Gender
import com.meskitah.core.domain.model.GoalType
import com.meskitah.core.domain.model.UserInfo
import com.meskitah.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPrefs: SharedPreferences
) : Preferences {
    override fun saveAge(age: Int) {
        sharedPrefs.edit().putInt(Preferences.KEY_AGE, age).apply()
    }

    override fun saveGender(gender: Gender) {
        sharedPrefs.edit().putString(Preferences.KEY_GENDER, gender.name).apply()
    }

    override fun saveWeight(weight: Float) {
        sharedPrefs.edit().putFloat(Preferences.KEY_WEIGHT, weight).apply()
    }

    override fun saveHeight(height: Int) {
        sharedPrefs.edit().putInt(Preferences.KEY_HEIGHT, height).apply()
    }

    override fun saveActivityLevel(level: ActivityLevel) {
        sharedPrefs.edit().putString(Preferences.KEY_ACTIVITY_LEVEL, level.name).apply()
    }

    override fun saveGoalType(type: GoalType) {
        sharedPrefs.edit().putString(Preferences.KEY_GOAL_TYPE, type.name).apply()
    }

    override fun saveCarbRatio(ratio: Float) {
        sharedPrefs.edit().putFloat(Preferences.KEY_CARB_RATIO, ratio).apply()
    }

    override fun saveProteinRatio(ratio: Float) {
        sharedPrefs.edit().putFloat(Preferences.KEY_PROTEIN_RATIO, ratio).apply()
    }

    override fun saveFatRatio(ratio: Float) {
        sharedPrefs.edit().putFloat(Preferences.KEY_FAT_RATIO, ratio).apply()
    }

    override fun loadUserInfo(): UserInfo {
        val age = sharedPrefs.getInt(Preferences.KEY_AGE, -1)
        val height = sharedPrefs.getInt(Preferences.KEY_HEIGHT, -1)
        val weight = sharedPrefs.getFloat(Preferences.KEY_WEIGHT, -1f)
        val gender =
            sharedPrefs.getString(Preferences.KEY_GENDER, Gender.Other.name) ?: Gender.Other.name
        val activityLevel =
            sharedPrefs.getString(Preferences.KEY_ACTIVITY_LEVEL, ActivityLevel.Medium.name)
                ?: ActivityLevel.Medium.name
        val goalType = sharedPrefs.getString(Preferences.KEY_GOAL_TYPE, GoalType.KeepWeight.name)
            ?: GoalType.KeepWeight.name
        val carbRatio = sharedPrefs.getFloat(Preferences.KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPrefs.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPrefs.getFloat(Preferences.KEY_FAT_RATIO, -1f)

        return UserInfo(
            gender = Gender.fromString(gender),
            age = age,
            weight = weight,
            height = height,
            activityLevel = ActivityLevel.fromString(activityLevel),
            goalType = GoalType.fromString(goalType),
            carbRatio = carbRatio,
            proteinRatio = proteinRatio,
            fatRatio = fatRatio
        )
    }
}