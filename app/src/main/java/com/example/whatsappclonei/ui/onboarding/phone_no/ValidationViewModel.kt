package com.example.whatsappclonei.ui.onboarding.phone_no

import android.content.Context
import android.util.Log
import androidx.activity.result.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsappclonei.Constants.TAG
import com.example.whatsappclonei.components.countrycodepicker.model.CountryCode
import com.example.whatsappclonei.components.countrycodepicker.utils.getLibCountries
import com.example.whatsappclonei.data.AuthRepository
import com.example.whatsappclonei.data.AuthRepositoryImpl
import com.example.whatsappclonei.data.model.Response
import com.example.whatsappclonei.ui.onboarding.validation.Country
import com.example.whatsappclonei.ui.onboarding.validation.JsonParser
import com.example.whatsappclonei.ui.onboarding.validation.PhoneValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidationViewModel @Inject constructor(
    private val jsonParser: JsonParser,
    private val repository: AuthRepository
) : ViewModel() {

    var countries: ArrayList<Country> = ArrayList()
        private set
    var validationResult by mutableStateOf<ValidationResult>(ValidationResult.None)
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var selectedCountryCode by mutableStateOf<CountryCode>(getLibCountries().first())
        private set

    var verificationId by mutableStateOf("")
        private set
    var isCodeSent by mutableStateOf(false)
        private set
    var signInResponse by mutableStateOf<Response<Boolean>>(Response.Loading)
        private set

    var verificationCode by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            // Parse the JSON data in a background coroutine
            val jsonArray = jsonParser.readDataFromRawFile()
            countries = jsonParser.parseCountry(jsonArray)

            Log.d("countries", "countries: $countries")
        }
    }
    fun validatePhoneNumber() {
        // Find the matching country
        val country = findCountryByDialCode(selectedCountryCode.countryPhoneCode)

        if (country == null) {
            validationResult = ValidationResult.InvalidCountry
            return
        }

        // Validate the phone number
        Log.d("country","${country},${selectedCountryCode.countryCode},${phoneNumber}")
        val isValid = PhoneValidation.validPhoneNumber(country, selectedCountryCode.countryPhoneCode, phoneNumber)

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
    fun onverificationCodeEntered(newVerificationCode : String){
        verificationCode = newVerificationCode
    }

    fun onCountryCodeChanged(newCountryCode: CountryCode) {
        selectedCountryCode = newCountryCode
    }
    // Function to initiate phone number verification
    fun sendVerificationCode() {
        viewModelScope.launch {
            repository.sendVerificationCode(
                phoneNumber = "+${selectedCountryCode.countryPhoneCode}$phoneNumber",
                onCodeSent = { verificationId ->
                    this@ValidationViewModel.verificationId = verificationId
                    isCodeSent = true
                },
                onVerificationFailed = { exception ->
                    Log.e(TAG, "onVerificationFailed: ${exception.message}")
                    isCodeSent = false
                },
                onCodeAutoRetrieved = { smsCode ->
                    Log.d(TAG, "onCodeAutoRetrieved: $smsCode")
                    signInWithPhoneAuthCredential(smsCode)
                }
            )
        }
    }
    // Function to sign in with the verification code
    fun signInWithPhoneAuthCredential(verificationCode: String) {
        viewModelScope.launch {
            signInResponse = repository.signInWithPhoneAuthCredential(verificationId, verificationCode)
        }
    }
    sealed class ValidationResult {
        object None : ValidationResult()
        object Valid : ValidationResult()
        object Invalid : ValidationResult()
        object InvalidCountry : ValidationResult()
        object Loading : ValidationResult()
    }
}