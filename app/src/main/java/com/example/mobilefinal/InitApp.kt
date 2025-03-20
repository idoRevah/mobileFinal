package com.example.mobilefinal;

import android.app.Application
import android.util.Log
import com.example.mobilefinal.data.MobileFinalDatabase

public class InitApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("InitApp", "Application created")
        MobileFinalDatabase.initDatabase(this)
    }
}
