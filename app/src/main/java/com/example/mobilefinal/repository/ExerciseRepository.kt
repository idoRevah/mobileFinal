package com.example.mobilefinal.repository

import com.example.mobilefinal.model.Exercise
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ExerciseRepository {

    private val apiKey = "Kl7wH8ZSN7qy1oMFhaizkA==2nGUwS9VWLaJWQzy" // Replace with your actual API key
    private val client = OkHttpClient()

    suspend fun getExercisesByMuscle(muscle: String): Result<List<Exercise>> {
        val url = "https://api.api-ninjas.com/v1/exercises?muscle=$muscle"

        val request = Request.Builder()
            .url(url)
            .header("X-Api-Key", apiKey)
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return Result.failure(IOException("Unexpected code ${response.code}"))
                }

                val responseBody = response.body?.string() ?: ""
                val exercises = parseExercises(responseBody)
                Result.success(exercises)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseExercises(jsonString: String): List<Exercise> {
        val exercises = mutableListOf<Exercise>()
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val exercise = Exercise(
                name = jsonObject.getString("name"),
                type = jsonObject.getString("type"),
                muscle = jsonObject.getString("muscle"),
                equipment = jsonObject.getString("equipment"),
                difficulty = jsonObject.getString("difficulty"),
                description = jsonObject.getString("instructions")
            )
            exercises.add(exercise)
        }
        return exercises
    }
}