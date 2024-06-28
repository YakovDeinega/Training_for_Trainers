package com.bignerdranch.android.training_for_trainers

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bignerdranch.android.training_for_trainers.navigation_tools.NavRoutes
import com.bignerdranch.android.training_for_trainers.ui.theme.merriweatherFontFamily
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.io.File
import java.util.jar.Attributes
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post(navController: NavHostController,mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory)){
    Column(modifier= Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .background(Color(0xff8c8c8c))
        .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        var text = remember{ mutableStateOf("") }
        var enabled = remember{mutableStateOf(true)}
        var focus = LocalFocusManager.current
        TextField(colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(0xff8c8c8c),focusedTextColor = Color.Black, unfocusedTextColor = Color(0xffd5d5d5)),
            shape = RoundedCornerShape(8.dp),enabled = enabled.value,
            textStyle = TextStyle(fontSize = 15.sp,
                fontFamily = merriweatherFontFamily), value = text.value,
            onValueChange = {it ->
                text.value = it
                mainViewModel.new_text.value = it},
            singleLine = true,
            modifier = Modifier
                .padding(top = 14.dp)
                .height(50.dp)
                .fillMaxWidth()
                .border(border = BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp))
                .background(color = Color(0xff8c8c8c)),
            placeholder = {Text("Название упражнения...")},
            keyboardActions = KeyboardActions(onDone = {focus.clearFocus(force=true)})
        )
        enabled.value = true
        Category(navController, mainViewModel)
        Description(navController = navController, mainViewModel = mainViewModel )
        Length_of_approach(navController, mainViewModel)
        Delay_time(navController, mainViewModel)
        Rounds(navController, mainViewModel)
        SinglePicker(mainViewModel)
        Atributes(navController, mainViewModel)
        Difficulty(navController, mainViewModel)
        setVideoId(mainViewModel = mainViewModel)
        Equipment(mainViewModel)
        SetColor(mainViewModel)
        SetArrow(mainViewModel)
//        mainViewModel.name_cur.value  = "Football"
//        mainViewModel.description.value = "Something about football"
//        mainViewModel.time_of_approach.value = 2.0
//        mainViewModel.delay_time.value = 1.0
//        mainViewModel.rounds.value = 10
//        mainViewModel.image.value = R.drawable.fitness
//        mainViewModel.benefits.value = "Super strong"
        Button(content = {Text(text="asd")}, onClick = {mainViewModel.insertItem()})
        var list = mainViewModel.database.dao.getAllExercises().collectAsState(initial = emptyList())
        Log.d(null,list.toString())
    }
}

