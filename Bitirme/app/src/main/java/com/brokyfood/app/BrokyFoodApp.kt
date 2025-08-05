package com.brokyfood.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BrokyFoodApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any required libraries here
    }
} 