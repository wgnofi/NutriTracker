package com.example.foodtracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food")
data class Food(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String = "",
    var calories: Int = 0,
    var sugar: Int = 0,
    var protein: Int = 0,
    var date: String = ""
)
