package com.meskitah.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meskitah.calorytracker.navigation.navigateTo
import com.meskitah.calorytracker.ui.theme.CaloryTrackerTheme
import com.meskitah.core.navigation.Route
import com.meskitah.onboarding_presentation.activity.ActivityLevelScreen
import com.meskitah.onboarding_presentation.age.AgeScreen
import com.meskitah.onboarding_presentation.gender.GenderScreen
import com.meskitah.onboarding_presentation.goal.GoalScreen
import com.meskitah.onboarding_presentation.height.HeightScreen
import com.meskitah.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.meskitah.onboarding_presentation.weight.WeightScreen
import com.meskitah.onboarding_presentation.welcome.WelcomeScreen
import com.meskitah.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.WELCOME,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigateTo)
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                onNavigate = navController::navigateTo,
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNavigate = navController::navigateTo)
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                onNavigate = navController::navigateTo,
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                onNavigate = navController::navigateTo,
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.ACTIVITY) {
                            ActivityLevelScreen(onNavigate = navController::navigateTo)
                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNavigate = navController::navigateTo)
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                onNavigate = navController::navigateTo,
                                scaffoldState = scaffoldState
                            )
                        }

                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigate = navController::navigateTo)
                        }
                        composable(Route.SEARCH) {

                        }
                    }
                }
            }
        }
    }
}