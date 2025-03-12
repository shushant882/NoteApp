package com.example.notesapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notesapp.R
import com.example.notesapp.ui.theme.Fire
import com.example.notesapp.ui.theme.Orange


@Composable
fun SplashScreen(navHostController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize().background(color= Color.Black), contentAlignment = Alignment.Center){
        Column(modifier = Modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){Image(painter = painterResource(id = R.drawable.firebase_02), contentDescription = null)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text="FireBase", color = Fire, fontSize = 35.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)
        Text(text="NotesApp", color = Orange, fontSize = 35.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif)

        }

}
LaunchedEffect(Unit){
    kotlinx.coroutines.delay(2500)
    navHostController.navigate(Sealed.HomeScreen.route){
        popUpTo(Sealed.SplashScreen.route){
            inclusive=true
        //this is to delete the splash screen from the backstack
        }
    }
}
}