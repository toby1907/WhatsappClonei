package com.example.whatsappclonei.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsappclonei.R
import com.example.whatsappclonei.ui.theme.WhatsappCloneiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentScreen() {
    // A state variable to track if the card is visible or not
    val cardVisible = remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            // A bottom app bar with a floating action button
            BottomAppBar() {
                // An attachment icon that toggles the card visibility
                IconButton(onClick = { cardVisible.value = !cardVisible.value }) {
                    Icon(
                        painter = painterResource(id = R.drawable.attach),
                        contentDescription = "Attachment"
                    )
                }
            }
        },
        floatingActionButton = {
            // A floating action button that also toggles the card visibility
            // Add a shape parameter with CircleShape
            FloatingActionButton(
                onClick = { cardVisible.value = !cardVisible.value },
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.attach),
                    contentDescription = "Attachment"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        // A conditional statement to show the card only if cardVisible is true
        if (cardVisible.value) {
          Row(modifier= Modifier.fillMaxSize(),
              verticalAlignment = Alignment.Bottom,
              horizontalArrangement = Arrangement.Center)  {
                Card(
                    onClick = { /* Do something */ },
                    modifier = Modifier
                        // Use a fixed size modifier with 375 x 291
                        .size(width = 375.dp, height = 291.dp)
                        .align(Alignment.Bottom)
                        .padding(it)
                ) {
                    // A lazy vertical grid with 3 items in a row and scrollable
                    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                        items(7) { index ->
                            // A list of icons and texts for each item
                            val icons = listOf(
                                R.drawable.document,
                                R.drawable.camera,
                                R.drawable.gallery,
                                R.drawable.audio,
                                R.drawable.location,
                                R.drawable.person,
                                R.drawable.poll,

                                )
                            val texts = listOf(
                                "Document",
                                "Camera",
                                "Gallery",
                                "Audio",
                                "Location",
                                "Contact",
                                "Poll",
                            )
                            val colors = listOf(
                                Color(0xFF1EBE71),
                                Color(0xFFF5D941),
                                Color(0xFF1E98BE),
                                        Color(0xFF8BBE1E),
                                        Color(0xFFDB3227),
                                Color(0xFFFFA800),
                                Color(0xFFF5D941)

                            )
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                IconButton(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .background(shape = CircleShape, color =colors[index] ),
                                    onClick = { /* Do something */ }) {
                                    Icon(
                                        modifier =Modifier
                                            .padding(4.dp)
                                            .size(24.dp),
                                        painter = painterResource(id = icons[index]),
                                        tint = Color.White,
                                        contentDescription = texts[index]
                                    )
                                }
                                Text(texts[index], Modifier.padding(top = 4.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AttachmentPreview() {
    WhatsappCloneiTheme {
        AttachmentScreen()
    }
}