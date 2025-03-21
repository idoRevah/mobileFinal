package com.example.mobilefinal;

import android.app.Application
import android.util.Log
import com.example.mobilefinal.data.MobileFinalDatabase
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

public class InitApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("InitApp", "Application created")
        MobileFinalDatabase.initDatabase(this)
        FirebaseApp.initializeApp(this)
    }
}
