package com.example.dsw53518_kotlin

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dsw53518_kotlin.utils.Routes
import com.example.dsw53518_kotlin.view.HomePage
import com.example.dsw53518_kotlin.view.LoginPage
import com.example.dsw53518_kotlin.view.RegisterPage
import com.example.dsw53518_kotlin.view.RemindersPage
import com.example.dsw53518_kotlin.viewmodel.AuthViewModel
import com.example.dsw53518_kotlin.viewmodel.ReminderViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val reminderViewModel: ReminderViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.loginPage, builder = {
        composable(Routes.loginPage) {
            LoginPage(navController, authViewModel)
        }
        composable(route = Routes.registerPage) {
            RegisterPage(navController, authViewModel = authViewModel)
        }
        composable(route = Routes.homePage) {
            HomePage(modifier, navController, authViewModel = authViewModel)
        }
        composable(route = Routes.todoPage) {
            RemindersPage(reminderViewModel, navController)
        }
    })
}