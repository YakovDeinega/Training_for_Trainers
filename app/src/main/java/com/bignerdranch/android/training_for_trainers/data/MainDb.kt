package com.bignerdranch.android.training_for_trainers.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        Exercises_data::class
    ],
    version = 8
)
abstract class MainDb: RoomDatabase() {
    abstract val dao: Dao
    companion object{
        fun createDataBase(context: Context): MainDb{
            return Room.databaseBuilder(
                context,
                MainDb::class.java,
                "MainDb.db",
            ).fallbackToDestructiveMigration().build()
        }
    }
}