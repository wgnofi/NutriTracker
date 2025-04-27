package com.example.foodtracker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFood(food: Food)

    @Query("SELECT * FROM food WHERE date = :d")
    fun getFoodsByDate(d: String): Flow<List<Food>>

    @Query("SELECT * FROM food")
    fun getAll(): Flow<List<Food>>
}