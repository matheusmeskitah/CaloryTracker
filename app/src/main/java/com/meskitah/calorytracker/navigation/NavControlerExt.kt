package com.meskitah.calorytracker.navigation

import androidx.navigation.NavController
import com.meskitah.core.util.UiEvent

fun NavController.navigateTo(event: UiEvent.Navigate) {
    this.navigate(event.route)
}