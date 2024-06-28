package com.bignerdranch.android.training_for_trainers

import android.app.Application
import com.bignerdranch.android.training_for_trainers.data.MainDb
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    val database by lazy { MainDb.createDataBase(this)}
}