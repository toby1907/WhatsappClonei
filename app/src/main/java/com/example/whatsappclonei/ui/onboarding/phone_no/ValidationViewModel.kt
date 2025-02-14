package com.example.whatsappclonei.ui.onboarding.phone_no

import android.content.Context
import androidx.activity.result.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.components.countrycodepicker.model.CountryCode
import com.example.whatsappclonei.components.countrycodepicker.utils.getLibCountries
import com.example.whatsappclonei.ui.onboarding.validation.Country
import com.example.whatsappclonei.ui.onboarding.validation.JsonParser
import com.example.whatsappclonei.ui.onboarding.validation.PhoneValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor(
    private val jsonParser: JsonParser,
) : ViewModel() {

    var countries: ArrayList<Country> = ArrayList()
        private set
    var validationResult by mutableStateOf<ValidationResult>(ValidationResult.None)
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var selectedCountryCode by mutableStateOf<CountryCode>(getLibCountries().first())
        private set

    init {
        viewModelScope.launch {
            // Parse the JSON data in a background coroutine
            val jsonArray = jsonParser.readDataFromRawFile()
            countries = jsonParser.parseCountry(jsonArray)
        }
    }
    fun validatePhoneNumber() {
        // Find the matching country
        val country = findCountryByDialCode(selectedCountryCode.countryCode)

        if (country == null) {
            validationResult = ValidationResult.InvalidCountry
            return
        }

        // Validate the phone number
        val isValid = PhoneValidation.validPhoneNumber(country, selectedCountryCode.countryCode, phoneNumber)

        validationResult = if (isValid) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid
        }
    }
    private fun findCountryByDialCode(dialCode: String): Country? {
        return countries.find { "+${it.countryCode}" == dialCode }
    }

    fun onPhoneNumberChanged(newPhoneNumber: String) {
        phoneNumber = newPhoneNumber
    }

    fun onCountryCodeChanged(newCountryCode: CountryCode) {
        selectedCountryCode = newCountryCode
    }
    sealed class ValidationResult {
        object None : ValidationResult()
        object Valid : ValidationResult()
        object Invalid : ValidationResult()
        object InvalidCountry : ValidationResult()
    }
}