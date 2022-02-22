package com.example.homeappdemo.util


import android.util.Patterns
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class Validator {

    companion object {

        // Default validation messages


        private val NAME_VALIDATION_MSG = """Name should be minimum 3 characters long
            |should have no number OR special character""".trimMargin()
        private val COUNTRY_VALIDATION_MSG = "Enter your country"
        private val CITY_VALIDATION_MSG = "Enter your city"
        private val STREET_VALIDATION_MSG = "Enter a valid street"
        private val STREETCODE_NAME_VALIDATION_MSG = "Enter a valid street code"
        private val POSTCODE_NAME_VALIDATION_MSG = "Enter 5 digit postal code"
        private val DEVICE_NAME_VALIDATION_MSG = "Enter valid device name"

        private fun getText(data: Any): String {
            var str = ""
            if (data is EditText) {
                str = data.text.trim().toString()
            } else if (data is String) {
                str = data.trim()
            }
            return str
        }


        fun isValidName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true

            // Name should be minimum 3 characters long
            if (str.length < 3) {
                valid = false
            }

            // Name not have any digit
            var exp = ".*[0-9].*"
            var pattern = Pattern.compile(exp)
            var matcher = pattern.matcher(str)
            if (matcher.matches()) {
                valid = false
            }

            // Name should contain no special character
            exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (matcher.matches()) {
                valid = false
            }

            if (updateUI) {
                val error: String? = if (valid) null else NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidStreetName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 3

            if (updateUI) {
                val error: String? = if (valid) null else STREET_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidCityName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length >= 3

            if (updateUI) {
                val error: String? = if (valid) null else CITY_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }


        fun isValidCountryName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 3

            if (updateUI) {
                val error: String? = if (valid) null else COUNTRY_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidStreetCode(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length >= 2

            if (updateUI) {
                val error: String? = if (valid) null else STREETCODE_NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidPostCode(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length == 5

            if (updateUI) {
                val error: String? = if (valid) null else POSTCODE_NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidDeviceName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2

            if (updateUI) {
                val error: String? = if (valid) null else DEVICE_NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        private fun setError(data: Any, error: String?) {
            if (data is EditText) {
                if (data.parent.parent is TextInputLayout) {
                    (data.parent.parent as TextInputLayout).error = error
                } else {
                    data.error = error
                }
            }
        }


    }
}