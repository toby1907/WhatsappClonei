package com.example.whatsappclonei.ui.status.add_status
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.R
import com.example.whatsappclonei.data.model.Status
import com.example.whatsappclonei.ui.theme.PrimaryGreen
import com.example.whatsappclonei.ui.theme.White

@Composable
fun CreateStatusScreen(
    onCancelClick: () -> Unit,
    onTextClick: () -> Unit,
    onPaletteClick: () -> Unit,
    onVideoClick: () -> Unit,
    onPhotoClick: () -> Unit,
    onMicClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top Bar
        // Status Text Input
        var text by remember { mutableStateOf(TextFieldValue("")) }
        var backgroundColor by remember {
            mutableStateOf(Status.noteColors[0]) }
        var textStyleState by remember { mutableStateOf(TextStyleState.Neutral) }
        val textStyle = when (textStyleState) {
            TextStyleState.Neutral -> TextStyle(
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            TextStyleState.Italic -> TextStyle(
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            TextStyleState.Bold -> TextStyle(
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }





        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onCancelClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Cancel",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    onTextClick()
                    textStyleState = when (textStyleState) {
                        TextStyleState.Neutral -> TextStyleState.Italic
                        TextStyleState.Italic -> TextStyleState.Bold
                        TextStyleState.Bold -> TextStyleState.Neutral
                    }
                }) {
                    Text(
                        text = "T",
                        style = textStyle
                    )
                }
                IconButton(onClick = { onPaletteClick()
                    backgroundColor = Status.noteColors.random()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.palete_icon),
                        contentDescription = "Palette",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                textStyle = textStyle,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (text.text.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.type_a_status),
                            color = Color.Gray,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                        /*Text(
                            text = "Type a status",
                            style = TextStyle(
                                fontSize = 38.sp,
                                lineHeight = 46.sp,
                                fontWeight = FontWeight(500),
                                color = Color(0x66FFFFFF),

                                textAlign = TextAlign.Center,
                            )
                        )*/
                    }
                    innerTextField()
                }
            )
        }

        // Bottom Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable(onClick = onVideoClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_video),
                        contentDescription = "Video",
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable(onClick = onPhotoClick),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_photo),
                        contentDescription = "Photo",
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .clickable(onClick = onTextClick),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "T",
                        style = TextStyle(
                            fontSize = 22.5.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF000000),

                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen)
                    .clickable(onClick = onMicClick),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mic_icon),
                    contentDescription = "Mic",
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}