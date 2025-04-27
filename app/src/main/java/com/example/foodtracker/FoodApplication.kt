package com.example.foodtracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.example.foodtracker.data.AppContainer
import com.example.foodtracker.data.DefaultAppContainer

class FoodApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Nutrient Alerts"
        val descriptionText = "Notifications for exceeding daily nutrient limits"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channelId = "nutrient_channel_id"
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}