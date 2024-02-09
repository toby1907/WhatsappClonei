package com.example.whatsappclonei.ui.voice_recorder

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.whatsappclonei.ui.theme.WhatsappCloneiTheme
import com.example.whatsappclonei.ui.theme.Yellow
import com.example.whatsappclonei.ui.theme.YellowVariant

@Composable
fun SleepBar(
   /* sleepData: SleepDayData,
    modifier: Modifier = Modifier*/
) {
    val textMeasurer = rememberTextMeasurer()
    Spacer(
        modifier = Modifier
            .drawWithCache {
                /* val brush =
                    Brush.verticalGradient(listOf(Yellow, YellowVariant))*/
                val textResult = textMeasurer.measure(AnnotatedString(""))

                onDrawBehind {
                    /*    drawRoundRect(brush = brush, cornerRadius = CornerRadius(10.dp.toPx()))*/
                    drawText(textResult)
                }
            }
            .fillMaxWidth()
            .height(24.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun CustomDrawPreview() {
    WhatsappCloneiTheme {
        SleepBar()
    }
}
