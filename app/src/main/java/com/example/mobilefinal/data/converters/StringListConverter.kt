package com.example.mobilefinal.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list) // Convert list to JSON string
    }

    @TypeConverter
    fun toList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type) // Convert JSON back to List<String>
    }
}