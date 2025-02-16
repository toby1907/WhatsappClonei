package com.example.whatsappclonei.ui.onboarding.phone_no

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.whatsappclonei.components.countrycodepicker.ui.CountryCodeDialog
import com.example.whatsappclonei.components.countrycodepicker.utils.getLibCountries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPhoneNoScreen( viewModel: ValidationViewModel = hiltViewModel()){

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
               title = {
                   Text(
                       text = "Phone number",
                       style = TextStyle(
                           fontSize = 17.sp,
                           lineHeight = 22.sp,
                           color = MaterialTheme.colorScheme.onBackground,
                           textAlign = TextAlign.Center,
                       )
                   )
               },
               actions = {
                   Button(
                       enabled = viewModel.phoneNumber.length >= 10,
                       onClick = {
                         if (viewModel.phoneNumber.length >= 10)  { viewModel.validatePhoneNumber() }
                       },
                       colors = ButtonDefaults.buttonColors(
                           containerColor = Color.Transparent,
                           contentColor = MaterialTheme.colorScheme.onBackground,
                           disabledContainerColor = Color.Transparent,
                           disabledContentColor = Color(0xFFD1D1D6),

                       )
                   ) {
                       Text(
                           text = "Done",
                           style = TextStyle(
                               fontSize = 17.sp,
                               lineHeight = 22.sp,
                               color = Color(0xFFD1D1D6),

                               textAlign = TextAlign.Right,
                           ))

                   }
               }
           )
        },
        content = { padding ->

                PhoneFieldContent(modifier = Modifier.padding(padding),viewModel)

        }
    )
}

@Composable
fun PhoneFieldContent(modifier: Modifier, viewModel: ValidationViewModel) {
    val phoneNumber = viewModel.phoneNumber
    Column(modifier = modifier
        .fillMaxSize(),
        verticalArrangement =   Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            text = "Please confirm your country code and enter your phone number ",
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        )
      Row ( verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp))   {
            CountryCodeDialog(
                pickedCountry = {},
                defaultSelectedCountry = getLibCountries().single { it.countryCode == "us" },
                isOnlyFlagShow = false,
                isShowIcon = true,
                padding = 2.dp,
                viewModel = viewModel
            )
            TextField(
                value = phoneNumber,
                onValueChange = {
                    viewModel.onPhoneNumberChanged(it)
                },
                modifier = Modifier.padding(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                placeholder = {
                    Text(
                        text = "81********",
                        style = TextStyle(
                            fontSize = 26.sp,
                            color = Color(0xFFC7C7CC),
                            )
                    )
                }
            )
        }

    }

}
/*
@Preview
@Composable
fun AddPhoneNoScreenPreview() {
    AddPhoneNoScreen()
}*/
