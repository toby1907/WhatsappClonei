package com.example.whatsappclonei.components.countrycodepicker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.whatsappclonei.components.countrycodepicker.model.CountryCode
import com.example.whatsappclonei.components.countrycodepicker.utils.getFlagMasterResID
import com.example.whatsappclonei.components.countrycodepicker.utils.getLibCountries
import com.example.whatsappclonei.components.countrycodepicker.utils.searchCountryList
import com.example.whatsappclonei.ui.onboarding.phone_no.ValidationViewModel

/*
@Preview
    @Composable
    private fun PreviewCountryCodeDialog() {
        CountryCodeDialog(
            pickedCountry = {},
            defaultSelectedCountry = getLibCountries().single { it.countryCode == "us" },
        )
    }

    @Preview
    @Composable
    private fun PreviewCountryCodeDialogNoIconReducedPadding() {
      Column (
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
          modifier = Modifier.fillMaxSize()

      )  {
            CountryCodeDialog(
                pickedCountry = {},
                defaultSelectedCountry = getLibCountries().single { it.countryCode == "us" },
                isOnlyFlagShow = false,
                isShowIcon = true,
                padding = 2.dp
            )
        }
    }*/

    @Composable
    fun CountryCodeDialog(
        modifier: Modifier = Modifier,
        padding: Dp = 15.dp,
        textColor: Color = Color.Black,
        backgroundColor: Color = Color.White,
        isOnlyFlagShow: Boolean = false,
        isShowIcon: Boolean = true,
        defaultSelectedCountry: CountryCode = getLibCountries().first(),
        pickedCountry: (CountryCode) -> Unit,
        dialogSearch: Boolean = true,
        dialogRounded: Int = 12,
        dialogTextColor: Color = Color.Black,
        dialogSearchHintColor: Color = Color.Gray,
        dialogTextSelectColor: Color = Color(0xff3898f0),
        dialogBackgroundColor: Color = Color.White,
        isCountryIconRounded: Boolean = false,
        viewModel: ValidationViewModel
    ) {
        val countryList: List<CountryCode> = getLibCountries()
        var isPickCountry by remember { mutableStateOf(defaultSelectedCountry) }
        var isOpenDialog by remember { mutableStateOf(false) }
        var searchValue by remember { mutableStateOf("") }

        LaunchedEffect(isPickCountry) {
            viewModel.onCountryCodeChanged(isPickCountry)
        }
        Card(
            modifier = modifier
                .padding(3.dp)
                .clickable { isOpenDialog = true },
            colors = CardDefaults.cardColors(
                backgroundColor
            ),
        ) {
            Column(modifier = Modifier.padding(padding)) {

                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(
                            id = getFlagMasterResID(
                                isPickCountry.countryCode
                            )
                        ), contentDescription = null,
                        modifier =
                        if (isCountryIconRounded) {
                            Modifier
                                .size(28.dp)
                                .clip(shape = RoundedCornerShape(100.dp))
                        } else {
                            Modifier.size(width = 36.dp, height = 22.dp)
                        },
                        contentScale = ContentScale.Crop
                    )
                    if (!isOnlyFlagShow) {
                        Text(
                            "${isPickCountry.countryPhoneCode}",
                            //"${isPickCountry.countryName}"
                            Modifier.padding(horizontal = 18.dp),
                            color = textColor
                        )
                    }
                    if (isShowIcon) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            }

            //Dialog
            if (isOpenDialog) {
                Dialog(
                    onDismissRequest = { isOpenDialog = false }, )
                {
                    Surface(
                      modifier =  Modifier
                          .fillMaxWidth()
                          .fillMaxHeight(0.85f),
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp,
                        color = dialogBackgroundColor,
                        shape = RoundedCornerShape(dialogRounded.dp),
                    ) {
                        Column {

                            if (dialogSearch) {
                                searchValue = DialogSearchView(
                                    textColor = dialogTextColor,
                                    hintColor = dialogSearchHintColor,
                                    textSelectColor = dialogTextSelectColor,
                                )
                            }
                            LazyColumn {
                                items(
                                    (if (searchValue.isEmpty()) {
                                        countryList
                                    } else {
                                        countryList.searchCountryList(searchValue)
                                    })
                                ) { countryItem ->
                                    Row(
                                        Modifier
                                            .padding(
                                                horizontal = 18.dp,
                                                vertical = 18.dp
                                            )
                                            .clickable {
                                                pickedCountry(countryItem)
                                                isPickCountry = countryItem
                                                isOpenDialog = false
                                            }) {
                                        Image(
                                            painter = painterResource(
                                                id = getFlagMasterResID(
                                                    countryItem.countryCode
                                                )
                                            ), contentDescription = null,
                                            if (isCountryIconRounded) {
                                                Modifier
                                                    .size(28.dp)
                                                    .clip(shape = RoundedCornerShape(100.dp))
                                            } else {
                                                Modifier.size(width = 36.dp, height = 22.dp)
                                            },
                                            contentScale = ContentScale.Crop
                                        )
                                        Text(
                                            countryItem.countryName,
                                            Modifier.padding(horizontal = 18.dp),
                                            color = dialogTextColor,
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    @Composable
    private fun DialogSearchView(
        textColor: Color,
        hintColor: Color,
        textSelectColor: Color,
    ): String {
        var searchValue by remember { mutableStateOf("") }
        Row {
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                value = searchValue,
                onValueChange = {
                    searchValue = it
                },
                fontSize = 14.sp,
                hint = "Search ...",
                textColor = textColor,
                hintColor = hintColor,
                textSelectColor = textSelectColor,
                textAlign = TextAlign.Start,
            )
        }
        return searchValue
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun CustomTextField(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
        hint: String = "",
        fontSize: TextUnit = 16.sp,
        textColor: Color,
        hintColor: Color,
        textSelectColor: Color,
        textAlign: TextAlign = TextAlign.Center
    ) {
        Box(modifier) {
            CompositionLocalProvider(
                LocalTextSelectionColors provides TextSelectionColors(
                    handleColor = textSelectColor,
                    backgroundColor = textSelectColor.copy(0.2f),
                )
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = textAlign,
                        fontSize = fontSize,
                    ),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = textColor.copy(0.2f)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor =  textColor,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = textSelectColor,
                    )
                )
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodySmall,
                        color = hintColor,
                        modifier = Modifier.then(
                            Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 52.dp)
                        )
                    )
                }
            }
        }
    }
