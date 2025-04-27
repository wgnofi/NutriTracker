package com.example.foodtracker.data

import android.content.Context

interface AppContainer {
    val foodRepository: FoodRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {
    override val foodRepository: FoodRepository by lazy {
        OfflineFoodRepository(FoodDatabase.getDatabase(context).foodDao())
    }
}