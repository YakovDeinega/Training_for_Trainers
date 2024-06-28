package com.bignerdranch.android.training_for_trainers

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bignerdranch.android.training_for_trainers.data.Exercises_data
import com.bignerdranch.android.training_for_trainers.navigation_tools.BottomNavigationBar
import com.bignerdranch.android.training_for_trainers.navigation_tools.NavRoutes
import com.bignerdranch.android.training_for_trainers.ui.theme.merriweatherFontFamily
import java.io.File
import kotlin.coroutines.coroutineContext
import kotlin.system.exitProcess

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Exercises(navController: NavHostController, theme: String?, mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)) {
    if (theme != null) {
        mainViewModel.name_cur.value = theme
    }
    var itemList = theme?.let { mainViewModel.database.dao.getNeededFromParentExercises(it) }?.collectAsState(
        initial = emptyList()
    )
    var copy = itemList
    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.height(50.dp),title = {Box(Modifier.fillMaxSize()){
                if (theme != null) {
                    Text(
                        text = theme,
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
            }}
                , navigationIcon = {
                Icon(
                    modifier = Modifier
                        .offset(x = 24.dp, y = 14.dp)
                        .clickable {
                            navController.navigate("home")
                        },
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            })
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        Divider(thickness = 2.dp,color=Color.Black,modifier = Modifier.height(55.dp))
        val searchText = remember{ mutableStateOf("") }
        SearchBar(modifier = Modifier.padding(top=70.dp,).padding(horizontal = 20.dp).
        fillMaxWidth().height(60.dp).
        clip(shape = RoundedCornerShape(25.dp)),
            query = searchText.value,
            onQueryChange = {text -> searchText.value = text},
            onSearch = {
                       text -> itemList = search(text,copy)
                    Log.d(null, itemList.toString())
            },
            active = false,
            onActiveChange = {},
            placeholder = {
                Row(modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(25.dp))
                    Text(text = "Поиск...", fontSize = 19.sp,
                        modifier = Modifier.fillMaxSize())
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color(0xff8a8a8a)
            )) {
        }
        LazyColumn(
            contentPadding = PaddingValues(top = 150.dp, bottom = 70.dp),
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (itemList != null) {
                items(itemList!!.value) { it ->
                    Making_Exercise(navController, theme,it,mainViewModel)
                }
            }
        }

    }
}

fun search(text: String, copy:  State<List<Exercises_data>>?): State<List<Exercises_data>>? {

    var itemList = copy!!.value.filter { it ->
        it.name.lowercase().startsWith(text)
    }
    return mutableStateOf(itemList)
}

@Composable
fun Making_Exercise(navController: NavHostController, theme: String?, item: Exercises_data, mainViewModel: MainViewModel){
        var str = item.benefits.split(" ")
        Row(
            modifier = Modifier
                .requiredWidth(width = 326.dp)
                .requiredHeight(height = 100.dp)
                .clip(shape = RoundedCornerShape(25.dp))
                .background(Color.Gray)
                .clickable(onClick = {
                    navController.navigate("exercises_info" + "/${item.toString()}") {
                    }

                })

        ) {
            AsyncImage(
                model = item.image,
                contentDescription = "Something",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(84.dp)
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .align(alignment = Alignment.CenterVertically)

            )
            Column(verticalArrangement = Arrangement.SpaceAround, modifier=Modifier.fillMaxHeight()) {
                Text(
                    text = item.name,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 17.sp
                    ),
                    modifier = Modifier
                        .requiredWidth(width = 177.dp)
                )
                Row() {
                    Box(
                        modifier = Modifier
                            .requiredWidth(width = 94.dp)
                            .requiredHeight(height = 32.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = Color(0xffbfbfbf))
                            .border(
                                border = BorderStroke(2.dp, Color(0xff8a8a8a)),
                                shape = RoundedCornerShape(10.dp)
                            )

                    ){
                        Text(
                            text = str[0],
                            color = Color.Black.copy(alpha = 0.25f),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.Center)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .requiredWidth(width = 94.dp)
                            .requiredHeight(height = 32.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = Color(0xffbfbfbf))
                            .border(
                                border = BorderStroke(2.dp, Color(0xff8a8a8a)),
                                shape = RoundedCornerShape(10.dp)
                            )
                    ){
                        Text(
                            text = str[1],
                            color = Color.Black.copy(alpha = 0.25f),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.Center)
                        )
                    }

                }
            }
        }
}


