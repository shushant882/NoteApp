package com.example.notesapp.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun Notesnavigation(navHostController: NavHostController){
    NavHost(navController=navHostController,startDestination = "splash"){
        composable(Sealed.SplashScreen.route){SplashScreen(navHostController)}
        composable(Sealed.InsertNotesScreen.route){ Insertnotescreen(navHostController
        ) }

        composable(Sealed.HomeScreen.route){ Notesscreen(
            navHostController
        )}
    }

}