package com.krzysobo.soboapptpl.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


open class SoboViewModel : ViewModel() {
    var isApiError: MutableState<Boolean> = mutableStateOf(false)
    var isFormSent: MutableState<Boolean> = mutableStateOf(false)
    var apiErrorDetails: MutableState<String> = mutableStateOf("")

    protected fun clearErrorFlag(errorFlag: MutableState<Boolean>) {
        errorFlag.value = false
    }

    protected fun clearApiError() {
        clearErrorFlag(isApiError)
    }

    protected fun clearFormSent() {
        isFormSent.value = false
    }
}