@Composable
fun Category(navController: NavHostController,mainViewModel: MainViewModel ){
    var themes = listOf("Футбол", "Баскетбол", "Волейбол", "Фитнес", "Скорость реакции","Ворк-аут")
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp)){
        val selectedOption = remember{ mutableStateOf(themes[0]) }
        Text(text = "Выберите категорию:",
            fontFamily = merriweatherFontFamily,
            fontSize = 15.sp,
            color = Color(0xffffffff))
        LazyHorizontalGrid(rows = GridCells.Fixed(2),modifier = Modifier.height(100.dp), contentPadding = PaddingValues(10.dp)){
            items(themes){theme->
                val selected = selectedOption.value == theme
                var color: Color
                if(selected)
                    color = Color.Black
                else
                    color = Color(0xff8a8a8a)
                Box(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .height(50.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = Color(0xffbfbfbf))
                        .border(
                            border = BorderStroke(2.dp, color),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .selectable(
                            selected = selected,
                            onClick = {
                                selectedOption.value = theme
                                mainViewModel.name_cur.value = theme
                            }
                        )
                ){
                    Text(
                        text = "$theme",
                        color = Color.Black.copy(alpha = 0.25f),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = merriweatherFontFamily
                        ),
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 6.dp)
                            .align(alignment = Alignment.Center)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Description(navController: NavHostController,mainViewModel: MainViewModel ){
    var text = remember{ mutableStateOf("") }
    var focus = LocalFocusManager.current
    TextField(colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(0xff8c8c8c),focusedTextColor = Color.Black, unfocusedTextColor = Color(0xffd5d5d5)),
        textStyle = TextStyle(fontSize = 15.sp,
        fontFamily = merriweatherFontFamily), enabled = true,value = text.value,
        onValueChange = {it ->
            text.value = it
            mainViewModel.description.value = it},
        modifier = Modifier
            .padding(top = 14.dp)
            .height(118.dp)
            .fillMaxWidth()
            .border(border = BorderStroke(2.dp, Color.Black), shape = RoundedCornerShape(8.dp))
            .background(color = Color(0xff8c8c8c)),
        placeholder = {Text("Придумайте описание...")},
        keyboardActions = KeyboardActions(onDone = {focus.clearFocus(force=true)}, onSend = {focus.clearFocus(force=true)}))
}

@Composable
fun Length_of_approach(navController: NavHostController,mainViewModel: MainViewModel){
    var time = remember{ mutableStateOf(0.0) }
    Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.padding(top=15.dp)){
        Text(text="Длительность подхода:", fontSize = 15.sp, fontFamily = merriweatherFontFamily, color = Color(0xffffffff))
        Row(){Icon(tint = Color(0xffffffff),imageVector = Icons.Filled.KeyboardArrowLeft,contentDescription = "Minus", modifier=Modifier.size(30.dp).clickable{
            time.value-=0.15
            time.value = (time.value*1000).roundToInt().toDouble()/1000.00
            mainViewModel.time_of_approach.value = time.value
        })
            Column(){
                Text(text="${time.value}", modifier = Modifier.align(Alignment.CenterHorizontally))
                Text(text = "minutes", modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            Icon(tint = Color(0xffffffff),imageVector = Icons.Filled.KeyboardArrowRight,contentDescription = "Plus", modifier=Modifier.size(30.dp).clickable{
                time.value+=0.15
                time.value = (time.value*1000).roundToInt().toDouble()/1000.00
                mainViewModel.time_of_approach.value = time.value
            })}
    }
}

@Composable
fun Delay_time(navController: NavHostController,mainViewModel: MainViewModel){
    var time = remember{ mutableStateOf(0.0) }
    Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.padding(top=15.dp)){
        Text(text="Длительность подхода:", fontSize = 15.sp, fontFamily = merriweatherFontFamily, color = Color(0xffffffff))
        Row(){Icon(tint = Color(0xffffffff),imageVector = Icons.Filled.KeyboardArrowLeft,contentDescription = "Minus", modifier=Modifier.size(30.dp).clickable{
            time.value-=0.15
            time.value = (time.value*1000).roundToInt().toDouble()/1000.00
            mainViewModel.delay_time.value = time.value
        })
            Column(){
                Text(text="${time.value}", modifier = Modifier.align(Alignment.CenterHorizontally))
                Text(text = "minutes", modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            Icon(tint = Color(0xffffffff),imageVector = Icons.Filled.KeyboardArrowRight,contentDescription = "Plus", modifier=Modifier.size(30.dp).clickable{
                time.value+=0.15
                time.value = (time.value*1000).roundToInt().toDouble()/1000.00
                mainViewModel.delay_time.value = time.value
            })}
    }
}

@Composable
fun Rounds(navController: NavHostController,mainViewModel: MainViewModel){
    var time = remember{ mutableStateOf(0) }
    Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.padding(top=15.dp)){
        Row(){Text(text="Количество подходов:", fontSize = 15.sp, fontFamily = merriweatherFontFamily, color = Color(0xffffffff))
            Icon(tint = Color(0xffffffff),imageVector = Icons.Filled.KeyboardArrowLeft,contentDescription = "Minus", modifier=Modifier.size(30.dp).clickable{
                time.value-=1
                mainViewModel.rounds.value = time.value
            })
            Text(text="${time.value}")
            Icon(tint = Color(0xffffffff),imageVector = Icons.Filled.KeyboardArrowRight,contentDescription = "Plus", modifier=Modifier.size(30.dp).clickable{
                time.value+=1
                mainViewModel.rounds.value = time.value
            })}
    }
}

@Composable
fun SinglePicker(mainViewModel: MainViewModel){
    var selectedImageUri by remember{
        mutableStateOf<Uri?>(null)
    }
    Log.d(null, selectedImageUri.toString())
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {uri -> selectedImageUri = uri
        mainViewModel.image.value = uri.toString()})
    Row(){
        Text(text = "Выберите фотографию из галереи:",
        modifier = Modifier.align(Alignment.CenterVertically),
        fontSize = 15.sp, fontFamily = merriweatherFontFamily, color = Color(0xffffffff))
        Box(modifier = Modifier.size(84.dp)){
        Image(painter = painterResource(id =R.drawable.football),
            contentDescription = "Image",contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(84.dp)
                .padding(8.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .align(alignment = Alignment.Center).clickable { photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        )
        AsyncImage(
            model = selectedImageUri,
            contentDescription = "Icon",contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(84.dp)
                .padding(8.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .align(alignment = Alignment.Center)
        )
    }}

}

@Composable
fun Atributes(navController: NavHostController,mainViewModel: MainViewModel ){
    var themes = listOf("Одиночное", "Командное", "Совместное", "Ловкость", "Удар","Пас")
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 15.dp)){
        val selectedOption = remember{ mutableStateOf(themes[0]) }
        Text(text = "Выберите категорию:",
            fontFamily = merriweatherFontFamily,
            fontSize = 15.sp,
            color = Color(0xffffffff))
        LazyHorizontalGrid(rows = GridCells.Fixed(2),modifier = Modifier.height(100.dp), contentPadding = PaddingValues(10.dp)){
            items(themes){theme->
                val selected = selectedOption.value == theme
                var color: Color
                if(selected)
                    color = Color.Black
                else
                    color = Color(0xff8a8a8a)
                Box(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .height(50.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = Color(0xffbfbfbf))
                        .border(
                            border = BorderStroke(2.dp, color),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .selectable(
                            selected = selected,
                            onClick = {
                                selectedOption.value = theme
                                mainViewModel.benefits.value += "$theme "
                            }
                        )
                ){
                    Text(
                        text = "$theme",
                        color = Color.Black.copy(alpha = 0.25f),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = merriweatherFontFamily
                        ),
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 6.dp)
                            .align(alignment = Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun Difficulty(navController: NavHostController,mainViewModel: MainViewModel ){
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Показать меню")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Text("Легкая", fontSize=18.sp, modifier = Modifier.padding(10.dp))
            Text("Средняя", fontSize=18.sp, modifier = Modifier.padding(10.dp))
            Text("Сложная", fontSize=18.sp, modifier = Modifier.padding(10.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun setVideoId(modifier: Modifier = Modifier, mainViewModel: MainViewModel) {
        var text = remember{ mutableStateOf("Add link to YouTube...") }
        OutlinedTextField(
            value = "",
            onValueChange = {it -> mainViewModel.video.value = it
                            text.value = it},
            label = {
                Text(
                    text = text.value,
                    color = Color(0xffd5d5d5),
                    lineHeight = 1.33.em,
                    style = TextStyle(
                        fontSize = 15.sp))
            },
            modifier = modifier
                .requiredWidth(width = 330.dp)
                .requiredHeight(height = 50.dp))

}

@Composable
fun Equipment(mainViewModel: MainViewModel){
        var themes = listOf("Конусы", "Мяч", "Скакалка", "Перчатки", "Ворота")
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)){
            val selectedOption = remember{ mutableStateOf(themes[0]) }
            Text(text = "Выберите необходимое снаряжение:",modifier = Modifier.padding(bottom=10.dp))
            LazyHorizontalGrid(rows = GridCells.Fixed(2),modifier = Modifier.height(70.dp), contentPadding = PaddingValues(vertical = 10.dp)){
                items(themes){theme->
                    val selected = selectedOption.value == theme
                    var color: Color
                    if(selected)
                        color = Color.Cyan
                    else
                        color = Color(0xff8a8a8a)
                    Box(
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = color)
                            .border(
                                border = BorderStroke(2.dp, Color(0xff8a8a8a)),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .selectable(
                                selected = selected,
                                onClick = {
                                    selectedOption.value = theme
                                    mainViewModel.equipment.value = theme
                                }
                            )
                    ){
                        Text(
                            text = "$theme",
                            color = Color.Black.copy(alpha = 0.25f),
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.Center)
                                .padding(horizontal = 15.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
}

@Composable
fun SetColor(mainViewModel: MainViewModel) {
    var themes = listOf(Color.Gray, Color.Cyan, Color.Blue, Color.Red, Color.Green)
    val selectedOption = remember{ mutableStateOf(themes[0]) }
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text = "Выберите цвета")

        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            modifier = Modifier.height(70.dp),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            items(themes) { theme ->
                val selected = selectedOption.value == theme
                var alpha: Float
                if (selected)
                    alpha = 1.0f
                else
                    alpha = 0.5f
                Canvas(modifier = Modifier
                    .size(20.dp)
                    .selectable(
                        selected = selected,
                        onClick = {
                            selectedOption.value = theme
                            mainViewModel.colors.value = theme.toArgb()
                        }
                    ) , onDraw = {
                    drawCircle(theme, alpha = alpha)
                })
            }
        }
    }
}

@Composable
fun SetArrow(mainViewModel: MainViewModel) {
    var themes = listOf(0, 1, 2)
    val myDictionary: MutableMap<Int, ImageVector> = mutableMapOf()
    myDictionary.put(0, Icons.Filled.KeyboardArrowLeft)
    myDictionary.put(1, Icons.Filled.KeyboardArrowRight)
    myDictionary.put(2, Icons.Filled.KeyboardArrowUp)
    val selectedOption = remember{ mutableStateOf(myDictionary[0]) }
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text = "Выберите цвета")
        LazyHorizontalGrid(rows = GridCells.Fixed(1),modifier = Modifier.height(70.dp), contentPadding = PaddingValues(vertical = 10.dp)){
            items(themes) { theme ->
                val selected = selectedOption.value == myDictionary[theme]
                var alpha: Float
                if (selected)
                    alpha = 1.0f
                else
                    alpha = 0.5f
                Icon(imageVector = myDictionary[theme]!!, contentDescription = "Arrow",modifier = Modifier.selectable(
                    selected = selected,
                    onClick = {
                        selectedOption.value = myDictionary[theme]
                        mainViewModel.arrows.value = theme
                    }
                ))
            }
        }
    }
}