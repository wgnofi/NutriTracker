package com.example.foodtracker.data

import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun addFood(f: Food)
    fun getFoodToday(d: String): Flow<List<Food>>
    fun getAll(): Flow<List<Food>>
}

class OfflineFoodRepository(private val foodDao: FoodDao): FoodRepository {
    override suspend fun addFood(f: Food) = foodDao.insertFood(f)

    override fun getFoodToday(d: String): Flow<List<Food>> = foodDao.getFoodsByDate(d)

    override fun getAll() = foodDao.getAll()
}