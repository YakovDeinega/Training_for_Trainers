package com.bignerdranch.android.training_for_trainers.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertItem(exercisesData: Exercises_data)
    @Delete
    suspend fun deleteItem(exercisesData: Exercises_data)
    @Update
    suspend fun updateItem(exercisesData: Exercises_data)
    @Query("SELECT * FROM Exercises_data")
    fun getAllExercises(): Flow<List<Exercises_data>>
    @Query("SELECT * FROM Exercises_data where id=:id")
    fun getNeededExercise(id: Int): Flow<List<Exercises_data>>
    @Query("SELECT * FROM EXERCISES_DATA where name_parent=:name_cur")
    fun getNeededFromParentExercises(name_cur: String): Flow<List<Exercises_data>>
}