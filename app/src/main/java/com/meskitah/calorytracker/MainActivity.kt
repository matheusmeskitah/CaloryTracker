package com.meskitah.calorytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.meskitah.calorytracker.navigation.navigateTo
import com.meskitah.calorytracker.ui.theme.CaloryTrackerTheme
import com.meskitah.core.domain.preferences.Preferences
import com.meskitah.core.navigation.Route
import com.meskitah.onboarding_presentation.activity.ActivityLevelScreen
import com.meskitah.onboarding_presentation.age.AgeScreen
import com.meskitah.onboarding_presentation.gender.GenderScreen
import com.meskitah.onboarding_presentation.goal.GoalScreen
import com.meskitah.onboarding_presentation.height.HeightScreen
import com.meskitah.onboarding_presentation.nutrient_goal.NutrientGoalScreen
import com.meskitah.onboarding_presentation.weight.WeightScreen
import com.meskitah.onboarding_presentation.welcome.WelcomeScreen
import com.meskitah.tracker_presentation.search.SearchScreen
import com.meskitah.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()

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
                        startDestination = if(shouldShowOnboarding) {
                            Route.WELCOME
                        } else Route.TRACKER_OVERVIEW,
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
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") { type = NavType.StringType },
                                navArgument("dayOfMonth") { type = NavType.IntType },
                                navArgument("month") { type = NavType.IntType },
                                navArgument("year") { type = NavType.IntType },
                            )
                        ) {
                            val mealName = it.arguments?.getString("mealName")!!
                            val day = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!

                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = day,
                                month = month,
                                year = year,
                                onNavigateUp = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}