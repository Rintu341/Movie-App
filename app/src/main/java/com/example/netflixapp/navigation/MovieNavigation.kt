package com.example.netflixapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.netflixapp.screens.details.DetailsScreen
import com.example.netflixapp.screens.home.HomeScreen

@Composable
fun MovieNavigation(){
    val navController = rememberNavController()  // 1st component

    NavHost(navController = navController, startDestination = MovieScreens.HomeScreen.name ){ // 2nd Component // It host each navigation graph component or item
        // start destination shows first screen of our Screen
        composable(route = MovieScreens.HomeScreen.name){ // This is one of the Navigation Graph item
            // Here we call actual composable
            HomeScreen(navController)
        }
        composable(route = MovieScreens.DetailsScreen.name + "/{movie}",
            arguments = listOf(navArgument(name = "movie") {type = NavType.StringType})){
            navBackStackEntry ->
                DetailsScreen(navController = navController,
                    navBackStackEntry.arguments?.getString("movie"))
        }

    }
}