package com.bignerdranch.android.training_for_trainers

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.substring
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.sqlite.db.SimpleSQLiteQuery
import com.bignerdranch.android.training_for_trainers.data.Exercises_data

import com.bignerdranch.android.training_for_trainers.ui.theme.merriweatherFontFamily
import okhttp3.internal.wait


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exercise_Info(navController: NavHostController, str: String?, mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)){
    val exerciseDataString = str?.substringAfter("[")?.substringBefore(")]")
    val exerciseDataParts = exerciseDataString?.split(", ")
    val exerciseData = exerciseDataParts?.get(1)?.let {
        Exercises_data(
        id = exerciseDataParts?.get(0)?.substringAfter("id=")?.toInt(),
        name = it.substringAfter("name="),
        name_parent = exerciseDataParts[2].substringAfter("name_parent="),
        description = exerciseDataParts[3].substringAfter("description="),
        time_of_approach = exerciseDataParts[4].substringAfter("time_of_approach=").toDouble(),
        delay_time = exerciseDataParts[5].substringAfter("delay_time=").toDouble(),
        rounds = exerciseDataParts[6].substringAfter("rounds=").toInt(),
        image = exerciseDataParts[7].substringAfter("image="),
        benefits = exerciseDataParts[8].substringAfter("benefits="),
        video = exerciseDataParts[9].substringAfter("video="),
        equipment = exerciseDataParts[10].substringAfter("equipment="),
        liked = exerciseDataParts[11].substringAfter("liked=").toBoolean(),
        colors = exerciseDataParts[12].substringAfter("colors=").replace(")", "").toInt(),
        arrows = exerciseDataParts[13].substringAfter("arrows=").replace(")", "").toInt()
    )
    }
    var info = str.toString()
    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.height(50.dp),title = {
                Box(Modifier.fillMaxSize()){
                if (info != null) {
                    if (exerciseData != null) {
                        Text(
                            text = exerciseData.name,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = merriweatherFontFamily,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.None
                            ),
                            modifier = Modifier
                                .requiredWidth(width = 110.dp)
                                .align(alignment = Alignment.Center)
                        )
                    }
                }
            }
            }, navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .offset(x = 24.dp, y = 14.dp)
                            .clickable {
                                navController.navigate("exercises" + "/${info}")
                            },
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                })
        }
    ) {
        Divider(thickness = 2.dp,color=Color.Black,modifier = Modifier.height(55.dp))
        Column(modifier= Modifier
            .padding(top = 70.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally){
        }
}}

@Composable
fun Making_Exercise_Info(navController: NavHostController, id: Int?, item: Exercises_data){
    Text(text = item.name)
}

