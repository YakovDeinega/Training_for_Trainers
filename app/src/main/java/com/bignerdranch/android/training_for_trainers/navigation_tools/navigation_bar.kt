package com.bignerdranch.android.training_for_trainers.navigation_tools

import Calendar
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bignerdranch.android.training_for_trainers.Exercise_Info
import com.bignerdranch.android.training_for_trainers.Exercises
import com.bignerdranch.android.training_for_trainers.Home
import com.bignerdranch.android.training_for_trainers.Post
import com.bignerdranch.android.training_for_trainers.Profile
import com.bignerdranch.android.training_for_trainers.Tactic_Panel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavRoutes.Home.route){
        composable(NavRoutes.Home.route) { Home(navController) }
        composable(NavRoutes.Calendar.route) { Calendar(navController) }
        composable(NavRoutes.Post.route){ Post(navController) }
        composable(NavRoutes.Tactic_Panel.route){Tactic_Panel(navController)}
        composable(NavRoutes.Profile.route){Profile(navController)}
        composable(NavRoutes.Exercises.route + "/{theme}"){ stackEntry ->
            val theme = stackEntry.arguments?.getString("theme")
            Exercises(navController,theme)}
        composable(NavRoutes.Exercises_Info.route + "/{item.toString()}",
            arguments = listOf(
                navArgument(name="item.toString()"){
                    type = NavType.StringType
                }
            )) { backstackEntry ->
            Exercise_Info(navController = navController,
                str = backstackEntry.arguments?.getString("item.toString()"))
        }
    }


}


@Composable
fun BottomNavigationBar(navController: NavController){
    NavigationBar(modifier = Modifier.height(50.dp)) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute==navItem.route,
                onClick = {
                    navController.navigate(navItem.route){
//                              popUpTo(navController.graph.findStartDestination().id)
//                              launchSingleTop = true
//                              restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = navItem.image,
                        contentDescription = navItem.title)
                })
        }
    }
}
object NavBarItems{
    val BarItems = listOf(
        BarItem(
            title="Home",
            image= Icons.Filled.Home,
            route="home"
        ),
        BarItem(
            title="Calendar",
            image= Icons.Filled.Notifications,
            route="calendar"
        ),
        BarItem(
            title="Post",
            image= Icons.Filled.Add,
            route="post"
        ),
        BarItem(
            title="Tactic Panel",
            image= Icons.Filled.Delete,
            route="tactic_panel"
        ),
        BarItem(
            title="Profile",
            image= Icons.Filled.Person,
            route="profile"
        )
    )
}
data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String
)
sealed class NavRoutes(val route: String){
    object Home: NavRoutes("home")
    object Calendar: NavRoutes("calendar")
    object Post: NavRoutes("post")
    object Tactic_Panel: NavRoutes("tactic_panel")
    object Profile: NavRoutes("profile")
    object Exercises: NavRoutes("exercises")
    object Exercises_Info: NavRoutes("exercises_info")
}