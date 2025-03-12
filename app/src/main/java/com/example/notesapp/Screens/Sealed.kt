package com.example.notesapp.Screens

sealed class Sealed(val route: String) {
    object SplashScreen : Sealed("splash")
    object InsertNotesScreen : Sealed("insert")
    object HomeScreen : Sealed("home")

}