import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun Calendar(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff8a8a8a))
            .padding(8.dp)
    ) {
        Header()
        CalendarView()
        EventsList()
        AddEventButton()
    }
}

@Composable
fun Header() {
    Text(
        text = "December 2023",
        color = Color.White,
        fontSize = 24.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun CalendarView() {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val calendar = Calendar.getInstance()
    calendar.set(2024, 5, 20) // December 2023
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            days.forEach {
                Text(text = it, color = Color.White)
            }
        }
        for (week in 1..5) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (day in 1..7) {
                    if (week == 1 && day < firstDayOfWeek) {
                        Spacer(modifier = Modifier.size(24.dp))
                    } else {
                        val dayOfMonth = (week - 1) * 7 + day - (firstDayOfWeek - 1)
                        if (dayOfMonth > 0 && dayOfMonth <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            DayCell(dayOfMonth)
                        } else {
                            Spacer(modifier = Modifier.size(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DayCell(day: Int) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(Color.Gray, CircleShape)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$day", color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun EventsList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        EventItem("10:00–13:00", "Dribbling around cones", "Needs to improve a little bit dribbling", Color(0xFF6200EE))
        EventItem("13:45–14:00", "Eating Lunch", "", Color(0xFF03DAC5))
        EventItem("17:00–18:00", "Watching replays", "Last game was actually bad. Wants to know why so", Color(0xFFB00020))
    }
}

@Composable
fun EventItem(time: String, title: String, description: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp).background(color = color)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = time, color = Color.Gray, fontSize = 12.sp)
            Text(text = title, color = Color.White, fontSize = 16.sp)
            if (description.isNotEmpty()) {
                Text(text = description, color = Color.White, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun AddEventButton() {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Surface(modifier = Modifier.fillMaxSize(), color = Color.Black.copy(alpha = 0.8f)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text("Add Event", fontSize = 20.sp, color = Color.Black)
                        Spacer(modifier = Modifier.height(16.dp))
                        // Add form fields here
                        Button(onClick = { showDialog = false }) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }

    FloatingActionButton(
        onClick = { showDialog = true },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Event")
    }
}
