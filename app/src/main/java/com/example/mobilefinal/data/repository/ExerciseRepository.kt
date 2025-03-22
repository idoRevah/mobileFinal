package com.example.mobilefinal.data.repository

import com.example.mobilefinal.data.model.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun getExerciseById(exerciseId: String): Result<Exercise> {
        return withContext(Dispatchers.IO) {
            val url = "https://exercisedb-api.vercel.app/api/v1/exercises/$exerciseId"
            val request = Request.Builder().url(url).build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        return@withContext Result.failure(IOException("Unexpected code ${response.code}"))
                    }

                    val responseBody = response.body?.string()
                    if (responseBody.isNullOrEmpty()) {
                        return@withContext Result.failure(IOException("Empty response body"))
                    }

                    val exercise = parseExerciseById(responseBody)

                    var gifRepository: GifsRepository = GifsRepository()
                    exercise.gifUrl = gifRepository.getGifUrlById(exercise.id) ?: exercise.gifUrl
                    Result.success(exercise)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
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
                    description = jsonArrayToStringList(exerciseObject.getJSONArray("instructions")).joinToString("\n"),
                    equipment = jsonArrayToStringList(exerciseObject.getJSONArray("equipments")).joinToString(", "),
                    secondaryMuscles = jsonArrayToStringList(exerciseObject.getJSONArray("secondaryMuscles")).joinToString(", "),
                )
                exercises.add(exercise)
            }
        }
        return exercises
    }

    private fun parseExerciseById(jsonString: String): Exercise {
        val jsonObject = JSONObject(jsonString)
        val data = jsonObject.getJSONObject("data")
        return Exercise(
            id = data.getString("exerciseId"),
            name = data.getString("name"),
            gifUrl = data.getString("gifUrl"),
            muscle = jsonArrayToStringList(data.getJSONArray("targetMuscles"))[0],
            description = jsonArrayToStringList(data.getJSONArray("instructions")).joinToString("\n\n"),
            equipment = jsonArrayToStringList(data.getJSONArray("equipments")).joinToString(", "),
            secondaryMuscles = jsonArrayToStringList(data.getJSONArray("secondaryMuscles")).joinToString(", "),
            )
    }

    private fun jsonArrayToStringList(jsonArray: JSONArray): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getString(i))
        }
        return list
    }
}