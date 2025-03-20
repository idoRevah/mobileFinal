package com.example.mobilefinal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mobilefinal.data.converters.StringListConverter
import com.example.mobilefinal.data.dao.WorkoutDao
import com.example.mobilefinal.data.model.Workout

@Database(entities = [Workout::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class MobileFinalDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
//    abstract fun exerciseDao(): ExerciseDao
//    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: MobileFinalDatabase? = null

        fun getDatabase(context: Context): MobileFinalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MobileFinalDatabase::class.java,
                    "mobileFinal.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}