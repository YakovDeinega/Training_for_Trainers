package com.bignerdranch.android.training_for_trainers

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.initialize
val email = "example@email.com"
val password = "password"

@Composable
fun Profile(navController: NavController){
    var firebaseApp = Firebase.initialize(context = LocalContext.current)
    Box(modifier = Modifier.size(50.dp).clickable { authenticateUser() })
}
fun registerUser() {

    // Создание нового пользователя
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
}
fun authenticateUser() {
    // Аутентификация пользователя
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w(
                "FirebaseAuth",
                "Не удалось аутентифицировать пользователя: ${task.exception?.localizedMessage}"
            )
        }
    }
}

