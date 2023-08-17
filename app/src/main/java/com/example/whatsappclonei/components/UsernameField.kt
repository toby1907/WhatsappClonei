package com.example.whatsappclonei.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.whatsappclonei.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameField(
    username: String,
    onUsernameValueChange: (newValue: String) -> Unit
) {
    OutlinedTextField(
        value = username,
        onValueChange = { newValue ->
            onUsernameValueChange(newValue)
        },
        label = {
            Text(
                text = Constants.USERNAME_LABEL
            )
        })
}