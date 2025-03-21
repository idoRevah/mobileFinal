package com.example.mobilefinal;

import android.app.Application
import android.util.Log
import com.example.mobilefinal.data.MobileFinalDatabase
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
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
        Log.d("ola", FirebaseFirestore.getInstance().app.name)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val collections = FirebaseFirestore.getInstance().().await()
                collections.forEach { collection ->
                    Log.d("FirestoreDebug", "Collection found: ${collection.id}")
                }

                val snapshot = FirebaseFirestore.getInstance()
                    .collection("workouts")
                    .get()
                    .await()

                if (snapshot.isEmpty) {
                    Log.e("ola", "❌ Firestore collection is EMPTY!")
                } else {
                    snapshot.documents.forEach { document ->
                        Log.d("ola", "Document ID: ${document.id}, Data: ${document.data}")
                    }
                }
            } catch (e: Exception) {
                Log.e("ola", "Firestore fetch error: ${e.message}")
            }
        }
    }
}
