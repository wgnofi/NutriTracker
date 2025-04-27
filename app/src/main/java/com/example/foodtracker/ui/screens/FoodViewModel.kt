package com.example.foodtracker.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foodtracker.FoodApplication
import com.example.foodtracker.data.Food
import com.example.foodtracker.data.FoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

sealed interface FoodState {
    data class Success(val foods: List<Food>): FoodState
    object Empty: FoodState
}

class FoodViewModel(
    val foodRepository: FoodRepository
): ViewModel() {

    val foodListToday = foodRepository.getFoodToday(Date.from(Instant.now()).toDay()).mapNotNull { res ->
        when {
            res.isNotEmpty() -> FoodState.Success(res)
            else -> {FoodState.Empty}
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = FoodState.Empty
    )

    val foods = foodRepository.getAll().mapNotNull { res ->
        when {
            res.isNotEmpty() -> FoodState.Success(res)
            else -> {FoodState.Empty}
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = FoodState.Empty
    )


    fun getTriplesFromToday(l:List<Food>):Triple<Float, Float, Float> {
        val totalCals = (l.sumOf { it.calories }.toFloat()) / 2000
        val totalSugar = (l.sumOf { it.sugar }.toFloat()) / 30
        val totalProtein = (l.sumOf { it.protein }.toFloat()) / 46

        return Triple(first = totalCals, second = totalSugar, third = totalProtein)
    }

    fun addFood(f: Food) {
        viewModelScope.launch {
            foodRepository.addFood(f)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val foodRepository =
                    (this[APPLICATION_KEY] as FoodApplication).container.foodRepository
                FoodViewModel(foodRepository)
            }
        }
    }
}

fun Date.toDay(): String {
    return "${date}/${month + 1}/${1900 + year}"
}