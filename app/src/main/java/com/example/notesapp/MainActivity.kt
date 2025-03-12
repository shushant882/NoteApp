package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.Screens.Notesnavigation
import com.example.notesapp.ui.theme.NotesappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController= rememberNavController()
            Notesnavigation(navHostController)
        }
    }
}

//MAIN THING
// THIS IS CRUD FILE LINK (OVERVIEW OF APP DESIGN) https://www.figma.com/design/b0nNJ6g0knCVyVDtFah8La/Jetpack-CRUD?node-id=0-1&p=f&t=PQ8T67zHqRCyAct1-0
