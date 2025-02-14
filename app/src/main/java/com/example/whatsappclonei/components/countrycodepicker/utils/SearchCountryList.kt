package com.example.whatsappclonei.components.countrycodepicker.utils

import com.example.whatsappclonei.components.countrycodepicker.model.CountryCode

fun List<CountryCode>.searchCountryList(key: String): MutableList<CountryCode> {
    val tempList = mutableListOf<CountryCode>()
    this.forEach {
        if (it.countryName.lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}