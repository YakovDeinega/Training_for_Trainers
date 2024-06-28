package com.bignerdranch.android.training_for_trainers

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun Tactic_Panel(navController: NavHostController,mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)){
    var list = mainViewModel.database.dao.getAllExercises().collectAsState(initial = emptyList()).toString()
    Box(modifier = Modifier.size(100.dp).background(Color.Black).clickable {
        Log.d("from panel", list)
        Log.d("null",mainViewModel.database.toString())
    })

}