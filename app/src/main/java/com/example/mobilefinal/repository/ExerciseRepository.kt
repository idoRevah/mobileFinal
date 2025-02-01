package com.example.mobilefinal.repository

import com.example.mobilefinal.model.Exercise
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ExerciseRepository {

    private val client = OkHttpClient()

    suspend fun getExercisesByMuscle(muscle: String): Result<List<Exercise>> {
        val url = "https://exercisedb-api.vercel.app/api/v1/muscles/$muscle/exercises"

        val request = Request.Builder()
            .url(url)
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
        val jsonObject = JSONObject(jsonString)
        if (jsonObject.getBoolean("success")) {
            val jsonArray = jsonObject.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                val exerciseObject = jsonArray.getJSONObject(i)
                val exercise = Exercise(
                    id = exerciseObject.getString("exerciseId"),
                    name = exerciseObject.getString("name"),
                    gifUrl = exerciseObject.getString("gifUrl"),
                    muscle = jsonArrayToStringList(exerciseObject.getJSONArray("targetMuscles"))[0],
                    description = jsonArrayToStringList(exerciseObject.getJSONArray("instructions")).joinToString(",")
                )
                exercises.add(exercise)
            }
        }
        return exercises
    }

    private fun jsonArrayToStringList(jsonArray: JSONArray): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }
}