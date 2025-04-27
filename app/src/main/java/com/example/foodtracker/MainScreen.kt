package com.example.foodtracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.foodtracker.data.Food
import com.example.foodtracker.ui.ProfileScreen
import com.example.foodtracker.ui.screens.AddFoodScreen
import com.example.foodtracker.ui.screens.FoodState
import com.example.foodtracker.ui.screens.FoodViewModel
import com.example.foodtracker.ui.screens.HomeScreen
import com.example.foodtracker.ui.screens.toDay
import java.time.Instant
import java.util.Date

enum class Navigation{
    Home, Add, Profile
}

@Composable
fun MainScreen(
    viewModel: FoodViewModel = viewModel(factory = FoodViewModel.Factory)
) {
    val foods by viewModel.foodListToday.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navigation.Home.name) {
        composable(route = Navigation.Home.name) {
            when(foods) {
                is FoodState.Success -> HomeScreen((foods as FoodState.Success).foods, foodViewModel = viewModel,{
                    navController.navigate(Navigation.Profile.name)
                }, {
                    navController.navigate(Navigation.Add.name)
                })
                else -> {
                    HomeScreen(emptyList(),viewModel ,{
                        navController.navigate(Navigation.Profile.name)
                    }, {
                        navController.navigate(Navigation.Add.name)
                    })
                }
            }
        }
        composable(route = Navigation.Add.name) {
            AddFoodScreen({navController.popBackStack()}) { name, calories, sugar, protein ->
                viewModel.addFood(Food(name = name,calories = calories, sugar = sugar, protein = protein, date = Date.from(
                    Instant.now()).toDay()))
                navController.popBackStack()
            }
        }
        composable(route = Navigation.Profile.name) {
            ProfileScreen({navController.popBackStack()}, viewModel)
        }
    }
}