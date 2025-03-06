package com.example.dsw53518_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dsw53518_kotlin.view.LoginPage
import androidx.navigation.compose.rememberNavController
import com.example.dsw53518_kotlin.utils.Routes
import com.example.dsw53518_kotlin.view.RegisterPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.loginPage, builder = {
                composable(Routes.loginPage) {
                    LoginPage(navController)
                }
                composable(route = Routes.registerPage) {
                    RegisterPage()
                }
            })
        }
    }
}