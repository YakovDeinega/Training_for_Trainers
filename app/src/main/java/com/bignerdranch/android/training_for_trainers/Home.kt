package com.bignerdranch.android.training_for_trainers

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.bignerdranch.android.training_for_trainers.navigation_tools.BottomNavigationBar
import com.bignerdranch.android.training_for_trainers.ui.theme.merriweatherFontFamily
import java.io.File


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController, mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)){
    var list1 = mainViewModel.database.dao.getAllExercises().collectAsState(initial = emptyList())
    Log.d("from panel", list1.toString())
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) },
        containerColor = Color(0xff8c8c8c)
    ){
        val myDictionary: MutableMap<Int, Int> = mutableMapOf()
        myDictionary.put(0, R.drawable.football)
        myDictionary.put(1, R.drawable.basketball)
        myDictionary.put(2, R.drawable.volleyball)
        myDictionary.put(3, R.drawable.fitness)
        myDictionary.put(4, R.drawable.reactive_agility)
        myDictionary.put(5, R.drawable.workout)
        var themes = listOf("Футбол", "Баскетбол", "Волейбол", "Фитнес", "Скорость реакции","Ворк-аут")
        LazyColumn(contentPadding = PaddingValues(top=20.dp, bottom=70.dp), modifier = Modifier
            .fillMaxSize()
            ,horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(20.dp) ){
            itemsIndexed(themes){
                    index, theme -> making_themes(index,theme,myDictionary, navController)
            }
        }
    }
}
@Composable
fun making_themes(index: Int, theme: String, myDictionary: MutableMap<Int, Int>,navController: NavHostController){
    Box(modifier = Modifier.clickable(onClick = {
        navController.navigate("exercises"  + "/$theme"){
//                              popUpTo(navController.graph.findStartDestination().id)
//                              launchSingleTop = true
//                              restoreState = true

        }
    })
    ){
        Image(
            painter = painterResource(id = myDictionary[index]!!),
            contentDescription = "$theme",
            modifier = Modifier
                .requiredWidth(width = 326.dp)
                .requiredHeight(height = 100.dp)
                .clip(shape = RoundedCornerShape(25.dp)))
        Text(
            text = theme,
            color = Color.White,
            textAlign = TextAlign.Center,
            lineHeight = 1.05.em,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = merriweatherFontFamily,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .align(alignment = Alignment.CenterStart)
                .requiredWidth(width = 110.dp)
                .wrapContentHeight(align = Alignment.CenterVertically))
    }
}