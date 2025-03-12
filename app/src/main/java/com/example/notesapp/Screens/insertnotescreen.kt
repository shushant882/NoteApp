package com.example.notesapp.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun Insertnotescreen(navHostController: NavHostController){
    val title = remember{ mutableStateOf("") }
    val description = remember{ mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()
    val notesCollection = db.collection("notes")
    val context = LocalContext.current
    Scaffold(floatingActionButton = {
        FloatingActionButton(contentColor = Color.White,
            containerColor = Color.Red,
            elevation = FloatingActionButtonDefaults.elevation(10.dp),
            onClick = {  if (title.value.isNotEmpty() && description.value.isNotEmpty()){
            navHostController.navigate(Sealed.HomeScreen.route)
                val note = hashMapOf(
                    "title" to title.value,
                    "description" to description.value

                )
                notesCollection.add(note).addOnSuccessListener {
                    Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                    title.value = ""
                    description.value = ""
                }.addOnFailureListener {
                    Toast.makeText(context, "Failed to add note", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(context, "Please enter title and description", Toast.LENGTH_SHORT).show()
            }
    }
        ){
            Icon(imageVector = Icons.Default.Done, contentDescription = null, tint = Color.White)
        }
    }) { innerpadding->
        Box(modifier = Modifier.padding(innerpadding)
            .fillMaxSize()
            .background(color= Color.Black)){

            Column (modifier = Modifier.padding(15.dp)){
                Text(text="Insert Data",color=Color.White, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, fontSize = 29.sp)
                Spacer(modifier = Modifier.height(15.dp))
                TextField(colors =TextFieldDefaults.colors(focusedContainerColor = Color.Gray, unfocusedContainerColor = Color.Gray),shape= RoundedCornerShape(corner = CornerSize(19.dp)),label={Text(text="Enter Your Title", fontSize = 19.sp, color = Color.Black) },value = title.value, onValueChange = {title.value=it}, modifier = Modifier.fillMaxWidth().fillMaxHeight(.1f))
                Spacer(modifier = Modifier.height(25.dp))
                TextField(colors =TextFieldDefaults.colors(focusedContainerColor = Color.Gray, unfocusedContainerColor = Color.Gray),shape= RoundedCornerShape(corner = CornerSize(19.dp)),label={Text(text="Enter Your Notes", fontSize = 19.sp, color = Color.Black) },value = description.value, onValueChange = {description.value=it}, modifier = Modifier.fillMaxWidth().fillMaxHeight(.6f))


            }
        }
    }
}