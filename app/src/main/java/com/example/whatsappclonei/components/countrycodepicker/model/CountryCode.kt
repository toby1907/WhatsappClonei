package com.example.whatsappclonei.components.countrycodepicker.model

import java.util.Locale


data class CountryCode(
    private var cCode: String,
    val countryPhoneCode: String = "",
    private val cName: String = "",
    val flagResID: Int = 0
) {
    val countryCode = cCode.lowercase(Locale.getDefault())
    val countryName = cName
}