package com.bignerdranch.android.training_for_trainers

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.bignerdranch.android.training_for_trainers.data.Exercises_data
import com.bignerdranch.android.training_for_trainers.data.MainDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(val database: MainDb): ViewModel() {
    val itemsList = database.dao.getAllExercises()
    var new_text= mutableStateOf("")
    var name_cur = mutableStateOf("")
    var id_needed = mutableStateOf(0)
    var description= mutableStateOf("")
    var time_of_approach= mutableStateOf(0.0)
    var delay_time= mutableStateOf(0.0)
    var rounds= mutableStateOf(0)
    var image= mutableStateOf("")
    var benefits = mutableStateOf("")
    var video= mutableStateOf("")
    var equipment = mutableStateOf("")
    var liked = mutableStateOf(false)
    var colors = mutableStateOf(0)
    var arrows= mutableStateOf(0)
    fun insertItem() = viewModelScope.launch {
        val nameItem = Exercises_data(null, new_text.value, name_cur.value, description.value, time_of_approach.value, delay_time.value, rounds.value, image.value, benefits.value, video.value, equipment.value, liked.value, colors.value, arrows.value)
        database.dao.insertItem(nameItem)
    }
    var needed_list: State<List<Exercises_data>>? = null
    companion object{
        val factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return MainViewModel(database) as T
            }
        }
    }
}

