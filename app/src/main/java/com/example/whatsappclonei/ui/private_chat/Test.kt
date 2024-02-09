package com.example.whatsappclonei.ui.private_chat

import android.media.MediaRecorder
import android.os.Environment
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.whatsappclonei.ui.theme.WhatsappCloneiTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun VoiceChatRecorder() {
    var isRecording by remember { mutableStateOf(false) }
    var audioFilePath by remember { mutableStateOf("") }
    var recordingTime by remember { mutableStateOf(0) }
    var isDragging by remember { mutableStateOf(false) }
    var audioPath = ""
    val formatter = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault())
    val now = Date()
    val context = LocalContext.current

    val recorder = remember {
        MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        }
    }

    val startRecording = {

        audioPath = "${
            context.getDir(
                "WhatsappClonei",
                0
            )?.absolutePath
        }/Media+Recording+ ${System.currentTimeMillis()}+.3gp"



        recorder.setOutputFile(audioPath)
        recorder.apply {
            prepare()
            start()
        }
        isRecording = true
    }

    val stopRecording = {
        recorder.apply {
            stop()
            release()
        }
        isRecording = false
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Box(
            modifier = Modifier
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            if (!isRecording) {
                                isDragging = true
                                startRecording()
                            }
                        },
                        onDragEnd = {
                            if (isDragging) {
                                isDragging = false
                                stopRecording()
                            }
                        },
                        onDragCancel = {
                            if (isDragging) {
                                isDragging = false
                                stopRecording()
                            }
                        },
                        onDrag = { change, dragAmount ->
                            if (isDragging) {
                                recordingTime++
                                change.consumeAllChanges()
                            }
                        }
                    )
                }
        ) {
            Button(
                onClick = {
                    if (isRecording) stopRecording() else startRecording()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = if (isRecording) "Stop Recording" else "Hold to Record")
                    if (isDragging) {
                        Text(text = "$recordingTime s", modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }

            if (audioFilePath.isNotEmpty()) {
                Text(
                    text = "Audio file saved at $audioFilePath",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VoiceChatRecorderPreview() {
    WhatsappCloneiTheme {
        VoiceChatRecorder()
    }
}