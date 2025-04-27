package com.example.foodtracker.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.foodtracker.R

fun showLimitExceededNotification(context: Context, nutrient: String, currentValue: Int, limit: Int) {
    val builder = NotificationCompat.Builder(context, "nutrient_channel_id")
        .setSmallIcon(R.drawable.time)
        .setContentTitle("Limit Exceeded!")
        .setContentText("Your $nutrient ($currentValue) has exceeded the daily limit ($limit).")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("NOTIFY", "Cannot make notifications")
        }
        notify(getNotificationIdForNutrient(nutrient), builder.build())
    }
}

fun getNotificationIdForNutrient(nutrient: String): Int {
    return when (nutrient) {
        "Calories" -> 101
        "Sugar" -> 102
        "Protein" -> 103
        else -> nutrient.hashCode()
    }
}