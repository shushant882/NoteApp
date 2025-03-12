package com.example.notesapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun Notesscreen(navHostController: NavHostController) {
    val db = FirebaseFirestore.getInstance()
    val notesCollection = db.collection("notes")
    val notesList = remember {
        mutableStateListOf<ListItem>()
    }
    val datavalue=remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        notesCollection.addSnapshotListener { value, error ->
            if (error != null) {
                // Handle error if needed
                datavalue.value=false
                return@addSnapshotListener


            }else{
            notesList.clear()
                value?.documents?.forEach { document ->
                    val note = document.toObject(ListItem::class.java)
                    if (note != null) {
                        note.id = document.id   // <---- Set the document ID here
                        notesList.add(note)
                    }
                }
                datavalue.value=true}
        }
    }
    Scaffold(floatingActionButton = {
        FloatingActionButton(
            contentColor = Color.White,
            containerColor = Color.Red,
            elevation = FloatingActionButtonDefaults.elevation(10.dp),
            onClick = {
                navHostController.navigate(Sealed.InsertNotesScreen.route)
            }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
    }) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.Black)
        ) {

            Column {
                val uriHandler = LocalUriHandler.current
                Text(
                    text = "Create Notes\nCrud",
                    fontFamily = FontFamily.Serif,
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(text="Made By Shushant",fontFamily = FontFamily.Serif,
                    fontSize = 20.sp, textDecoration = TextDecoration.Underline,
                    color = Color.White,modifier = Modifier.clickable {
                        uriHandler.openUri("https://www.linkedin.com/in/shushant-974402281/")})
                if(datavalue.value){
                    LazyColumn {
                        items(notesList) { item ->
                            NoteItem(item, notesCollection,navHostController)
                        }
                    }
                }
                else{
                    Box(modifier=Modifier.fillMaxSize()){
                        CircularProgressIndicator(modifier = Modifier.size(25.dp).align(Alignment.Center))
                    }
                }

            }
        }
    }
}

// New composable for making list items visible and making box
@Composable
fun NoteItem(item: ListItem, notesCollection: CollectionReference,navHostController: NavHostController) {
    var expanded by  remember { mutableStateOf(false) }
    val context= LocalContext.current
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = "Delete Note") },
            text = { Text(text = "Are you sure you want to delete this note?") },
            confirmButton = {
                Text(text = "Delete", color = Color.Red, modifier = Modifier.clickable {
                    notesCollection.document(item.id).delete()
                    showDeleteDialog = false
                })
            },
            dismissButton = {
                Text(text = "Cancel", color = Color.Gray, modifier = Modifier.clickable {
                    showDeleteDialog = false
                })
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(9.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp)))
            .background(color = Color.DarkGray)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White),
            offset = DpOffset(x = (-40).dp, y = 0.dp)
        ) {
            DropdownMenuItem(text = { Text("Update", color = Color.Black) }, onClick = { navHostController.navigate(Sealed.InsertNotesScreen.route)
                expanded = false })
            DropdownMenuItem(text = { Text("Delete", color = Color.Black) }, onClick = {
                expanded = false
                showDeleteDialog = true
            })
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopEnd)
                .clickable { expanded = true }
        )
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = item.title,
                color = Color.White,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = item.description,
                color = Color.Gray,
                fontSize = 16.sp
            )

        }
    }

}
