package com.cs4520.assignment4

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs4520.assignment4.view.LoginCompose
import com.cs4520.assignment4.view.ProductListCompose
import com.cs4520.assignment4.viewmodel.MVVMProductViewModel

@Composable
fun Navigation(viewModel : MVVMProductViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginCompose(navController)
        }
        
        composable("productList") {
            ProductListCompose(viewModel = viewModel)
        }
    }
}