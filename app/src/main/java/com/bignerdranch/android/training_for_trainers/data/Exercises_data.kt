package com.bignerdranch.android.training_for_trainers.data

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Exercises_data(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val name_parent: String,
    val description: String,
    val time_of_approach: Double,
    val delay_time: Double,
    val rounds: Int,
    val image: String,
    val benefits: String,
    val video: String,
    val equipment: String,
    val liked: Boolean,
    val colors: Int,
    val arrows: Int
)
